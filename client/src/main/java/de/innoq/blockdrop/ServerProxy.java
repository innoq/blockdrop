package de.innoq.blockdrop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

import de.innoq.blockdrop.algo.NopDropHeuristic;
import de.innoq.blockdrop.algo.Operation;
import de.innoq.blockdrop.algo.heuristics.Heuristic;
import de.innoq.blockdrop.http.BlockDropResource;
import de.innoq.blockdrop.model.Point;

/**
 * Client Representation of the game
 *
 */
public class ServerProxy implements Runnable {

	private BufferedReader inputReader;
	private PrintWriter outputWriter;
	private Point[] fixed = new Point[0];; // The Mess on the bottom of the game;
	private Point[] currentBlock = new Point[0];
	Parser parser = new Parser();
	Heuristic heuristic = new NopDropHeuristic();

	public ServerProxy(BufferedReader inputReader,
			PrintWriter outputWriter) {
		this.inputReader = inputReader;
		this.outputWriter = outputWriter;
	}

	@Override
	public void run() {
	    try {
			HttpServer httpServer = startServer();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    // Oh? httpServer.stop();	
		
		String input;
		try {
			System.out.println("Waiting for network input");
			
			while ((input = inputReader.readLine()) != null) {
				System.out.println("NET:" + input);
				if (input.startsWith("echo!")) {
					outputWriter.println(input.substring(5));
				}
				if (input.startsWith("[next")) {
					currentBlock = parser.parseNextBlock(input);
					// TODO to trigger the heuristic we need an representation of the "fixed" Block on the bottom.
					// This can be obtained via "info"
					System.out.println("Server has send next block: " + input);
					System.out.println ("Asking for status");
					outputWriter.println("info");
				}
				
				if (input.startsWith("[blocks ")) {
					fixed = parser.parseBlocks(input);
					// We should now have an currentBlock and the fixed Section. Hand over to the heuristic.	
					if (currentBlock != null) {
						List<Operation> operations = heuristic.calculateMoves(currentBlock, fixed);
						
						sendOperations(operations);
					}
					
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	/**
	 * Http Uri to get current board as json
	 * @return
	 */
    private  URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(9998).build();
    }
	
    protected  HttpServer startServer() throws IOException {
    	final URI BASE_URI = getBaseURI();
        System.out.println("Starting grizzly...");
        //ResourceConfig rc = new PackagesResourceConfig("com.sun.jersey.samples.helloworld.resources");
        final BlockDropResource resource = new BlockDropResource();
        ResourceConfig rc = new DefaultResourceConfig() {

			@Override
			public Object getProperty(String propertyName) {
				if ("board".equals(propertyName)) {
					concat (fixed, currentBlock);
					return fixed;
				}
				return null;
			}

			@Override
			public Set<Object> getSingletons() {
				Set<Object> result = new HashSet<Object>();
				result.add(resource);
				return result;
			}
        	
        };
        return GrizzlyServerFactory.createHttpServer(BASE_URI, rc);
    }    
	
    public static <T> T[] concat(T[] first, T[] second) {
    	  T[] result = Arrays.copyOf(first, first.length + second.length);
    	  System.arraycopy(second, 0, result, first.length, second.length);
    	  return result;
    	}


	private void sendOperations(List<Operation> operations) {
		StringBuffer buf = new StringBuffer("m ");
		boolean drop = false;
		boolean realCommand = false;
		for (Operation op : operations) {
			if (op.equals(Operation.drop)) {
				drop = true;
				// 
			} else {
				buf.append(op.representation());
				buf.append (" ");
				realCommand = true;
			}
			
		}
		if (realCommand) {
			outputWriter.println (buf.toString());
		}

		if (drop) {
			outputWriter.println ("d");
		}		
	}

}
