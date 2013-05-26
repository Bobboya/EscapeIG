package fr.umlv.escapeig;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.gesture.GestureOverlayView;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import fr.umlv.escapeig.gesture.GestureHandler;
import fr.umlv.escapeig.view.BoardView;
import fr.umlv.escapeig.view.GestureView;
import fr.umlv.escapeig.world.Board;

public class GameActivity extends Activity {
	
	Board board;
	BoardView boardView;
	GestureOverlayView mView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//Fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		GestureHandler.self.loadGesture(this, R.raw.gestures);
		
		board = Board.create();
		board.getShipFactory().getHeroShip();
		board.createBackground(BitmapFactory.decodeResource(getResources(), R.drawable.moon),
				BitmapFactory.decodeResource(getResources(), R.drawable.moon).getWidth(),
				BitmapFactory.decodeResource(getResources(), R.drawable.moon).getHeight());
		
		boardView = new BoardView(this, board);
		
        mView = new GestureView(this);
        mView.setGestureVisible(false);
        mView.addOnGesturePerformedListener(GestureHandler.self);
        mView.addView(boardView);
        setContentView(mView);
        
		board.play();
	}

}