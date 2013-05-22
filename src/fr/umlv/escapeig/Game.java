package fr.umlv.escapeig;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import fr.umlv.escapeig.view.BoardView;
import fr.umlv.escapeig.world.Board;

public class Game extends Activity {
	
	private Board board;
	private BoardView boardView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//Fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		board = Board.create();
		board.getShipFactory().createHeroShip(100);
		board.createBackground(R.drawable.moon,
				BitmapFactory.decodeResource(getResources(), R.drawable.moon).getWidth(),
				BitmapFactory.decodeResource(getResources(), R.drawable.moon).getHeight());
		
		boardView = new BoardView(this, board);
		setContentView(boardView);
		
		board.play();
	}

}