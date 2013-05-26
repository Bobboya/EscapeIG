package fr.umlv.escapeig.world;

import android.graphics.Bitmap;

public class ScrollingBackground {
	
	public final Bitmap bitmap;
	public float currentY;
	public final int layerHeight;
	public final int layerWidth;
	
	private float scrollSpeed;
	
	/**
	 * @param image the image to scroll
	 * @param width the layer with
	 * @param height the layer height
	 * @param scrollSpeed the delta between steps in pixel
	 */
	ScrollingBackground(Bitmap bitmap, int width, int height, float scrollSpeed) {
		this.bitmap = bitmap;
		this.layerHeight = height;
		this.layerWidth = width;
		this.currentY = 0;
		this.scrollSpeed = scrollSpeed;
	}
	
	public void step () {
		currentY += scrollSpeed;
	}
	
	public void reset() {
		currentY = 0;
	}
}
