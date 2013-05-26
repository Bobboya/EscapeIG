package fr.umlv.escapeig;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Button gameButton = (Button)findViewById(R.id.buttonGame);
		gameButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View vw) {
				Intent intent = new Intent(context, GameActivity.class);
				startActivity(intent);
			}
		});
		
		Button builderButton = (Button)findViewById(R.id.buttonBuilder);
		builderButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View vw) {
				Intent intent = new Intent(context, BuilderActivity.class);
				startActivity(intent);
			}
		});
	}
}