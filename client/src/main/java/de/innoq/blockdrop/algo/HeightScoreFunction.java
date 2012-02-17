package de.innoq.blockdrop.algo;


/**
 * A Score Function that penalizes height.
 */
public class HeightScoreFunction implements ScoreFunction {

	@Override
	public int score(byte[][][] board) {
		// Arbitrary factor to give room for variattions
		return getHeight (board)*100;

	}

	protected int getHeight(byte[][][] board) {
		for (int z = 9; z >0; z--) {
			
			for (int x = 0; x < 5; x++ ){
				for (int y = 0; y < 5 ; y++) {
					if (board[x][y][z]==1) {
						return z;
					}
				}
			}			
		}
		return 0;
	}

}
