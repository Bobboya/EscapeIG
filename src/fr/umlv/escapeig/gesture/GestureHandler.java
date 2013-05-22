package fr.umlv.escapeig.gesture;

import java.util.ArrayList;

import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class GestureHandler extends SimpleOnGestureListener {
	
	public static final GestureHandler self = new GestureHandler();
	
	public final ArrayList<SimpleOnGestureListener> listeners = new ArrayList<SimpleOnGestureListener>();
	
	private GestureHandler () {
		
	}
	
	@Override
	public boolean onDown (MotionEvent e) {
		return true;
	}
	
	@Override
	public boolean onScroll (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		for (SimpleOnGestureListener listener : listeners)
			listener.onScroll(e1, e2, distanceX, distanceY);
		return true;
	}
	
}