package fr.umlv.escapeig.gl;


/**
 * A layer scrolling an image
 */
public class ScrollingImage {
	
	private final Painter img;
	private final float scrollSpeed;
	
	/**
	 * @param image the image to scroll
	 * @param scrollSpeed the delta between steps in pixel
	 */
	ScrollingImage (Painter image, final float scrollSpeed) {
		this.img = image;
		this.scrollSpeed = scrollSpeed;
	}

	public void step() {
		img.move(0, scrollSpeed);
	}
	
}
