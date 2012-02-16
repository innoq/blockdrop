package de.innoq.blockdrop;

import de.innoq.blockdrop.model.Point;

public interface TetrisListener {

	
	/**
	 * Used to register the server to this listener
	 * @param server
	 */
	public void setServer(TetrisServer server);

	/**
	 * Will be called whenever a "next" block is recieved from the Server.
	 * 
	 * @param points
	 */
	public void next(Point[] currentBlock, Point[] fixed);
	

}
