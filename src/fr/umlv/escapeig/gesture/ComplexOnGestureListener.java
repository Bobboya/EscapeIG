package fr.umlv.escapeig.gesture;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public abstract class ComplexOnGestureListener extends SimpleOnGestureListener {
	
	@Override
	public boolean onDown (MotionEvent e) {
		return true;
	}
	
	public boolean onUp (MotionEvent e1) {
		return false;
	}
	
	public boolean onSquare () {
		return false;
	}
	
	public boolean onRing () {
		return false;
	}
	
}
