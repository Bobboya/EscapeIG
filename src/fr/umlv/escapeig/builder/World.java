package fr.umlv.escapeig.builder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.widget.Toast;

public class World implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 962633937908070466L;

	public Bitmap background;
	public ArrayList<Ship> ships;

	private Context context;

	public World(Context context) {
		ships = new ArrayList<Ship>();
		this.context = context;
	}

	public void exportLevel(String nameFile) {
		try {
			File ret = Environment.getExternalStorageDirectory();
			String state = Environment.getExternalStorageState();

			if (Environment.MEDIA_MOUNTED.equals(state)) {
				// We can read and write the media
				String path = ret.getAbsolutePath() + "/" + nameFile + ".lvl";

				FileOutputStream file = new FileOutputStream(path);
				ObjectOutputStream oos = new ObjectOutputStream(file);

				// oos.writeObject(new BitmapSerializable.BitmapDataObject());

				// Add bitmap of background
				// BitmapSerializable.writeBitmap(oos, background);
				MyBitmap mBitBack = new MyBitmap(getBytes(background));
				oos.writeObject(mBitBack);
				oos.flush();

				// Send number of ship for facilitate reading
				int nbShip = ships.size();
				oos.writeInt(nbShip);
				oos.flush();

				// Add all ships
				for(int i = 0; i < nbShip; i++) {
					Ship ship = ships.get(i);
					oos.writeFloat(ship.x);
					oos.writeFloat(ship.yOrigin);
					oos.writeObject(ship.gesture);
					MyBitmap mBitShip = new MyBitmap(getBytes(ship.bitmap));
					oos.writeObject(mBitShip);
					oos.flush();
				}
				oos.close();
			} else {
				Toast.makeText(context, "Impossible d'exporter le niveau", Toast.LENGTH_LONG).show();
			}
		}
		catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	public static World importLevel(String path, Context context) {
		try {
			System.out.println(path);
			FileInputStream file = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(file);

			World world = new World(context);
			// First read a bitmap
			// world.background = bs.readObject(ois);
			// Bitmap bitmap = BitmapSerializable.readBitmap(ois);
			MyBitmap mBitBack = (MyBitmap)ois.readObject();
			Bitmap bitBack = getBitmap(mBitBack.getBitmapBytes());
			world.background = bitBack;

			// Number of ship
			int nbShip = ois.readInt();

			for(int i = 0; i < nbShip; i++) {
				float x = ois.readFloat(); 
				float y = ois.readFloat();
				@SuppressWarnings("unchecked") // safe cast
				ArrayList<PointF> gesture = (ArrayList<PointF>)ois.readObject();
				MyBitmap mBitShip = (MyBitmap)ois.readObject();
				Bitmap bitShip = getBitmap(mBitShip.getBitmapBytes());
				Ship ship = new Ship(x, y, bitShip, gesture);
				world.ships.add(ship);
			}

			return world;

		} catch (Exception e) {
			System.out.println("erreur");
			e.printStackTrace();
			return null;
		} 
	}

	public static Bitmap getBitmap(byte[] data) {
		return BitmapFactory.decodeByteArray(data, 0, data.length);
	}

	public static byte[] getBytes(Bitmap bitmap) {
		ByteArrayOutputStream baops = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, baops);
		return baops.toByteArray();
	}
}