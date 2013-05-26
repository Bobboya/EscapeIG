package fr.umlv.escapeig.builder;

import java.io.Serializable;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;

public class Ship implements Serializable {

	private static final long serialVersionUID = -7577703160863033380L;
	
	public float x;
	
	public float yActual;
	public float yOrigin;
	
	public Bitmap bitmap;
	
	public ArrayList<PointF> gesture;
	
	public Ship() {
		this(0, 0, null, null);
	}
	
	public Ship(float x, float y, Bitmap bitmap, ArrayList<PointF> gesture) {
		this.x = x;
		this.yActual = y;
		this.yOrigin = y;
		this.bitmap = bitmap;
		this.gesture = gesture;
	}
	
	public void draw(Canvas canvas, float yBottom) {
		canvas.drawBitmap(bitmap, x, yBottom, null);
	}
}
