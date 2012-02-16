/*     */ package progevent.blockdrop.threed;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class Dumpster3D
/*     */ {
/*     */   public final int Nx;
/*     */   public final int Ny;
/*     */   public final int Nz;
/*     */   private final Random rnd;
/*  18 */   public static final Block3D[] ALL_BLOCKS = { 
/*  20 */     Block3D.blk(new Block3D.Pt[] { 
/*  20 */     Block3D.pt(0, 0) }), 
/*  22 */     Block3D.blk(new Block3D.Pt[] { 
/*  22 */     Block3D.pt(0, 0), Block3D.pt(1, 0) }), 
/*  24 */     Block3D.blk(new Block3D.Pt[] { 
/*  24 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(2, 0) }), 
/*  25 */     Block3D.blk(new Block3D.Pt[] { 
/*  25 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(1, 1) }), 
/*  27 */     Block3D.blk(new Block3D.Pt[] { 
/*  27 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(2, 0), Block3D.pt(3, 0) }), 
/*  28 */     Block3D.blk(new Block3D.Pt[] { 
/*  28 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(0, 1), Block3D.pt(1, 1) }), 
/*  29 */     Block3D.blk(new Block3D.Pt[] { 
/*  29 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(1, 1), Block3D.pt(2, 1) }), 
/*  30 */     Block3D.blk(new Block3D.Pt[] { 
/*  30 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(2, 0), Block3D.pt(2, 1) }), 
/*  31 */     Block3D.blk(new Block3D.Pt[] { 
/*  31 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(2, 0), Block3D.pt(1, 1) }), 
/*  32 */     Block3D.blk(new Block3D.Pt[] { 
/*  32 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(1, 1), Block3D.pt(1, 1, 1) }), 
/*  33 */     Block3D.blk(new Block3D.Pt[] { 
/*  33 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(1, 1), Block3D.pt(1, 1, -1) }), 
/*  34 */     Block3D.blk(new Block3D.Pt[] { 
/*  34 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(1, 1), Block3D.pt(1, 0, 1) }), 
/*  36 */     Block3D.blk(new Block3D.Pt[] { 
/*  36 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(2, 0), Block3D.pt(3, 0), Block3D.pt(4, 0) }), 
/*  37 */     Block3D.blk(new Block3D.Pt[] { 
/*  37 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(2, 0), Block3D.pt(3, 0), Block3D.pt(3, 1) }), 
/*  38 */     Block3D.blk(new Block3D.Pt[] { 
/*  38 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(2, 0), Block3D.pt(3, 0), Block3D.pt(2, 1) }), 
/*  39 */     Block3D.blk(new Block3D.Pt[] { 
/*  39 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(0, 1), Block3D.pt(1, 1), Block3D.pt(2, 0, 0) }), 
/*  40 */     Block3D.blk(new Block3D.Pt[] { 
/*  40 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(0, 1), Block3D.pt(1, 1), Block3D.pt(0, 0, 1) }), 
/*  41 */     Block3D.blk(new Block3D.Pt[] { 
/*  41 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(1, 1), Block3D.pt(2, 1), Block3D.pt(2, 2) }), 
/*  42 */     Block3D.blk(new Block3D.Pt[] { 
/*  42 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(1, 1), Block3D.pt(2, 1), Block3D.pt(2, 1, 1) }), 
/*  43 */     Block3D.blk(new Block3D.Pt[] { 
/*  43 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(1, 1), Block3D.pt(2, 1), Block3D.pt(1, 1, 1) }), 
/*  44 */     Block3D.blk(new Block3D.Pt[] { 
/*  44 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(1, 1), Block3D.pt(2, 1), Block3D.pt(2, 1, -1) }), 
/*  45 */     Block3D.blk(new Block3D.Pt[] { 
/*  45 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(1, 1), Block3D.pt(2, 1), Block3D.pt(1, 1, -1) }), 
/*  46 */     Block3D.blk(new Block3D.Pt[] { 
/*  46 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(1, 1), Block3D.pt(0, 0, 1), Block3D.pt(1, 1, 1) }), 
/*  47 */     Block3D.blk(new Block3D.Pt[] { 
/*  47 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(1, 1), Block3D.pt(0, 0, -1), Block3D.pt(1, 1, 1) }), 
/*  48 */     Block3D.blk(new Block3D.Pt[] { 
/*  48 */     Block3D.pt(0, 0), Block3D.pt(1, 0), Block3D.pt(2, 0), Block3D.pt(0, 1), Block3D.pt(2, 1) }) };
/*     */ 
/*  51 */   public static Block3D[] BLOCKS = ALL_BLOCKS;
/*     */ 
/*  54 */   public int completed = 0;
/*     */   int[][][] cubes;
/*     */   public Block3D next;
/*     */ 
/*     */   public Dumpster3D(int Nx, int Ny, int Nz)
/*     */   {
/*  61 */     this.Nx = Nx; this.Ny = Ny; this.Nz = Nz;
/*  62 */     this.cubes = new int[Nz][Nx][Ny];
/*  63 */     this.rnd = new Random();
/*  64 */     selectNextBlock();
/*     */   }
/*     */   public Block3D initialPosition(Block3D b) {
/*  67 */     Block3D.Pt mn = b.rotationZero();
/*  68 */     Block3D.Pt mx = b.maxCoords();
/*  69 */     return b.move((this.Nx - mx.x - mn.x) / 2, (this.Ny - mx.y - mn.y) / 2, this.Nz - mn.z);
/*     */   }
/*     */ 
/*     */   public Block3D randomRotate(Block3D b) {
/*  73 */     return b.rotate(Alg.mul(Alg.rotz[this.rnd.nextInt(4)], 
/*  74 */       Alg.mul(Alg.roty[this.rnd.nextInt(4)], 
/*  75 */       Alg.rotx[this.rnd.nextInt(4)])), 
/*  76 */       b.rotationZero());
/*     */   }
/*     */ 
/*     */   public Block3D randomCube() {
/*  80 */     Block3D b = randomRotate(BLOCKS[this.rnd.nextInt(BLOCKS.length)]);
/*  81 */     return initialPosition(b);
/*     */   }
/*     */ 
/*     */   public synchronized void selectNextBlock()
/*     */   {
/*  86 */     this.next = randomCube();
/*     */   }
/*     */ 
/*     */   public int[][] heights()
/*     */   {
/*  91 */     int[][] res = new int[this.Nx][this.Ny];
/*  92 */     for (int x = 0; x < this.Nx; x++) {
/*  93 */       for (int y = 0; y < this.Ny; y++) {
/*  94 */         int z = this.Nz;
/*  95 */         while (z > 0) {
/*  96 */           z--; if (this.cubes[z][x][y] != 0) {
/*  97 */             res[x][y] = z;
/*  98 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 103 */     return res;
/*     */   }
/*     */ 
/*     */   public int get(Block3D.Pt p) {
/* 107 */     if ((p.x < 0) || (p.x >= this.Nx)) {
/* 108 */       return 1;
/*     */     }
/* 110 */     if ((p.y < 0) || (p.y >= this.Ny)) {
/* 111 */       return 1;
/*     */     }
/* 113 */     if (p.z < 0) {
/* 114 */       return 1;
/*     */     }
/* 116 */     if (p.z >= this.Nz) {
/* 117 */       return 0;
/*     */     }
/* 119 */     return this.cubes[p.z][p.x][p.y];
/*     */   }
/*     */ 
/*     */   protected void set(Block3D.Pt p, int val) {
/* 123 */     if ((p.x < 0) || (p.x >= this.Nx) || 
/* 124 */       (p.y < 0) || (p.y >= this.Ny) || 
/* 125 */       (p.z < 0) || (p.z >= this.Nz)) {
/* 126 */       return;
/*     */     }
/* 128 */     this.cubes[p.z][p.x][p.y] = val;
/*     */   }
/*     */   public void set(Block3D.Pt p) {
/* 131 */     set(p, 1);
/*     */   }
/*     */   public void set(Block3D b) {
/* 134 */     for (Block3D.Pt p : b.data)
/* 135 */       set(p); 
/*     */   }
/*     */ 
/*     */   public void clear(Block3D.Pt p) {
/* 138 */     set(p, 0);
/*     */   }
/*     */   public void set(int x, int y, int z, int val) {
/* 141 */     this.cubes[z][x][y] = val;
/*     */   }
/*     */ 
/*     */   public void setLayer(int z, int val) {
/* 145 */     for (int x = 0; x < this.Nx; x++)
/* 146 */       for (int y = 0; y < this.Ny; y++)
/* 147 */         this.cubes[z][x][y] = val;
/*     */   }
/*     */ 
/*     */   public int get(int x, int y, int z)
/*     */   {
/* 153 */     return this.cubes[z][x][y];
/*     */   }
/*     */ 
/*     */   public int[][] getLayer(int z) {
/* 157 */     return this.cubes[z];
/*     */   }
/*     */ 
/*     */   public boolean fitsQ(Block3D blk) {
/* 161 */     for (Block3D.Pt p : blk.data) {
/* 162 */       if (get(p) != 0) {
/* 163 */         return false;
/*     */       }
/*     */     }
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */   protected synchronized boolean modifyNext(Block3D next_) {
/* 170 */     boolean modifyOK = fitsQ(next_);
/* 171 */     if (modifyOK) {
/* 172 */       this.next = next_;
/*     */     }
/* 174 */     notify();
/* 175 */     return modifyOK;
/*     */   }
/*     */ 
/*     */   public synchronized boolean down()
/*     */   {
/* 180 */     return modifyNext(this.next.move(0, 0, -1));
/*     */   }
/*     */ 
/*     */   public synchronized boolean moveX(int dx) {
/* 184 */     return modifyNext(this.next.move(dx, 0, 0));
/*     */   }
/*     */ 
/*     */   public synchronized boolean moveY(int dy) {
/* 188 */     return modifyNext(this.next.move(0, dy, 0));
/*     */   }
/*     */ 
/*     */   public synchronized boolean rotX() {
/* 192 */     return modifyNext(this.next.rotateX());
/*     */   }
/*     */   public synchronized boolean nrotX() {
/* 195 */     return modifyNext(this.next.nrotateX());
/*     */   }
/*     */   public synchronized boolean rotY() {
/* 198 */     return modifyNext(this.next.rotateY());
/*     */   }
/*     */   public synchronized boolean nrotY() {
/* 201 */     return modifyNext(this.next.nrotateY());
/*     */   }
/*     */   public synchronized boolean rotZ() {
/* 204 */     return modifyNext(this.next.rotateX());
/*     */   }
/*     */   public synchronized boolean nrotZ() {
/* 207 */     return modifyNext(this.next.nrotateZ());
/*     */   }
/*     */ 
/*     */   public void clearLayer(int z)
/*     */   {
/* 213 */     int[][] layer = this.cubes[z];
/* 214 */     for (int x = 0; x < this.Nx; x++)
/* 215 */       for (int y = 0; y < this.Ny; y++)
/* 216 */         layer[x][y] = 0;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 223 */     this.completed = 0;
/* 224 */     for (int z = 0; z < this.Nz; z++)
/* 225 */       clearLayer(z);
/*     */   }
/*     */ 
/*     */   public boolean filledLayerQ(int z)
/*     */   {
/* 230 */     for (int x = 0; x < this.Nx; x++) {
/* 231 */       for (int y = 0; y < this.Ny; y++) {
/* 232 */         if (this.cubes[z][x][y] == 0) {
/* 233 */           return false;
/*     */         }
/*     */       }
/*     */     }
/* 237 */     return true;
/*     */   }
/*     */ 
/*     */   protected void swapLayers(int z0, int z1) {
/* 241 */     int[][] aux = this.cubes[z0];
/* 242 */     this.cubes[z0] = this.cubes[z1];
/* 243 */     this.cubes[z1] = aux;
/*     */   }
/*     */ 
/*     */   protected void bubbleup(int z0) {
/* 247 */     for (int z = z0; z + 1 < this.Nz; z++)
/* 248 */       swapLayers(z, z + 1);
/*     */   }
/*     */ 
/*     */   public int removeFilledLayers(int zMn)
/*     */   {
/* 253 */     int nFilled = 0;
/* 254 */     int z = Math.max(zMn, 0);
/* 255 */     while (z < this.Nz) {
/* 256 */       if (filledLayerQ(z)) {
/* 257 */         nFilled++;
/* 258 */         clearLayer(z);
/* 259 */         bubbleup(z);
/*     */       }
/* 261 */       z++;
/*     */     }
/* 263 */     this.completed += nFilled;
/* 264 */     return nFilled;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 269 */     StringBuilder sb = new StringBuilder();
/* 270 */     boolean written = false;
/* 271 */     sb.append("[");
/* 272 */     for (int z = 0; z < this.Nz; z++) {
/* 273 */       for (int x = 0; x < this.Nx; x++) {
/* 274 */         for (int y = 0; y < this.Ny; y++) {
/* 275 */           if (this.cubes[z][x][y] != 0) {
/* 276 */             if (written)
/* 277 */               sb.append(",");
/*     */             else {
/* 279 */               written = true;
/*     */             }
/* 281 */             sb.append("[").append(x).append(",").append(y).append(",").append(z).append("]");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 286 */     sb.append("]");
/* 287 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toString_()
/*     */   {
/* 292 */     StringBuilder sb = new StringBuilder();
/* 293 */     for (int z = 0; z < this.Nz; z++) {
/* 294 */       for (int x = 0; x < this.Nx; x++) {
/* 295 */         for (int y = 0; y < this.Ny; y++) {
/* 296 */           if (this.cubes[z][x][y] == 0)
/* 297 */             sb.append(" ");
/*     */           else {
/* 299 */             sb.append(z);
/*     */           }
/*     */         }
/* 302 */         sb.append("\n");
/*     */       }
/* 304 */       sb.append("..................................\n");
/*     */     }
/* 306 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           /Users/martinei/blockdrop/
 * Qualified Name:     progevent.blockdrop.threed.Dumpster3D
 * JD-Core Version:    0.6.0
 */