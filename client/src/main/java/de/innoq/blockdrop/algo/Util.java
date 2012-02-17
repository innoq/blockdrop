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
	
	
	/**
	 * Create a deep copy of an board.
	 */
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
	 * Move the Block by xOffset, yOfsset and drop it on the fixed Tiles. Return
	 * a new Baord representing the Result;
	 * 
	 * @param board
	 * @param block
	 * @param xOffset
	 * @param yOffset
	 * @return
	 */
	public static byte[][][] dropBlockOnOffset(byte[][][] board, Point[] block,
			int xOffset, int yOffset) {
		
		byte[][][] result = Arrays.copyOf(board,board.length);
		
	
		int zOffset = 0;
		
		while (testFits (board, block, xOffset,yOffset,zOffset)) {
			zOffset -= 1;
		}
		
		zOffset +=1;
		
		// Merge the Block into the board on its final position
		
		for (Point p : block) {
			result [p.x+xOffset][p.y + yOffset][p.z+zOffset] = 1;
		}
		
		return result;
	}
	
	public static boolean testFits (byte[][][] board, Point[] block,int xOffset, int yOffset, int zOffset) {
		for (Point p : block) {
			int newX = p.x + xOffset;
			int newY = p.y + yOffset;
			int newZ = p.z + zOffset;
			if (newX < 0 || newX > 4 || newY < 0 || newY > 4 || newZ < 0) return false; // Ein Block ist nicht mehr in der Ebene
			if (newZ < 10) { // Was noch oben rausragt ist in Ordnung
				if (board [newX][newY][newZ] == 1) return false;
			}
		}
		return true;		
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
