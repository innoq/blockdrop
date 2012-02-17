package de.innoq.blockdrop.algo.heuristics;

import java.util.Arrays;
import java.util.List;

import de.innoq.blockdrop.algo.HeightScoreFunction;
import de.innoq.blockdrop.algo.Operation;
import de.innoq.blockdrop.algo.ScoreFunction;
import de.innoq.blockdrop.model.Point;
import static de.innoq.blockdrop.algo.Util.*;

/**
 * A Heuristic that tries each possible Position and Orientation for a tile and
 * scores the Result using a Score Function. the best Result is taken.
 * 
 */
public class BoardScoreHeuristic implements Heuristic {

	// Score Function can later be replaced with other ones
	private ScoreFunction scoreFunction = new HeightScoreFunction();

	@Override
	public List<Operation> calculateMoves(Point[] currentBlock, Point[] fixed) {
		// Wir verzichten zunäcsht auf rotieren und probieren nur jede Mögliche
		// Situation aus.

		Point minCoords = minCoords(currentBlock);
		Point maxCoods = maxCoords(currentBlock);

		byte[][][] board = pointsToGrid(fixed);
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				// Move Block to x,y
				int xOffset = x - minCoords.x;
				int yOffset = y - minCoords.y;

				byte[][][] newBoard = dropBlockOnOffset(board, currentBlock,
						xOffset, yOffset);

			}
		}

		// 1. currentBlock normaliziere vordere, linke Untere ecke der convexen
		// Hülle is 0/0/0
		// 2. Erzeuge rotationen des Blocks ?
		// For each possible Orientation
		// TODO Auto-generated method stub
		return null;
	}




	}

	

}
