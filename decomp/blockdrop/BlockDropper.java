package progevent.blockdrop;

public class BlockDropper extends Thread {
	Server server;


	long     t_snooze;
	long     t_snooze_init=5000;
	double   alpha=Math.exp(Math.log(0.1)/20); // Factor 0.1 after 20 iterations
	boolean  blocked=false;

	public BlockDropper(Server server) {
		this.server=server;
		t_snooze=t_snooze_init;
	}
	public synchronized void block() {
		blocked=true;
	}
	public synchronized void unblock() {
		blocked=false;
		notify();
	}

	public void snooze() {
		try { 
			System.out.println("ZZZZ "+t_snooze);
			Thread.sleep(t_snooze); 
		} catch(InterruptedException e) { 
			System.out.println("INTERUPTED");
		}
		System.out.println("AWAKE");
		t_snooze=(long)(alpha*t_snooze);
	}

	public void resetSnooze() {
		t_snooze=t_snooze_init;
	}

	public void run() {
		while(!server.isTerminated()) {
			while(blocked) {
				synchronized (this) {
					try { wait(); } 
					catch (InterruptedException e) {
						System.out.println("INTERUPTED");
						return; 
					}
				}
			}
			snooze();
			if(!blocked) server.drop(0, 0);			
		}
	}
}