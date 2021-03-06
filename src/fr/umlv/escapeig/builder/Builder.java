package fr.umlv.escapeig.builder;

import java.util.ArrayList;

import fr.umlv.escapeig.BuilderActivity;
import fr.umlv.escapeig.R;
import fr.umlv.escapeig.world.ShipType;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;

public class Builder extends SurfaceView {

	public static final float START_POSITION = 690;
	public static final float LEFT_POSITION = 0;
	public static final int TIME_LONG_PRESS = 500;
	public static final int MAX_ENNEMIES = 25;
	public static final int MARGIN_DETECT_SHIP = 60; 

	protected Context context;

	protected Bitmap bitmap1;

	protected ArrayList<Float> listTop;
	protected float topBegin;

	protected float bitmapHeight;
	public float screenHeight;
	public float screenWidth;

	// y value in left bottom
	protected float yValue = 0;

	// Save the date of world under definition
	public BuilderWorld world;

	// Manage gesture scroll and long pressed
	protected GestureDetector mGestureDetector;
	protected GestureDetector.SimpleOnGestureListener mScrollHandler = new GestureDetector.SimpleOnGestureListener() {

		boolean onShip = false;
		BuilderShip ship = null;

		@Override
		public boolean onDown(MotionEvent e) {
			float yPos = screenHeight - e.getY() + yValue;
			float xPos = e.getX();
			for(int i = 0; i < world.ships.size(); i++) {
				BuilderShip s = world.ships.get(i);
				float xShip = s.x;
				float yShip = s.yOrigin;

				if(yShip > yPos - MARGIN_DETECT_SHIP && 
				   yShip < yPos + MARGIN_DETECT_SHIP &&
				   xShip > xPos - MARGIN_DETECT_SHIP &&
				   xShip < xPos + MARGIN_DETECT_SHIP) {
					onShip = true;
					ship = s;
					return true;
				}

			}
			onShip = false;
			ship = null;
			return true;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			if(!onShip) { // scrolling the screen
				// Move all top	
				scrollMoveElement(distanceY);
				// If first top is return to the beginning
				if(listTop.get(0) <= topBegin) {
					scrollReturnBeginning();
				}
			}
			else { // Add a gesture for a ship	
				try {
					ship.gesture = new ArrayList<PointFL>();
					PointFL point = new PointFL(e2.getX(), e2.getY());
					ship.gesture.add(point);
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
			// Can not acces to postInvalidate in abstract so use reDraw
			reDraw();
			// Log.d("GestureDetector", "Scroll " + distanceY);
			return true;
		}

		@Override
		public void onLongPress(MotionEvent event) {
			if(!onShip) {
				// Print windowPopup to choose the ennemie's ship
				float posY = screenHeight - event.getY() + yValue;
				((BuilderActivity)context).startPopupActivity(event.getX(), posY);
				// Log.d("GestureDetector", "LongPress");
			}
			else {
				for(int i = 0; i < world.ships.size(); i++) {
					if(ship.equals(world.ships.get(i))) {
						world.ships.remove(i);
						reDraw();
					}
				}
			}
		}
	};

	private void reDraw() {
		this.postInvalidate();
	}

	public Builder(Context context) {
		super(context);
		this.context = context;
		//DisplayMetrics dm = context.getResources().getDisplayMetrics(); 
		// screenHeight = dm.ydpi;
		// don't work value not determinate yet

		world = new BuilderWorld(context);

		listTop = new ArrayList<Float>();


		// Init gestureDetector
		mGestureDetector = new GestureDetector(context, mScrollHandler);
		//screenHeight = START_POSITION;

		callback();
	}

	public void initiateWithWorld(BuilderWorld w) {
		this.world = w;

		setBackground(w.background);
	}

	public boolean setBackground(Bitmap bitmap) {
		if(bitmap == null) throw new IllegalStateException("Image vide");
		if(this.bitmap1 == bitmap) return false;
		try {
			callback();
			bitmap1 = bitmap;
			bitmapHeight = bitmap.getHeight();
			world.background = bitmap;
			yValue = 0;

			// Add fisrt value of top in listTop and fixed fisrt value of top
			float f = -(bitmapHeight - START_POSITION);
			listTop.add(f);
			topBegin = f;

			reDraw();
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	/*
	 * turn a bitmap of value x
	 */
	protected static Bitmap rotate(Bitmap bit, float x)
	{
		int width = bit.getWidth();
		int height = bit.getHeight();

		float[] mirrorY = 
			{  -1, 0, 0, 
				0, 1, 0,  
				0, 0, 1    
			};
		Matrix matrixMirrorY = new Matrix();
		matrixMirrorY.setValues(mirrorY);

		// calculate the scale - in this case = 0.4f
		Matrix matrix = new Matrix();

		matrix.postRotate(x);
		matrix.postConcat(matrixMirrorY);

		Bitmap rotateBitmap = Bitmap.createBitmap(bit, 0, 0,width, height, matrix, true);
		return rotateBitmap;
	}


	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int nb = listTop.size();
		// For background
		for(int i = 0; i < nb; i++) {
			float f = listTop.get(i);

			if(i > topBegin && i < screenHeight) {

				canvas.drawBitmap(bitmap1, LEFT_POSITION, f, null);
				if(i == nb - 1 && Math.abs(f) < bitmapHeight) {

					listTop.add(f - bitmapHeight);
				}
			}
		}

		// For ship on the background
		ArrayList<BuilderShip> ships = world.ships;
		int nbShip = ships.size();
		for(int i = 0; i < nbShip; i++) {
			BuilderShip ship = ships.get(i);
			float yShip = ship.yActual;

			if(yShip > yValue && yShip < yValue + screenHeight) {
				ship.draw(canvas, convertYtoDisplay(yShip));
			}
		}
	}

	private void callback(){
		getHolder().addCallback(new Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
			}


			@Override
			public void surfaceCreated(SurfaceHolder arg0) {
				setWillNotDraw(false);
			}

			@Override
			public void surfaceChanged(SurfaceHolder arg0, int arg1, int width, int height){  
				screenHeight = height;
				screenWidth = width;
			}
		});	
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

	/**
	 * Run through listTop and move all of them
	 * @param move 
	 */
	private void scrollMoveElement(float move) {

		yValue -= move;
		if(yValue < 0) {
			scrollReturnBeginning();
		}

		int nbBackground = listTop.size();
		for(int i = 0; i < nbBackground; i++) {
			listTop.set(i, listTop.get(i) - move);
		}

		/*int nbShip = world.ships.size();
		for(int i = 0; i < nbShip; i++) {
			world.ships.get(i).yActual -= move;
		}*/
	}

	private void scrollReturnBeginning() {

		yValue = 0;

		int nbBackground = listTop.size();
		for(int i = 0; i < nbBackground; i++) {
			float f = topBegin + (-i) * bitmapHeight;
			listTop.set(i, f);
		}

		/*int nbShip = world.ships.size();
		for(int i = 0; i < nbShip; i++) {
			world.ships.get(i).yActual = world.ships.get(i).yOrigin;
		}*/
	}

	public void addShip(String valueOfDrawable, float x, float y) {
		if(world.ships.size() >= MAX_ENNEMIES) {
			Toast.makeText(context, "Nombre maximum d'ennemies atteint", Toast.LENGTH_LONG).show();
			return;
		}
		Bitmap bit = BitmapFactory.decodeResource(getResources(), Integer.parseInt(valueOfDrawable));
		BuilderShip ship = new BuilderShip();
		ship.bitmap = rotate(bit, 180);
		int halfHeight = bit.getHeight() / 2;
		int halfWidth = bit.getWidth() / 2;	
		
		ship.ordinal = getOrdinalShip(valueOfDrawable);
		ship.x = x - halfWidth;
		ship.yActual = y + halfHeight;
		ship.yOrigin = y + halfHeight;

		world.ships.add(ship);
		reDraw();
	}
	
	private int getOrdinalShip(String value) {
		if(value.equals(String.valueOf(R.drawable.ship1))) {
			return ShipType.JUPITER.ordinal();
		}
		else if(value.equals(String.valueOf(R.drawable.ship3))) {
			return ShipType.EARTH.ordinal();
		} 
		else if(value.equals(String.valueOf(R.drawable.ship4))) {
			return ShipType.MOON.ordinal();
		}
		return Integer.MIN_VALUE;
	}

	private float convertYtoDisplay(float f) {
		float ret = screenHeight - (f - yValue);
		return ret;
	}
}