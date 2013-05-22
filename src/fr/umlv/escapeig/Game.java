package fr.umlv.escapeig;

import org.jbox2d.common.Vec2;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import fr.umlv.escapeig.view.BoardView;
import fr.umlv.escapeig.view.Viewport;
import fr.umlv.escapeig.world.Actor;
import fr.umlv.escapeig.world.Board;

public class Game extends Activity {

	private Board board;
	private BoardView boardView;
	private Viewport viewportTransform;
	private Rect drawRect;
	private Vec2 topLeft;
	private Vec2 bottomRight;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//Fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		boardView = new BoardView(this);
		setContentView(boardView);
		
		//GLView
		viewportTransform = new Viewport(Board.WIDTH, Board.HEIGHT, 0, 0);
		
		board = Board.create();
		board.getShipFactory().createHeroShip(100);
		
		drawRect = new Rect();
		topLeft = new Vec2();
		bottomRight = new Vec2();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	public void draw(Canvas painter) {
		//viewportTransform.setCenter(boardView.getWidth()/2, -boardView.getHeight()/2);
		for (Actor act : board.actors) {
			Vec2 tl = act.getTopLeft();
			Vec2 br = act.getBottomRight();
			viewportTransform.getWorldToScreen(tl, topLeft);
			viewportTransform.getWorldToScreen(br, bottomRight);
			drawRect.set((int)topLeft.x, (int)topLeft.y, (int)bottomRight.x, (int)bottomRight.y);
			painter.save();
			painter.drawBitmap(BitmapFactory.decodeResource(getResources(), act.getImage()),null, drawRect, null);
			painter.restore();
		}
	}
	
	public void updateViewport (int width, int height) {
		Log.d("COORDS", "Update viewport");
		viewportTransform.update(width, height);
	}

	public void touch(MotionEvent me) {
		
		Vec2 tmpIn = new Vec2(me.getX(), me.getY());
		Vec2 tmpOut = new Vec2();
		viewportTransform.getWorldToScreen(tmpIn, tmpOut);
		
		Log.d("COORDS", "clickx : "+me.getX()+" / clicky : "+me.getY());
		Log.d("COORDS", "convx : "+tmpOut.x+" / convy : "+tmpOut.y);
	}

}