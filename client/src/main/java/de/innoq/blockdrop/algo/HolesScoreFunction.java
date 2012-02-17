package de.innoq.blockdrop.algo;


/**
 * A Score Function that penalizes height.
 */
public class HolesScoreFunction implements ScoreFunction {

	int[] emptyStack = {0,0,0,0,0,0,0,0,0,0};
	
	@Override
	public int score(byte[][][] board) {
		// Arbitrary factor to give room for variattions
		return getNumberOfHoles (board)*100;

	}

	protected int getNumberOfHoles(byte[][][] board) {
		int numberOfHoles = 0;
		
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				int numberOfHolesPerStack = 0;
				boolean gotHole = false;
				for (int z = 0; z < 10; z++) {
					if (board[x][y][z] == 1 && gotHole) {
						gotHole = false;
						numberOfHolesPerStack++;
					}
					else if (board[x][y][z] == 0) {
						gotHole = true;
					}
				}
				numberOfHoles += numberOfHolesPerStack;
			}
		}
		
		return numberOfHoles;
	}

}
