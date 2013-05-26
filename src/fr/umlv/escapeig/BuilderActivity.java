package fr.umlv.escapeig;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import fr.umlv.escapeig.builder.Builder;
import fr.umlv.escapeig.builder.BuilderWorld;

public class BuilderActivity extends Activity {

	protected Builder builder;
	protected float x;
	protected float y;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		this.builder = new Builder(this);
		builder.setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.moon));

		setContentView(builder);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menuChangeBack:
			Intent intentBack = new Intent(this, ChooseBackListActivity.class);
			startActivityForResult(intentBack, 25);
			break;
		case R.id.menuSave :
			exportDialog();
			break;
		case R.id.menuLoad :
			Intent intentLoad = new Intent(this, ImportLevelListActivity.class);
			startActivityForResult(intentLoad, 50);
			break;	
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void startPopupActivity(float x, float y) {
		this.x = x;
		this.y = y;

		Intent intent = new Intent(this, PopupListActivity.class);
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) { // Activity for add ship
			if(resultCode == RESULT_OK && this.x != 0 && this.y != 0){
				String result = data.getStringExtra("result");
				builder.addShip(result, this.x, this.y);
				this.x = 0;
				this.y = 0;
			}
			else {
				// probleme result result code ou valeurs x et y
				return;
			}
		} else if(requestCode == 25) { // Activity to change background
			if(resultCode == RESULT_OK) {
				String result = data.getStringExtra("result");
				Bitmap bitmap = BitmapFactory.decodeFile(result);
				if(bitmap == null) {
					Toast.makeText(this, "Ce fichier n'est pas une image", Toast.LENGTH_LONG).show();
					return;
				}
				builder.setBackground(bitmap);
			}
			else {
				return;
			}
		} else if(requestCode == 50) { // Activity to load a level
			if(resultCode == RESULT_OK) {
				String result = data.getStringExtra("result");
				BuilderWorld world = BuilderWorld.importLevel(result, this);
				if(world == null) {
					Toast.makeText(this, "Impossible de charger ce niveau", Toast.LENGTH_LONG).show();
					return;
				}
				builder.initiateWithWorld(world);
			}
			else {
				return;
			}
		}
	}


	private void exportDialog() {

		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		final Builder builder = this.builder;
		
		dialog.setTitle("Sauvegarder le niveau");
		dialog.setMessage("Entrer le nom du fichier :");

		// Use an EditText view to get user input.
		final EditText input = new EditText(this);
		dialog.setView(input);

		dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				builder.world.exportLevel(value, builder.screenWidth, builder.screenHeight);
				return;
			}
		});

		dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});

		dialog.create().show();
	}
}	
