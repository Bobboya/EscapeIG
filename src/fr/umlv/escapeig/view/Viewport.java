package fr.umlv.escapeig.view;

import org.jbox2d.common.Vec2;

public class Viewport {
	
	private boolean valid;
	private boolean yFlip;
	private float scaleX;
	private float scaleY;

	private final int worldWidth;
	private final int worldHeight;
	private int screenWidth;
	private int screenHeight;
	
	public Viewport(int worldWidth, int worldHeight, int screenWidth, int screenHeight, boolean yFlip) {
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.valid = false;
		this.yFlip = yFlip;
		update();
	}
	
	public Viewport(int worldWidth, int worldHeight, int screenWidth, int screenHeight) {
		this(worldWidth, worldHeight, screenWidth, screenHeight, true);
	}
	
	public boolean getWorldToScreen(Vec2 in, Vec2 out) {
		if (!valid) return false;
		out.x = getWorldXToScreen(in.x);
		out.y = getWorldYToScreen(in.y);
		return true;
	}
	
	public float getWorldYToScreen(float y) {
		float val = y*scaleY;
		if (yFlip) val = screenHeight-val;
		return val;
	}
	
	public float getWorldXToScreen(float x) {
		return x*scaleX;
	}
	
	public void update (int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		update();
	}
	
	private void update () {
		if (screenWidth == 0 ||
			screenHeight == 0 ||
			worldWidth == 0 ||
			worldHeight == 0)
		{
			valid = false;
			return;
		}
		
		if (worldWidth > screenWidth)
			scaleX = (float)worldWidth / screenWidth;
		else
			scaleX = (float)screenWidth / worldWidth;
		
		if (worldHeight > screenHeight)
			scaleY = (float)worldHeight / screenHeight;
		else
			scaleY = (float)screenHeight / worldHeight;
		
		valid = true;
	}
	
	public int getWorldWidth() {
		return worldWidth;
	}

	public int getWorldHeight() {
		return worldHeight;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setYFlip(boolean b) {
		yFlip = b;
	}

}
