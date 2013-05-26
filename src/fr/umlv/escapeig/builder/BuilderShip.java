package fr.umlv.escapeig.builder;

import java.io.Serializable;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BuilderShip implements Serializable {

	private static final long serialVersionUID = -7577703160863033380L;
	
	public float x;
	
	public float yActual;
	public float yOrigin;
	
	public Bitmap bitmap;
	
	public int ordinal;
	
	public ArrayList<PointFL> gesture;
	
	public BuilderShip() {
		this(0, 0, null, 0, null);
	}
	
	public BuilderShip(float x, float y, Bitmap bitmap, int ordinal, ArrayList<PointFL> gesture) {
		this.x = x;
		this.yActual = y;
		this.yOrigin = y;
		this.bitmap = bitmap;
		this.ordinal = ordinal;
		this.gesture = gesture;
	}
	
	public void draw(Canvas canvas, float yBottom) {
		canvas.drawBitmap(bitmap, x, yBottom, null);
	}
}
