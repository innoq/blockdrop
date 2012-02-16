package de.innoq.blockdrop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import de.innoq.blockdrop.algo.Operation;
import de.innoq.blockdrop.http.HttpServerTetrisListener;
import de.innoq.blockdrop.model.Point;

/**
 * Client Representation of the game.Add new TetrisListeners here to do what you want.
 *
 */
public class ServerProxy implements Runnable, TetrisServer {

	private BufferedReader inputReader;
	private PrintWriter outputWriter;
	
	private Point[] fixed = new Point[0];; // The Mess on the bottom of the game;
	private Point[] currentBlock = new Point[0];
	
	private List<TetrisListener> listeners = new LinkedList<TetrisListener>();
	
	
	Parser parser = new Parser();
	

	public ServerProxy(BufferedReader inputReader,
			PrintWriter outputWriter) {
		this.inputReader = inputReader;
		this.outputWriter = outputWriter;
		
		
		listeners.add(new HeuristicTetrisListener());
		listeners.add (new HttpServerTetrisListener());
		
		for (TetrisListener listener : listeners) {
			listener.setServer(this);
		}
	}

	@Override
	public void run() {
		String input;
		try {
			System.out.println("Waiting for network input");
			boolean handleNext = false;
			while ((input = inputReader.readLine()) != null) {
				System.out.println("NET:" + input);

				if (input.startsWith("[next")) {
					currentBlock = parser.parseNextBlock(input);
					// TODO to trigger the heuristic we need an representation of the "fixed" Block on the bottom.
					// This can be obtained via "info"
					System.out.println ("Asking for status");
					outputWriter.println("info");
					handleNext = true;
				}
				
				if (input.startsWith("[blocks ")) {
					fixed = parser.parseBlocks(input);
					
					if (handleNext) { // This info was triggered by a next!
						for (TetrisListener listener : listeners) {
							listener.next(currentBlock, fixed);
						}
						handleNext = false;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	


	@Override
	public void doOperations(List<Operation> operations) {
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

	@Override
	public Point[] getCurrentPiece() {
		return currentBlock;
	}

	@Override
	public Point[] getFixed() {
		return fixed;
	}

}
