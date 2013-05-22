package fr.umlv.escapeig.gl;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import fr.umlv.escapeig.Game;
import fr.umlv.escapeig.world.Board;

public class View extends GLSurfaceView {
	
	private final fr.umlv.escapeig.gl.Renderer mRenderer;

	public View(Game game){
		super(game);
		setEGLContextClientVersion(2);
		
		mRenderer = new fr.umlv.escapeig.gl.Renderer(game);
		setRenderer(mRenderer);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		if (e.getAction() == MotionEvent.ACTION_MOVE) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				//shut up!
			}
		}
	    return true;
	}
	
}