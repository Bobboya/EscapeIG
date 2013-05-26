package fr.umlv.escapeig.view;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
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
	private final WeaponPicker wp;
	
	private final Rect dstRect = new Rect();
	private final Vec2 position = new Vec2();
	private final Vec2 topLeft = new Vec2();
	private final Vec2 bottomRight = new Vec2();
	
	public BoardView(Context ctx, Board game) {
		super(ctx);
		this.board = game;
		this.viewportTransform = new Viewport(Board.WIDTH, Board.HEIGHT, 0, 0);
		this.bm = new BitmapManager(getResources());
		this.wp = new WeaponPicker(ctx,bm, board.getHero());
		getHolder().addCallback(this);
		GestureHandler.self.listeners.add(wp);
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
		
		for (Actor act : board.actors) {
			painter.save();
			Vec2 pos = act.getPosition();
			viewportTransform.getWorldToScreen(pos, position);
			painter.translate(position.x, position.y);
			painter.rotate(act.getAngle()+MathUtils.PI*MathUtils.RAD2DEG);
			
			Vec2 tl = act.getTopLeft();
			Vec2 br = act.getBottomRight();
			viewportTransform.getWorldToScreen(tl, topLeft, false);
			viewportTransform.getWorldToScreen(br, bottomRight, false);
			dstRect.set((int)topLeft.x, (int)topLeft.y, (int)bottomRight.x, (int)bottomRight.y);
			painter.drawBitmap(bm.getBitmap(act.getImage()),null, dstRect, null);
			painter.restore();
		}
		
		wp.draw(painter);
	}
}
