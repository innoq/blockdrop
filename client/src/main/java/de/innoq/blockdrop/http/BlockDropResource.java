package de.innoq.blockdrop.http;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import com.sun.jersey.api.core.ResourceConfig;

import de.innoq.blockdrop.model.Point;

@Path("/")
public class BlockDropResource {

	@Context
	ResourceConfig config;
	
	@Path("board")
	@GET
	public String getCurrentBoard() {
		
		Point[] board = (Point[]) config.getProperty("board");
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		
		for (int i = 0; i < board.length; i++) {
			Point point = board [i];
			buffer.append ("[");
			buffer.append (point.x);
			buffer.append (",");
			buffer.append (point.y);
			buffer.append (",");
			buffer.append (point.z);
			buffer.append("]");
			if (i < board.length-1) {
				buffer.append (",");
			}
			
			
		}
		
		buffer.append("]");
		return (buffer.toString());
	}
}
