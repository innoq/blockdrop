package de.innoq.blockdrop.algo.heuristics;

import java.util.List;

import de.innoq.blockdrop.algo.Operation;
import de.innoq.blockdrop.model.Point;

public interface Heuristic {
	public List<Operation> calculateMoves (Point[] currentBlock, Point[] fixed);
	
}
