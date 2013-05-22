package fr.umlv.escapeig.view;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseIntArray;

public class BitmapManager {
	
	private SparseIntArray imageBitmapPosition;
	private ArrayList<Bitmap> bitmaps;
	private Resources resources;
	
	public BitmapManager (Resources r) {
		resources = r;
		bitmaps = new ArrayList<Bitmap>();
		imageBitmapPosition = new SparseIntArray();
	}
	
	public Bitmap getBitmap (int image) {
		int pos = imageBitmapPosition.get(image, -1);
		if (pos != -1) return bitmaps.get(pos);
		
		Bitmap bitmap = BitmapFactory.decodeResource(resources, image);
		bitmaps.add(bitmap);
		imageBitmapPosition.append(image, bitmaps.indexOf(bitmap));
		return bitmap;
	}

}
