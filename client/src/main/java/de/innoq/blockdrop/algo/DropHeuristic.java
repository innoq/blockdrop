package de.innoq.blockdrop.algo;

import java.util.List;

import de.innoq.blockdrop.model.Point;

public interface DropHeuristic {
	public List<Operation> calculateMoves (Point[] currentBlock, Point[] fixed);
	
}
