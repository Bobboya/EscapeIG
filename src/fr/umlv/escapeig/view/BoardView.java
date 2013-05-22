package fr.umlv.escapeig.view;

import org.jbox2d.common.Vec2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import fr.umlv.escapeig.gesture.GestureHandler;
import fr.umlv.escapeig.world.Actor;
import fr.umlv.escapeig.world.Board;
import fr.umlv.escapeig.world.ScrollingBackground;

public class BoardView extends SurfaceView implements SurfaceHolder.Callback {

	private final Board board;
	private final Viewport viewportTransform;
	private final BitmapManager bm;
	private GestureDetector gestureDetector;
	
	private final Rect dstRect = new Rect();
	private final Rect srcRect = new Rect();
	private final Vec2 topLeft = new Vec2();
	private final Vec2 bottomRight = new Vec2();
	
	public BoardView(Context ctx, Board game) {
		super(ctx);
		this.board = game;
		this.viewportTransform = new Viewport(Board.WIDTH, Board.HEIGHT, 0, 0);
		this.bm = new BitmapManager(getResources());
		this.gestureDetector = new GestureDetector(ctx, GestureHandler.self);
		getHolder().addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int arg1, int width, int height) {
		viewportTransform.update(width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		setWillNotDraw(false);
		board.register(this);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		return gestureDetector.onTouchEvent(event);
	}


	@Override
	protected void onDraw(Canvas painter) {
		viewportTransform.setYFlip(false);
		for (ScrollingBackground sb : board.backgrounds) {
			int screenY = 0;
			int imgMin = (int)(sb.layerHeight-viewportTransform.getWorldHeight()-sb.currentY);
			int imgMax =  (int) (sb.layerHeight-sb.currentY);
			
			if (imgMin < 0)  {
				screenY = (int)viewportTransform.getWorldYToScreen(-imgMin);
				srcRect.set(0, sb.layerHeight+imgMin, sb.layerWidth, sb.layerHeight);
				dstRect.set(0, 0, viewportTransform.getScreenWidth(), screenY);
				painter.drawBitmap(bm.getBitmap(sb.img), srcRect, dstRect, null);
				if (-imgMin >= viewportTransform.getWorldHeight()) sb.reset();
			}
			
			srcRect.set(0, imgMin, sb.layerWidth, imgMax);
			dstRect.set(0, screenY, viewportTransform.getScreenWidth(), viewportTransform.getScreenHeight());
			painter.drawBitmap(bm.getBitmap(sb.img), srcRect, dstRect, null);
			Log.d("Rect", srcRect.toShortString()+" / "+dstRect.toShortString());
		}
		
		viewportTransform.setYFlip(true);
		for (Actor act : board.actors) {
			Vec2 tl = act.getTopLeft();
			Vec2 br = act.getBottomRight();
			viewportTransform.getWorldToScreen(tl, topLeft);
			viewportTransform.getWorldToScreen(br, bottomRight);
			dstRect.set((int)topLeft.x, (int)topLeft.y, (int)bottomRight.x, (int)bottomRight.y);
			painter.drawBitmap(bm.getBitmap(act.getImage()),null, dstRect, null);
		}
	}
}
