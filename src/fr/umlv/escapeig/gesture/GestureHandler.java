package fr.umlv.escapeig.gesture;

import java.util.ArrayList;

import android.view.MotionEvent;

public class GestureHandler extends ComplexOnGestureListener {
	
	public static final GestureHandler self = new GestureHandler();
	
	public final ArrayList<ComplexOnGestureListener> listeners = new ArrayList<ComplexOnGestureListener>();
	
	private GestureHandler () {
		
	}
	
	@Override
	public boolean onDown (MotionEvent e) {
		return true;
	}
	
	@Override
	public boolean onScroll (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		for (ComplexOnGestureListener listener : listeners)
			listener.onScroll(e1, e2, distanceX, distanceY);
		return true;
	}
	
	@Override
	public boolean onUp (MotionEvent e1) {
		for (ComplexOnGestureListener listener : listeners)
			listener.onUp(e1);
		return true;
	}
	
}