package de.innoq.blockdrop;

import java.util.List;

import de.innoq.blockdrop.algo.Operation;
import de.innoq.blockdrop.model.Point;

/**
 * An Abstraction of the tetris Server. TetrisListeners can use it to send command to the server
 *
 */
public interface TetrisServer {

	public void doOperations (List<Operation> operations);
	
	/**
	 * The Current piece to be manipulated
	 * @return
	 */
	public Point[] getCurrentPiece();
	
	/**
	 * The Pieces at the "bottom".
	 * @return
	 */
	public Point[] getFixed();

}
