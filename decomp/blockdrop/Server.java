package progevent.blockdrop;

public interface Server {
	void execute(String command);
	void info();
	void terminate();
	void terminate(Throwable ex);		
	boolean isTerminated();
	void pause();
	void resume();
	void start();
	void signalError(String msg);
	boolean drop(int col, int rot);
}