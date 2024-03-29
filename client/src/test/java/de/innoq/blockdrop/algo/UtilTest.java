package de.innoq.blockdrop.algo;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import de.innoq.blockdrop.model.Point;
import static de.innoq.blockdrop.model.Point.*;

public class UtilTest {

	@Test
	public void testCopy() {
		
		byte[][][] board = new byte[5][5][10];
		
		for (int x= 0; x < 5; x++) {
			for (int y=0; y < 5; y++ ){
				Arrays.fill(board[x][y], (byte) 5);		
			}
			
		}
		
		byte[][][] result = Util.copyBoard(board);
		
		// Original �berschreiben
		for (int x= 0; x < 5; x++) {
			for (int y=0; y < 5; y++ ){
				Arrays.fill(board[x][y], (byte) 7);		
			}
			
		}		
		// Then
		assertEquals (result[4][4][9], 5);
		assertEquals (result[0][4][9], 5);		
		assertEquals (result[0][4][0], 5);
		assertEquals (result[0][0][9], 5);		
	}
	
	
	@Test
	public void testTestFit() {
		byte[][][] board = new byte[5][5][10];
		
		board [0][0][0] = 1;
		
		Point[] block = new Point[]{
			point (4,1,10),
			point (4,0,10)
		};
				
		assertTrue (Util.testFits(board, block, 0, 0, 0));
		// �ber den rechten Rand hinaus
		assertFalse (Util.testFits(board, block, 1,0,0));
		// �ber den linke Rand
		assertFalse (Util.testFits(board, block, -5,0,0));
		
		assertTrue (Util.testFits(board, block, 0, 0, -1));
		
		assertTrue (Util.testFits(board, block, 0, 0, -9));
		// Der zweite Quader
		assertFalse (Util.testFits(board, block, 4, 0, -10));

	}
	
	@Test
	public void testBlockOnOffset(){
		// Start with an empty board
		byte[][][] board = new byte[5][5][10];
		
		// Lets drop a simple Block
	 	Point[] block = new Point[]{
				point (0,0,10)
			};
				
		board = Util.dropBlockOnOffset(board, block, 0, 0);
		// The BLock should now be on the bottom, other fields empty
		assertEquals (board[0][0][0],1);
		assertEquals (board[0][0][9],0);
		assertEquals (board[0][0][1],0);
		
		// Stack another bigger block onto:
	 	block = new Point[]{
				point (0,0,10),
				point (0,0,9),
				point (1,0,9),
				point (2,0,9)				
			};
				
		board = Util.dropBlockOnOffset(board, block, 0, 0);
		assertEquals (board[0][0][0],1); // The first Block we droped
		// The second one stacked upon:
		assertEquals (board[0][0][1],1);
		assertEquals (board[0][0][2],1);
		assertEquals (board[1][0][1],1);
		assertEquals (board[2][0][1],1);
		
		assertEquals (board[3][0][1],0);		
	}
}
