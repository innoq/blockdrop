package de.innoq.blockdrop.algo;

import java.util.Arrays;

import de.innoq.blockdrop.model.Point;

public class Util {

	/**
	 * Convert from Array of point into  a 3d array of bytes
	 * @param points
	 * @return
	 */
	public static byte[][][] pointsToGrid(Point[] points) {
		byte[][][] result = new byte[5][5][10];
		for (Point point : points) {
			result[point.x][point.y][point.z] = 1;
		}
		return result;
	}
	
	
	public static  byte[][][] copyBoard (byte[][][] board) {
		byte[][][] result = Arrays.copyOf(board,board.length);
		for (int x = 0;x  < result.length ; x++) {
			result [x] = Arrays.copyOf (result[x],result[x].length);
			for (int y = 0; y < result.length ; y++) {
				result[x][y] = Arrays.copyOf (result[x][y], result[x][y].length);
			}
		}		
		return result;
	}
	
	
	/**
	 * Move the Block by xOffset,yOfsset and drop in on the fixed Tiles. Return
	 * a new Baord representing the Result;
	 * 
	 * @param board
	 * @param currentBlock
	 * @param xOffset
	 * @param yOffset
	 * @return
	 */
	private byte[][][] dropBlockOnOffset(byte[][][] board, Point[] currentBlock,
			int xOffset, int yOffset) {
		
		byte[][][] result = Arrays.copyOf(board,board.length);
	//	Point[] result = new Point[board.length + currentBlock.length];

		// Point]
		// System.arraycopy(paramObject1, paramInt1, paramObject2, paramInt2,
		// paramInt3)
		// TODO Auto-generated method stub
		return null;
		
	}
	
	
	public static Point minCoords(Point[] points) {
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

	public static Point maxCoords(Point[] points) {
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
