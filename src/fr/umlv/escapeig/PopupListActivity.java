package fr.umlv.escapeig;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import fr.umlv.escapeig.R;

public class PopupListActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

		HashMap<String, String> map;

		
		map = new HashMap<String, String>();
		map.put("title", "Vaisseau 1");        
		map.put("img", String.valueOf(R.drawable.ship1));

		listItem.add(map);

		map = new HashMap<String, String>();
		map.put("title", "Vaisseau 2");
		map.put("img", String.valueOf(R.drawable.ship3));
		listItem.add(map);

		map = new HashMap<String, String>();
		map.put("title", "Vaisseau 3");
		map.put("img", String.valueOf(R.drawable.ship4));
		listItem.add(map);

		SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.activity_popup_choose_ship,
				new String[] {"img", "title"}, new int[] {R.id.img, R.id.title});

		setListAdapter(mSchedule);

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		@SuppressWarnings("unchecked")
		HashMap<String, String> map = (HashMap<String, String>) getListAdapter().getItem(position);
		
		 Intent returnIntent = new Intent();
		 returnIntent.putExtra("result", map.get("img"));
		 setResult(RESULT_OK,returnIntent);     
		 finish();
	}
} 