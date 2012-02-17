package de.innoq.blockdrop;

import java.util.List;

import de.innoq.blockdrop.algo.Operation;
import de.innoq.blockdrop.algo.heuristics.BoardScoreHeuristic;
import de.innoq.blockdrop.algo.heuristics.Heuristic;
import de.innoq.blockdrop.model.Point;

/**
 * A TetrisListener that uses a Heuristic to create command for the server 
 *
 */
public class HeuristicTetrisListener implements TetrisListener {

	private TetrisServer server;
	// TODO Use a better heuristic here
	private Heuristic  heuristic = new BoardScoreHeuristic();

	@Override
	public void setServer(TetrisServer server) {
		this.server = server;
		
	}

	@Override
	public void next(Point[] currentBlock, Point[] fixed) {
		List<Operation> operations = heuristic.calculateMoves(currentBlock, fixed);
		System.out.println ("Operatiosn to send is "+ operations);
		server.doOperations(operations);
	}

}
