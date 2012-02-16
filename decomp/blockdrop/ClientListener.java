package progevent.blockdrop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientListener extends Thread {
	Server server;
	Socket clientSocket;
	public ClientListener(Server server, Socket clientSocket) {
		this.server=server;
		this.clientSocket=clientSocket;

	}
	public void run() {
		String command="";
		try {
			BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			while(!server.isTerminated()) {
				while((command=clientReader.readLine())!=null) {
					server.execute(command);
				}
			}
		} catch (IOException e) {
			server.terminate(e);
		}	
	}
}