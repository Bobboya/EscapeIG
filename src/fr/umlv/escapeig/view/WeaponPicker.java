package fr.umlv.escapeig.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import fr.umlv.escapeig.gesture.ComplexOnGestureListener;
import fr.umlv.escapeig.world.HeroShip;
import fr.umlv.escapeig.world.WeaponType;

class WeaponPicker extends ComplexOnGestureListener {
	
	private final Rect dstRect;
	private final float density;
	private final BitmapManager bm;
	private final WeaponType types[] = WeaponType.values();
	private final float ylimit;
	private final float xlimit;
	private final HeroShip ship;
	private final float ratio;
	
	WeaponPicker(Context ctx, BitmapManager bm, HeroShip s) {
		ship = s;
		density = ctx.getResources().getDisplayMetrics().density;
		dstRect = new Rect(0,0,(int)(30*density),(int)(30*density));
		ylimit = dstRect.bottom;
		xlimit = dstRect.right*types.length;
		this.bm = bm;
		ratio = 1/density/((float)dstRect.right/density);
	}
	
	void draw(Canvas painter) {
		for (int i=0; i < types.length; ++i) {
			painter.save();
			painter.translate(i*dstRect.right, 0);
			painter.drawBitmap(bm.getBitmap(types[i].wd.icon), null, dstRect, null);
			painter.restore();
		}	
	}
	
	@Override
	public boolean onSingleTapUp(MotionEvent e)  {
		float pos = e.getX()*ratio;
		if (e.getY() > ylimit || e.getX() > xlimit) return true;
		ship.defaultWeapon = types[(int)pos];
		Log.d("Weapon", ship.defaultWeapon.name());
		return false;
	}
}
