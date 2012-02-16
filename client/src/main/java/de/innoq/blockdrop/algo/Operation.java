package de.innoq.blockdrop.algo;

public enum Operation {
	
	plusX ("+x"),
	minusX ("-x"),

	plusY ("+y"),
	minusY ("-y"),
	
	plusRotX ("+rx"),
	minusRotX ("-rx"),
	
	plusRotY ("+ry"),
	minusRotY ("-ry"),
	
	plusRotZ ("+rz"),
	minusRotZ ("-rz");
	
	private String representation;

	
	Operation (String representation) {
		this.representation = representation;
	}
	
	public String representation() {
		return representation;
	}
}
