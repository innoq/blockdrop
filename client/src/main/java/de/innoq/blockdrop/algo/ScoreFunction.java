package de.innoq.blockdrop.algo;


public interface ScoreFunction {

	/**
	 * Return a score value for a board. Bigger values are worse.
	 * 
	 * @param board
	 * @return
	 */
	public int score(byte[][][] board);
}
