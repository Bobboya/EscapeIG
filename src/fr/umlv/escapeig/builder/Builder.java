package fr.umlv.escapeig.builder;

import java.util.ArrayList;

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

/**
 * @author louis
 *
 */
public class Builder extends SurfaceView {

	public static final float START_POSITION = 690;
	public static final float LEFT_POSITION = 0;
	public static final int TIME_LONG_PRESS = 500;
	public static final int MAX_ENNEMIES = 25;

	protected Context context;

	protected Bitmap bitmap1;

	protected ArrayList<Float> listTop;
	protected float topBegin;

	protected float bitmapHeight;
	protected float screenHeight;

	// y value in left bottom
	protected float yValue = 0;

	// Save the date of world under definition
	protected World world;
	
	// Manage gesture scroll and long pressed
	protected GestureDetector mGestureDetector;
	protected GestureDetector.SimpleOnGestureListener mScrollHandler = new GestureDetector.SimpleOnGestureListener() {

		boolean isScrolling = true;
		
		@Override
		public boolean onDown(MotionEvent e) {
			
			return true;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// Move all top	
			scrollMoveElement(distanceY);
			// If first top is return to the beginning
			if(listTop.get(0) <= topBegin) {
				scrollReturnBeginning();
			}
			// Can not acces to postInvalidate in abstract so use reDraw
			reDraw();
			// Log.d("GestureDetector", "Scroll " + distanceY);
			return true;
		}

		@Override
		public void onLongPress(MotionEvent event) {
			// Print windowPopup to choose the ennemie's ship
			float posY = screenHeight - event.getY() + yValue;
			System.out.println(posY);
			((BuilderActivity)context).startPopupActivity(event.getX(), posY);
			// Log.d("GestureDetector", "LongPress");
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

		world = new World(context);
		
		listTop = new ArrayList<Float>();


		// Init gestureDetector
		mGestureDetector = new GestureDetector(context, mScrollHandler);
		//screenHeight = START_POSITION;

		// System.out.println("size of screen " + screenHeight);
		callback();
	}
	
	public void initiateWithWorld(World w) {
		this.world = w;
		
		setBackground(w.background);
	}

	protected boolean setBackground(Bitmap bitmap) {
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

			// System.out.println("Top value = " + top);
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
					
					// System.out.println("Add top nb = " + nb);
					
					listTop.add(f - bitmapHeight);
				}
			}
		}
		
		// For ship on the background
		ArrayList<Ship> ships = world.ships;
		int nbShip = ships.size();
		for(int i = 0; i < nbShip; i++) {
			Ship ship = ships.get(i);
			float yShip = ship.yActual;
			
			// System.out.println("----- DRAW SHIP -----");
			// System.out.println(convertYtoDisplay(yShip));
			
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
	
	protected void addShip(String valueOfDrawable, float x, float y) {
		if(world.ships.size() >= MAX_ENNEMIES) {
			Toast.makeText(context, "Nombre maximum d'ennemies atteint", Toast.LENGTH_LONG).show();
			return;
		}
		Bitmap bit = BitmapFactory.decodeResource(getResources(), Integer.parseInt(valueOfDrawable));
		Ship ship = new Ship();
		ship.bitmap = rotate(bit, 180);
		int halfHeight = bit.getHeight() / 2;
		int halfWidth = bit.getWidth() / 2;		
		
		ship.x = x - halfWidth;
		ship.yActual = y + halfHeight;
		ship.yOrigin = y + halfHeight;
		
		// System.out.println("----- Ship -----");
		// System.out.println("X value = " + x);
		// System.out.println("yActual value = " + ship.yActual);
		
		world.ships.add(ship);
		// System.out.println("Nombre de ship = " + world.ships.size());
		reDraw();
	}
	
	private float convertYtoDisplay(float f) {
		float ret = screenHeight - (f - yValue);
		return ret;
	}
}