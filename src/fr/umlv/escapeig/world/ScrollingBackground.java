package fr.umlv.escapeig.world;

public class ScrollingBackground {
	
	public final int img;
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
	ScrollingBackground(int image, int width, int height, float scrollSpeed) {
		this.img = image;
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
