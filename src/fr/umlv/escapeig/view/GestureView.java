package fr.umlv.escapeig.view;

import fr.umlv.escapeig.gesture.GestureHandler;
import android.content.Context;
import android.gesture.GestureOverlayView;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureView extends GestureOverlayView {
	
	private GestureDetector gestureDetector;
	
	public GestureView(Context context) {
		super(context);
		this.gestureDetector = new GestureDetector(context, GestureHandler.self);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		boolean b = super.onTouchEvent(event);
		if (event.getAction() == MotionEvent.ACTION_UP)
			return GestureHandler.self.onUp(event);
		gestureDetector.onTouchEvent(event);
		return b;
	}

}
