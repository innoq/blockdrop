package de.innoq.blockdrop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
	
	ServerSocket    listner;
	Socket          socket;	
	PrintWriter     outputWriter;
	BufferedReader  inputReader;
    Parser parser = new Parser();
	int             port;
	boolean         servermode;

	protected void connect() throws IOException {
		System.out.println("Client connecting to "+port);
		socket=new Socket("localhost", port);
		
		inputReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		outputWriter=new PrintWriter(socket.getOutputStream(), true);
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
						
						
						while((input=inputReader.readLine())!=null) {
							System.out.println("NET:"+input);
							if(input.startsWith("echo!")) {
								outputWriter.println(input.substring(5));
							}
							if (input.startsWith ("[next")) {
								parser.parseNextBlock (input);
								System.out.println ("Server has send next block: "+ input);
							}
							
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
							outputWriter.println(input);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			};

			new Thread(reader).start();
			new Thread(writer).start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Start Client with a port as number
	 * @param args
	 */
	public static void main(String[] args) {
		Client client=new Client();
		client.port=Integer.parseInt(args[0]);
		client.run();
	}

}
