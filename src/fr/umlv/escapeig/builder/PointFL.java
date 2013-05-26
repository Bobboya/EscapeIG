package fr.umlv.escapeig.builder;

import java.io.Serializable;

public class PointFL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8866929654940141637L;
	
	public float x;
	public float y;
	
	public PointFL(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
