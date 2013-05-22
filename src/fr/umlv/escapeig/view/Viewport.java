package fr.umlv.escapeig.view;

import org.jbox2d.common.Vec2;

public class Viewport {
	
	private boolean valid;
	private float scaleX;
	private float scaleY;
	
	private final int worldWidth;
	private final int worldHeight;
	private int screenWidth;
	private int screenHeight;
	
	public Viewport(int worldWidth, int worldHeight, int screenWidth, int screenHeight) {
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.valid = false;
		update();
	}
	
	public boolean getWorldToScreen(Vec2 in, Vec2 out) {
		if (!valid) return false;
		out.x = Math.round(in.x*scaleX);
		out.y = Math.round(screenHeight-in.y*scaleY);
		return true;
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

}
