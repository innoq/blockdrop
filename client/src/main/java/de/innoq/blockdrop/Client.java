package de.innoq.blockdrop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

import de.innoq.blockdrop.http.BlockDropResource;


public class Client {
	
	ServerSocket    listner;
	Socket          socket;	
	PrintWriter     outputWriter;
	BufferedReader  inputReader;

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
			// Automatically responses to "next" Messages from the server.
			Runnable reader = new ServerProxy(inputReader, outputWriter);

			// Read stdin and pipes output to Server
			Runnable writer= new Runnable() {
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
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Client client=new Client();
		client.port=Integer.parseInt(args[0]);
		client.run();
	}

}
