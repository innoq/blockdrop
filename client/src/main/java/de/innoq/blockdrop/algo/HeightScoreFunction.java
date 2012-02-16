package de.innoq.blockdrop.algo;

import de.innoq.blockdrop.model.Point;

/**
 * A Score Function that penalizes height.
 */
public class HeightScoreFunction implements ScoreFunction {

	@Override
	public int score(Point[] board) {
		// Arbitrary factor to give room for variattions
		return getHeight (board)*100;

	}

	protected int getHeight(Point[] board) {
		int height = 0;
		for (Point point : board) {
			height = Math.max(height, point.z);
		}
		return height;
	}

}
