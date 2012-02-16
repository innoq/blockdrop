package de.innoq.blockdrop.algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import de.innoq.blockdrop.Parser;
import de.innoq.blockdrop.model.Point;

public class HeuristicDrivenResponder implements Runnable {

	private BufferedReader inputReader;
	private PrintWriter outputWriter;
	Parser parser = new Parser();
	DropHeuristic heuristic = new NopDropHeuristic();

	public HeuristicDrivenResponder(BufferedReader inputReader,
			PrintWriter outputWriter) {
		this.inputReader = inputReader;
		this.outputWriter = outputWriter;
	}

	@Override
	public void run() {
		String input;
		try {
			System.out.println("Waiting for network input");
			Point[] currentBlock = null;
			Point[] fixed; // The Mess on the bottom of the game;
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
						currentBlock = null;
					}
					
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
