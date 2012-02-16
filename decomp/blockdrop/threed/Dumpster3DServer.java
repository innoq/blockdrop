/*     */ package progevent.blockdrop.threed;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import progevent.blockdrop.ClientListener;
/*     */ import progevent.blockdrop.Server;
/*     */ 
/*     */ public class Dumpster3DServer
/*     */   implements Server
/*     */ {
/*     */   Dumpster3D dumpster;
/*     */   ClientListener client;
/*     */   Block3DDropper dropper;
/*     */   ServerSocket listenSocket;
/*     */   Socket clientSocket;
/*     */   PrintWriter clientWriter;
/*     */   State state;
/*     */   static final int DEFAULT_PORT = 31415;
/*     */   static final int TEST_PORT = 27182;
/*     */   static final int X = 5;
/*     */   static final int Y = 5;
/*     */   static final int Z = 10;
/*     */ 
/*     */   public Dumpster3DServer(int Nx, int Ny, int Nz)
/*     */   {
/*  32 */     this.dumpster = new Dumpster3D(Nx, Ny, Nz);
/*  33 */     this.state = State.paused;
/*     */   }
/*     */ 
/*     */   public void run(int port) throws IOException {
/*  37 */     this.listenSocket = new ServerSocket(port);
/*  38 */     System.out.println("Waiting for connection at " + port);
/*  39 */     this.clientSocket = this.listenSocket.accept();
/*  40 */     System.out.println("Established connection at " + port);
/*  41 */     this.clientWriter = new PrintWriter(this.clientSocket.getOutputStream(), true);
/*  42 */     this.dumpster.reset();
/*  43 */     this.client = new ClientListener(this, this.clientSocket);
/*  44 */     this.client.start();
/*  45 */     sendToClient("(**** BLOCKDROP ****)");
/*  46 */     selectNextBlock();
/*     */ 
/*  48 */     this.dropper = new Block3DDropper(this);
/*  49 */     this.dropper.block();
/*  50 */     this.dropper.start();
/*     */     try
/*     */     {
/*  53 */       this.client.join();
/*  54 */       System.out.println("Dying ...");
/*  55 */       this.dropper.interrupt();
/*  56 */       this.dropper.join();
/*  57 */       System.out.println("Dead");
/*     */     }
/*     */     catch (InterruptedException e) {
/*  60 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void execute(String command)
/*     */   {
/*  68 */     command = command.trim().toLowerCase();
/*  69 */     if (command.equals("start")) {
/*  70 */       start();
/*  71 */     } else if (command.equals("end")) {
/*  72 */       terminate();
/*  73 */     } else if (command.equals("pause")) {
/*  74 */       pause();
/*  75 */     } else if (command.equals("cont")) {
/*  76 */       resume();
/*  77 */     } else if (command.startsWith("m ")) {
/*  78 */       String[] cmds = command.split(" ");
/*  79 */       for (int i = 1; i < cmds.length; i++) {
/*  80 */         if (!executeMove(cmds[i].trim())) {
/*  81 */           signalError("Did not execute " + i + "th of " + command);
/*  82 */           break;
/*     */         }
/*     */       }
/*  85 */       sendBlockInfo();
/*  86 */     } else if ((command.equals("d")) || (command.equals("drop"))) {
/*  87 */       drop();
/*  88 */     } else if ((command.equals("i")) || (command.equals("info"))) {
/*  89 */       info();
/*  90 */     } else if (command.equals("help")) {
/*  91 */       help();
/*     */     } else {
/*  93 */       signalError("unknown command [" + command + "]");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected synchronized boolean executeMove(String mv)
/*     */   {
/*  99 */     if (mv.equals("+x"))
/* 100 */       return this.dumpster.moveX(1);
/* 101 */     if (mv.equals("-x"))
/* 102 */       return this.dumpster.moveX(-1);
/* 103 */     if (mv.equals("+y"))
/* 104 */       return this.dumpster.moveY(1);
/* 105 */     if (mv.equals("-y"))
/* 106 */       return this.dumpster.moveY(-1);
/* 107 */     if (mv.equals("+rx"))
/* 108 */       return this.dumpster.rotX();
/* 109 */     if (mv.equals("-rx"))
/* 110 */       return this.dumpster.nrotX();
/* 111 */     if (mv.equals("+ry"))
/* 112 */       return this.dumpster.rotY();
/* 113 */     if (mv.equals("-ry"))
/* 114 */       return this.dumpster.nrotY();
/* 115 */     if (mv.equals("+rz"))
/* 116 */       return this.dumpster.rotZ();
/* 117 */     if (mv.equals("-rz")) {
/* 118 */       return this.dumpster.nrotZ();
/*     */     }
/* 120 */     return false;
/*     */   }
/*     */ 
/*     */   public void info()
/*     */   {
/* 127 */     StringBuilder sb = new StringBuilder();
/* 128 */     sb.append("[info [next ");
/* 129 */     sb.append(this.dumpster.next.toString()).append("]\n");
/* 130 */     sb.append("[completed ").append(this.dumpster.completed).append("]\n");
/* 131 */     sb.append("[blocks ").append(this.dumpster.toString()).append("]]");
/* 132 */     sendToClient(sb.toString());
/*     */   }
/*     */ 
/*     */   public void terminate()
/*     */   {
/* 137 */     sendToClient("[info server terminated]");
/* 138 */     this.state = State.terminated;
/*     */     try {
/* 140 */       this.clientSocket.close();
/*     */     } catch (Exception e) {
/* 142 */       System.out.println("Error on socket close: " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void terminate(Throwable ex)
/*     */   {
/* 148 */     signalError(ex.getMessage() + " : server terminated");
/* 149 */     this.state = State.terminated;
/*     */     try {
/* 151 */       this.clientSocket.close();
/*     */     } catch (Exception e) {
/* 153 */       System.out.println("Error on socket close: " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isTerminated()
/*     */   {
/* 159 */     return this.state == State.terminated;
/*     */   }
/*     */ 
/*     */   public void pause()
/*     */   {
/* 164 */     if (this.state == State.running) {
/* 165 */       this.state = State.paused;
/* 166 */       this.dropper.block();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void resume()
/*     */   {
/* 172 */     if (this.state == State.paused) {
/* 173 */       this.state = State.running;
/* 174 */       this.dropper.unblock();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/* 180 */     if (this.state != State.terminated) {
/* 181 */       this.dropper.block();
/* 182 */       this.state = State.running;
/* 183 */       this.dropper.resetSnooze();
/* 184 */       this.dumpster.reset();
/* 185 */       selectNextBlock();
/* 186 */       this.dropper.unblock();
/*     */     } else {
/* 188 */       signalError("Starting a terminated sever does not work");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void signalError(String msg)
/*     */   {
/* 194 */     sendToClient("[ERROR " + msg + " ]");
/*     */   }
/*     */ 
/*     */   public boolean drop(int col, int rot)
/*     */   {
/* 199 */     throw new RuntimeException("UNSUPPORTED, BADLY DESIGNED PROGRAM AT WORK");
/*     */   }
/*     */ 
/*     */   protected void sendStreamToClient(InputStream is)
/*     */     throws IOException
/*     */   {
/* 207 */     BufferedReader rd = new BufferedReader(new InputStreamReader(is));
/*     */     String line;
/* 209 */     while ((line = rd.readLine()) != null)
/*     */     {
/*     */       String line;
/* 210 */       sendToClient(line);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void help() {
/* 215 */     sendToClient("[help ");
/*     */     try {
/* 217 */       sendStreamToClient(getClass().getResourceAsStream("/help.txt"));
/*     */     } catch (IOException localIOException) {
/*     */     }
/* 220 */     sendToClient("[blocks");
/* 221 */     for (Block3D b : Dumpster3D.BLOCKS) {
/* 222 */       sendToClient(b.toString());
/*     */     }
/* 224 */     sendToClient("]]");
/*     */   }
/*     */ 
/*     */   protected synchronized void sendBlockInfo()
/*     */   {
/* 229 */     sendToClient("[next " + this.dumpster.next.toString() + "]");
/*     */   }
/*     */   protected synchronized void selectNextBlock() {
/* 232 */     this.dumpster.selectNextBlock();
/* 233 */     sendBlockInfo();
/*     */   }
/*     */ 
/*     */   public synchronized boolean down() {
/* 237 */     boolean done = this.dumpster.down();
/* 238 */     if (!done) {
/* 239 */       int z_min = this.dumpster.next.minCoords().z;
/* 240 */       int z_max = this.dumpster.next.maxCoords().z;
/* 241 */       if (z_max < this.dumpster.Nz) {
/* 242 */         this.dumpster.set(this.dumpster.next);
/* 243 */         this.dumpster.removeFilledLayers(z_min);
/* 244 */         this.dumpster.selectNextBlock();
/* 245 */         sendBlockInfo();
/*     */       } else {
/* 247 */         pause();
/* 248 */         sendToClient("[finish [completed " + this.dumpster.completed + "]");
/*     */       }
/*     */     } else {
/* 251 */       sendBlockInfo();
/*     */     }
/* 253 */     return done;
/*     */   }
/*     */ 
/*     */   public synchronized void drop() {
/* 257 */     boolean done = true;
/* 258 */     while (done) {
/* 259 */       done = this.dumpster.down();
/* 260 */       if (!done) {
/* 261 */         int z_min = this.dumpster.next.minCoords().z;
/* 262 */         int z_max = this.dumpster.next.maxCoords().z;
/* 263 */         if (z_max < this.dumpster.Nz) {
/* 264 */           this.dumpster.set(this.dumpster.next);
/* 265 */           this.dumpster.removeFilledLayers(z_min);
/* 266 */           this.dumpster.selectNextBlock();
/* 267 */           sendBlockInfo();
/*     */         } else {
/* 269 */           pause();
/* 270 */           sendToClient("[finish [completed " + this.dumpster.completed + "]");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void sendToClient(String msg) {
/* 277 */     this.clientWriter.println(msg);
/* 278 */     this.clientWriter.flush();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     try
/*     */     {
/* 290 */       int port = 31415;
/*     */       try {
/* 292 */         port = Integer.parseInt(args[0]);
/*     */       } catch (Exception e) {
/* 294 */         System.out.println("Using default port 31415");
/*     */       }
/*     */       Dumpster3DServer srv;
/* 296 */       if (port == 27182) {
/* 297 */         Dumpster3DServer srv = new Dumpster3DServer(2, 2, 5);
/* 298 */         Dumpster3D.BLOCKS = new Block3D[] { Block3D.blk(new Block3D.Pt[] { Block3D.pt(0, 0) }), 
/* 299 */           Block3D.blk(new Block3D.Pt[] { 
/* 299 */           Block3D.pt(0, 0), Block3D.pt(1, 0) }) };
/*     */       } else {
/* 301 */         srv = new Dumpster3DServer(5, 5, 10);
/*     */       }
/* 303 */       srv.run(port);
/*     */     } catch (IOException e) {
/* 305 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   static enum State
/*     */   {
/*  27 */     running, finished, paused, stopped, terminated;
/*     */   }
/*     */ }

/* Location:           /Users/martinei/blockdrop/
 * Qualified Name:     progevent.blockdrop.threed.Dumpster3DServer
 * JD-Core Version:    0.6.0
 */