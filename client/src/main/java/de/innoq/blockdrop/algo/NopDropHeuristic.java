package de.innoq.blockdrop.algo;

import java.util.Collections;
import java.util.List;

import de.innoq.blockdrop.model.Point;

public class NopDropHeuristic implements DropHeuristic {

	@Override
	public List<Operation> calculateMoves(Point[] currentBlock, Point[] fixed) {
		System.out.println ("Heuristic was asked to handle block ");
		renderBlock (currentBlock) ;
		
		return Collections.singletonList(Operation.drop);
	}

	private void renderBlock(Point[] currentBlock) {
		
		int[][] condensedView = new int[5][5];
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				condensedView[x][y] = 0;
			}
		}
		
		for (Point point : currentBlock) {
			condensedView[point.x][ point.y] = Math.max(condensedView[point.x][ point.y],point.z);
		}
		
		
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
			
				System.out.printf("%3d",condensedView[x][y]);
			}
			System.out.println();
		}
	}

}
