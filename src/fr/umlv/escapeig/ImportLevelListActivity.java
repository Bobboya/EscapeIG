package fr.umlv.escapeig;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import fr.umlv.escapeig.R;

public class ImportLevelListActivity extends ListActivity {

	protected File directory;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		directory = getPath();
		initiateAdapter(directory);
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
			if(f.isDirectory() ||
					isLevelFile(f)) {
				map = new HashMap<String, String>();
				map.put("name", f.getName());
				map.put("file", f.getAbsolutePath());
				listItem.add(map);
			}
		}

		SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.activity_choose_background,
				new String[] {"name"}, new int[] {R.id.backText});

		setListAdapter(mSchedule);
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
			setResult(RESULT_OK, returnIntent);     
			finish();
		}
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

	private boolean isLevelFile(File f) {
		if(!f.isFile()) return false;
		String name = f.getName();
		int length = name.length();
	
		if(length < 5) return false; // a good finish with ".lvl" so he needs 5 characters minimum
		String end = name.substring(length - 4, length);
		if(end.equals(".lvl")) {
			return true;
		}
		return false;
	}
}
