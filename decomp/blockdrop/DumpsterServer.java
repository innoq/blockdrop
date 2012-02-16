package progevent.blockdrop;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DumpsterServer implements Server {

	Dumpster        dumpster;
	Block           nextBlock;

	ClientListener  client;
	BlockDropper    dropper;

	ServerSocket    listenSocket;
	Socket          clientSocket;
	
	PrintWriter     clientWriter;
	
	enum State {
		running, finished, paused, stopped, terminated;
	}
	State state;
	
	public DumpsterServer() {
		dumpster=new Dumpster(14, 10);
	}
	
	public void run(int port) throws IOException {
		listenSocket=new ServerSocket(port);
		System.out.println("Waiting for connection at "+port);
		clientSocket=listenSocket.accept();
		System.out.println("Established connection at "+port);
		clientWriter=new PrintWriter(clientSocket.getOutputStream(), true);
		dumpster.reset();
		client=new ClientListener(this, clientSocket);
		client.start();
		sendToClient("(**** BLOCKDROP ****)");
		selectNextBlock();

		dropper=new BlockDropper(this);
		dropper.block();
		dropper.start();

		try {
			client.join();
			System.out.println("Dying ...");
			dropper.interrupt();
			dropper.join();
			System.out.println("Dead");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected void selectNextBlock() {
		nextBlock=Block.randomBlock();
		sendToClient("(*NEXT*\n"+nextBlock.toString()+")");
	}
	
	@Override
	public void execute(String command) {
		command=command.trim().toLowerCase();
		if(command.equals("start")) {
			start();
		} else if(command.equals("end")) {
			terminate();
		} else if(command.equals("pause")) {
			pause();
		} else if(command.equals("cont")) {
			resume();
		} else if(command.startsWith("drop")) {
			String[] args=command.substring(4).trim().split(" ");
			try {
				int col=args.length>0?Integer.parseInt(args[0].trim()):0;
				int rot=args.length>1?Integer.parseInt(args[1].trim()):0;
				drop(col, rot);
			} catch(NumberFormatException ex) {
				signalError("parameter error ["+command+"]");
			}
		} else if(command.equals("info")) {
			info();
		} else {
			signalError("unknown command ["+command+"]");
		}
	}


	@Override
	public void info() {
		sendToClient("(*INFO*\n");
		sendToClient("(*NEXT*\n"+nextBlock.toString()+")");
		sendToClient("(*PIT*\n");
		dumpster.print(clientWriter);
		sendToClient("\n(*STAT* (*BLOCKS* "+dumpster.blocksDropped+") (*ROWS* "+dumpster.rowsCompleted+"))\n");
		sendToClient(")");
	}

	@Override
	public synchronized void terminate() {
		sendToClient("(*INFO*SERVER TERMINATED)");
		state=State.terminated;
		try {
			clientSocket.close();
		} catch(Exception e) {
			System.out.println("Error on socket close: "+e.getMessage());
		}
	}

	@Override
	public synchronized void terminate(Throwable ex) {
		signalError(ex.getMessage()+"\n SERVER TERMINATED");
		state=State.terminated;
		try {
			clientSocket.close();
		} catch(Exception e) {
			System.out.println("Error on socket close: "+e.getMessage());
		}
	}

	@Override
	public synchronized boolean isTerminated() {
		return state==State.terminated;
	}

	@Override
	public synchronized void pause() {
		if(state==State.running) {
			state=State.paused;
			dropper.block();
		}
	}

	@Override
	public synchronized void resume() {
		if(state==State.paused) {
			state=State.running;
			dropper.unblock();
		}
	}

	@Override
	public synchronized void start() {
		if(state!=State.terminated) {
			dropper.block();
			state=State.running;
			dropper.resetSnooze();
			dumpster.reset();
			selectNextBlock();
			dropper.unblock();
		} else {
			signalError("Starting a terminated sever does not work");
		}
	}
	
	protected synchronized void finish() {
		if(state!=State.terminated) {
			dropper.block();
			state=State.finished;
			sendToClient("(*FINISH* (*BLOCKS* "+dumpster.blocksDropped+") (*ROWS* "+dumpster.rowsCompleted+"))");
		}
	}


	@Override
	public void signalError(String msg) {
		sendToClient("(*ERROR*"+msg+")");
	}

	@Override
	public boolean drop(int col, int rot) {
		boolean dropped=dumpster.dropBlock(nextBlock.rotate(rot).data(), col);
		if(dropped) {
			selectNextBlock();
		} else {
			finish();
		}
		return dropped;
	}

	protected void sendToClient(String msg) {
		clientWriter.println(msg);
	}

	static final int DEFAULT_PORT=31415;
	public static void main(String[] args) {
		DumpsterServer srv=new DumpsterServer();
		try {
			int port=DEFAULT_PORT;
			try {
				port=Integer.parseInt(args[0]);
			} catch(Exception e) {
				System.out.println("Using default port "+DEFAULT_PORT);
			}
			srv.run(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
