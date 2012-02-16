
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/** Geklaut aus dem jar*/

public class TCPDummy {

	ServerSocket    listner;
	Socket          client;	
	PrintWriter     clientWriter;
	BufferedReader  clientReader;

	int             port;
	boolean         servermode;

	protected void connect() throws IOException {
		if(servermode) {
			System.out.println("Server listening at "+port);
			listner=new ServerSocket(port);
			client=listner.accept();
		} else {
			System.out.println("Client connecting to "+port);
			client=new Socket("localhost", port);
		}
		clientReader=new BufferedReader(new InputStreamReader(client.getInputStream()));
		clientWriter=new PrintWriter(client.getOutputStream(), true);
	}

	public void run() {
		try {
			connect();
			Runnable reader=new Runnable() {				
				@Override
				public void run() {
					String input;
					try {
						System.out.println("Waiting for network input");
						while((input=clientReader.readLine())!=null) {
							System.out.println("NET:"+input);
							if(input.startsWith("echo!"))
								clientWriter.println(input.substring(5));
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			};

			Runnable writer=new Runnable() {

				@Override
				public void run() {
					BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
					String input;
					try {
						System.out.println("Waiting for stdin");
						while((input=stdin.readLine())!=null) {
							System.out.println("STDIN:"+input);
							clientWriter.println(input);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			};

			new Thread(reader).start();
			new Thread(writer).start();
			if(servermode) {
				clientWriter.println("How may I be of service?");
			} else {
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		TCPDummy s=new TCPDummy();
		s.port=Integer.parseInt(args[0]);
		if(args.length>1) {
			s.servermode=true;
		}
		s.run();
	}

}
