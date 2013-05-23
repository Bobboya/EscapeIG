package fr.umlv.escapeig.view;

import org.jbox2d.common.Vec2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import fr.umlv.escapeig.world.Actor;
import fr.umlv.escapeig.world.Board;
import fr.umlv.escapeig.world.ScrollingBackground;

public class BoardView extends SurfaceView implements SurfaceHolder.Callback {

	private final Board board;
	private final Viewport viewportTransform;
	private final BitmapManager bm;
	
	
	private final Rect dstRect = new Rect();
	private final Vec2 topLeft = new Vec2();
	private final Vec2 bottomRight = new Vec2();
	
	public BoardView(Context ctx, Board game) {
		super(ctx);
		this.board = game;
		this.viewportTransform = new Viewport(Board.WIDTH, Board.HEIGHT, 0, 0);
		this.bm = new BitmapManager(getResources());
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
	protected void onDraw(Canvas painter) {
		viewportTransform.setYFlip(false);
		for (ScrollingBackground sb : board.backgrounds) {
			Bitmap b = bm.getBitmap(sb.img);
			float pos = -b.getHeight()+sb.currentY+viewportTransform.getScreenHeight();
			if (pos > 0) {
				painter.drawBitmap(b, 0, -b.getHeight()+pos, null);
				if (pos >= viewportTransform.getScreenHeight())
					sb.reset();
			}
			painter.drawBitmap(b, 0, pos, null);
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
