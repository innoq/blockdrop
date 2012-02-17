package de.innoq.blockdrop.algo;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

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
		
		// Original Ÿberschreiben
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
}
