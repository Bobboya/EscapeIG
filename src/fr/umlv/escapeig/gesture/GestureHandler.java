package fr.umlv.escapeig.gesture;

import java.util.ArrayList;

import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.util.Log;
import android.view.MotionEvent;

public class GestureHandler extends ComplexOnGestureListener implements OnGesturePerformedListener {
	
	public static final GestureHandler self = new GestureHandler();
	private static final double MIN_SCORE = 4;
	
	public final ArrayList<ComplexOnGestureListener> listeners = new ArrayList<ComplexOnGestureListener>();
	public GestureLibrary gLibrary;
	
	private GestureHandler () {
		
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

	@Override
	public void onGesturePerformed(GestureOverlayView arg0, Gesture gesture) {
		ArrayList<Prediction> predictions = gLibrary.recognize(gesture);
		double maxScore = 0;
		Prediction best =null;
		//Find the gesture with the best precision
		for (int i = 0; i<predictions.size(); ++i) {
			Prediction prediction = predictions.get(i);
			if (prediction.score > MIN_SCORE) {
				if (best == null) {
					best = prediction;
					maxScore = prediction.score;
				}
				if (prediction.score > maxScore) {
					maxScore = prediction.score;
					best = prediction;
				}
			}
		}
		
		//Match the gesture
		if (best != null) {
			Log.d("Gesture", best.name+" "+best.score);
			if (best.name.equalsIgnoreCase("Square")) {
				for(ComplexOnGestureListener listener : listeners) {
					listener.onSquare();
				}
			}
			if (best.name.equalsIgnoreCase("Ring")) {
				for(ComplexOnGestureListener listener : listeners) {
					listener.onRing();
				}
			}
		}
	}
	
	public void loadGesture (Context context, int gestureId) {
		gLibrary = GestureLibraries.fromRawResource(context, gestureId);
		if (!gLibrary.load()) Log.d("Gesture", "Can't load");
	}
	
	
	
}