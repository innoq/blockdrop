package de.innoq.blockdrop.algo.heuristics;

import java.util.List;

import de.innoq.blockdrop.algo.HeightScoreFunction;
import de.innoq.blockdrop.algo.Operation;
import de.innoq.blockdrop.algo.ScoreFunction;
import de.innoq.blockdrop.model.Point;

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

		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				// Move Block to x,y
				int xOffset = x - minCoords.x;
				int yOffset = y - minCoords.y;

				Point[] newBoard = dropBlockOnOffset(fixed, currentBlock,
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

	private static byte[][][] pointsToGrid(Point[] points) {
		byte[][][] result = new byte[5][5][10];
		for (Point point : points) {
			result[point.x][point.y][point.z] = 1;
		}
		return result;
	}

	/**
	 * Move the Block by xOffset,yOfsset and drop in on the fixed Tiles. Return
	 * a new Baord representing the Result;
	 * 
	 * @param fixed
	 * @param currentBlock
	 * @param xOffset
	 * @param yOffset
	 * @return
	 */
	private Point[] dropBlockOnOffset(Point[] fixed, Point[] currentBlock,
			int xOffset, int yOffset) {
		Point[] result = new Point[fixed.length + currentBlock.length];

		// Point]
		// System.arraycopy(paramObject1, paramInt1, paramObject2, paramInt2,
		// paramInt3)
		// TODO Auto-generated method stub
		return null;
	}

	private static Point minCoords(Point[] points) {
		int mx = Integer.MAX_VALUE;
		int my = Integer.MAX_VALUE;
		int mz = Integer.MAX_VALUE;

		for (Point p : points) {
			mx = Math.min(p.x, mx);
			my = Math.min(p.y, my);
			mz = Math.min(p.z, mz);

		}
		return new Point(mx, my, mz);
	}

	private static Point maxCoords(Point[] points) {
		int mx = Integer.MIN_VALUE;
		int my = Integer.MIN_VALUE;
		int mz = Integer.MIN_VALUE;

		for (Point p : points) {
			mx = Math.max(p.x, mx);
			my = Math.max(p.y, my);
			mz = Math.max(p.z, mz);

		}
		return new Point(mx, my, mz);
	}
}
