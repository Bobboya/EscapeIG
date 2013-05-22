package fr.umlv.escapeig.view;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import fr.umlv.escapeig.Game;

public class BoardView extends SurfaceView implements SurfaceHolder.Callback{

	private final Game game;
	
	public BoardView(Game game) {
		super(game);
		this.game = game;
		getHolder().addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int arg1, int width, int height) {
		game.updateViewport(width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		setWillNotDraw(false);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent me){
		Log.d("COORDS", "clickx : "+me.getX()+" / clicky : "+me.getY());
		game.touch(me);
		postInvalidate();
		return true;
	}


	@Override
	protected void onDraw(Canvas canvas) {
		game.draw(canvas);
	}
}
