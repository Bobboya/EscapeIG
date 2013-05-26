package fr.umlv.escapeig.builder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Environment;
import android.widget.Toast;
import fr.umlv.escapeig.world.ShipType;

public class BuilderWorld implements Serializable {

	private static final long serialVersionUID = 962633937908070466L;
	private static final ShipType shipTypes[] = ShipType.values();

	public Bitmap background;
	public ArrayList<BuilderShip> ships;
	public float screenWidth;
	public float screenHeight;

	private Context context;
	
	public BuilderWorld(Context context) {
		ships = new ArrayList<BuilderShip>();
		this.context = context;
	}

	public void exportLevel(String nameFile, float screenWidth, float screenHeight) {
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
					BuilderShip ship = ships.get(i);
					oos.writeFloat(ship.x);
					oos.writeFloat(ship.yOrigin);
					oos.writeObject(ship.gesture);
					oos.writeInt(ship.ordinal);
					oos.flush();
				}
				//oos.writeFloat(screenWidth);
				//oos.writeFloat(screenHeight);
				oos.flush();
				
				oos.close();
			} else {
				Toast.makeText(context, "Impossible d'exporter le niveau", Toast.LENGTH_LONG).show();
			}
		}
		catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	public static BuilderWorld importLevel(String path, Context context) {
		FileInputStream file = null;
		ObjectInputStream ois = null;
		try {
			file = new FileInputStream(path);
			ois = new ObjectInputStream(file);

			BuilderWorld world = new BuilderWorld(context);
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
				int ordinal = ois.readInt();
				Bitmap bitShip = BitmapFactory.decodeResource(context.getResources(), getImg(ordinal));
				Bitmap bitShipRotate = Builder.rotate(bitShip, 180);
				BuilderShip ship = new BuilderShip(x, y, bitShipRotate, ordinal, gesture);
				world.ships.add(ship);
			}
			//world.screenWidth = ois.readFloat();
			//world.screenHeight = ois.readFloat();

			return world;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			if (ois != null)
				try {
					ois.close();
				} catch (IOException e) {}
			if (file != null)
				try {
					file.close();
				} catch (IOException e) {}
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
	
	private static int getImg(int ordinal) {
		if (ordinal < shipTypes.length) return shipTypes[ordinal].desc.image;
		return Integer.MIN_VALUE;
	}
}