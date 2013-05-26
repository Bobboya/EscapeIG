package fr.umlv.escapeig;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import fr.umlv.escapeig.builder.BuilderWorld;
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

		System.out.println("GAME ACTIVITY");
		
<<<<<<< HEAD
		Intent intent = new Intent(this, ImportLevelListActivity.class);
		startActivityForResult(intent, 1);
	}

	private void initiate(String path) {
		board = Board.createBoard(this, path);

=======
		board = Board.createBoard(this,"/mnt/sdcard/test21.lvl");
		
>>>>>>> Nico
		boardView = new BoardView(this, board);

		mView = new GestureView(this);
		mView.setGestureVisible(false);
		mView.addOnGesturePerformedListener(GestureHandler.self);
		mView.addView(boardView);
		setContentView(mView);

		board.play();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) { // Activity for add ship
			if(resultCode == RESULT_OK){
				String result = data.getStringExtra("result");
				initiate(result);
			}
			else {
				// probleme result result code ou valeurs x et y
				return;
			}
		} 
	}
}