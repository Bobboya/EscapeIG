package fr.umlv.escapeig.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import fr.umlv.escapeig.R;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ChooseBackListActivity extends ListActivity {

	protected File directory;
	protected File directoryPictures;
	protected File directoryToShow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		directory = getPath();
		initiateAdapter(directory);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		@SuppressWarnings("unchecked")
		HashMap<String, String> map = (HashMap<String, String>) getListAdapter().getItem(position);
		File f = new File(map.get("file"));
		
		if(f.isDirectory()) {
			initiateAdapter(f);
			return;
		} 
		
		if(f.isFile()) {
			Intent returnIntent = new Intent();
			returnIntent.putExtra("result", map.get("file"));
			setResult(RESULT_OK,returnIntent);     
			finish();
		}
	}

	private void initiateAdapter(File directory) {
		if(directory.isFile()) {
			throw new IllegalArgumentException();
		}
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

		// Add parent
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", "...");
		map.put("file", directory.getParent());
		listItem.add(map);

		File[] filesArray = directory.listFiles();
		for(int i = 0; i < filesArray.length; i++) {
			File f = filesArray[i];
			map = new HashMap<String, String>();
			map.put("name", f.getName());
			map.put("file", f.getAbsolutePath());
			listItem.add(map);
		}
		SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.activity_choose_background,
				new String[] {"name"}, new int[] {R.id.backText});

		setListAdapter(mSchedule);
	}

	private File getPath() {

		File ret;

		String state = Environment.getExternalStorageState();
		// If external storage is enable
		if (Environment.MEDIA_MOUNTED.equals(state) || 
				Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			ret = Environment.getExternalStorageDirectory();
			return ret;
		} 

		// Else use root
		ret = new File("/");
		return ret;
	}
}
