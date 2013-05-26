package fr.umlv.escapeig.world;


import java.util.ArrayList;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.Log;
import fr.umlv.escapeig.builder.BuilderShip;
import fr.umlv.escapeig.builder.BuilderWorld;
import fr.umlv.escapeig.view.BoardView;

/**
 * Class handling the representation and movement of the actors in the world
 *
 */
public class Board {
	
	public static final int WIDTH = 100;
	public static final int HEIGHT = 150;

	/**
	 * Number of Steps per second
	 */
	public static final int STEP_PER_SECOND = 60;
	/**
	 * Group index of the walls blocking the player
	 */
	public static final int WALL_INDEX = -2;
	private static final int TIME_STEP = (int)(1.0/STEP_PER_SECOND*1000f);
	private static final int VELOCITY_ITERATIONS = 2;
	private static final int POSITION_ITERATIONS = 2;
	private static final float BACKGROUND_SPEED = 2;

	private boolean running;
	private final Thread play;
	final World world;
	
	public final ArrayList<Actor> actors;
	public final ArrayList<ScrollingBackground> backgrounds;
	
	private ShipFactory sf;
	private WeaponFactory wf;
	
	private BoardView listener;
	private final Body groundBody;

	private Board (int width, int height) {
		
		this.world = new World(new Vec2(0,0));
		world.setContactListener(new WorldContactListener());
		this.actors = new ArrayList<Actor>(64);
		this.backgrounds = new ArrayList<ScrollingBackground>();
		this.running = false;
		this.groundBody = world.createBody(new BodyDef());
		
		play = new Thread (new Runnable() {
			@Override
			public void run() {
				while (!Thread.interrupted()) {
					step();
					try {
						Thread.sleep(TIME_STEP);
					} catch (InterruptedException e) {
						//silence
					}
				}
			}
		});
	}

	/**
	 * Make the world move by a step
	 * @return the 
	 */
	public boolean step() {
		for (int i=0; i<backgrounds.size(); ++i) backgrounds.get(i).step();
		for (int i=0; i<actors.size(); ++i) actors.get(i).step();
		world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		if (listener != null) listener.postInvalidate();
		return true;
	}
	
	private void createWall (float x1, float y1, float x2, float y2) {
		final FixtureDef wallFixtureDef = new FixtureDef();
		wallFixtureDef.restitution = 0;
		wallFixtureDef.friction = 0;
		wallFixtureDef.filter.groupIndex = WALL_INDEX;
		
		final EdgeShape groundShape = new EdgeShape();
		groundShape.set(new Vec2(x1,y1), new Vec2(x2, y2));
		
		wallFixtureDef.shape = groundShape;
		groundBody.createFixture(wallFixtureDef);
	}
	
	public ShipFactory getShipFactory () {
		return sf;
	}
	
	public WeaponFactory getWeaponFactory () {
		return wf;
	}
	
	public void createBackground(Bitmap image, int width, int height) {
		ScrollingBackground sb = new ScrollingBackground(image, width, height, BACKGROUND_SPEED);
		backgrounds.add(sb);
	}
	
	private static Board create(int width, int height) {
		Board board = new Board(width, height);
		board.createWall(0, 0, width, 0);
		board.createWall(0, 0, 0, height);
		board.createWall(0, height, width, height);
		board.createWall(width, 0, width, height);
		
		board.sf = new ShipFactory(board);
		board.wf = new WeaponFactory(board);
		return board;
	}
	
	public static Board create () {
		return create(Board.WIDTH, Board.HEIGHT);
	}
	
	public void play () {
		if (running) return;
		play.start();
		running = true;
	}
	
	public void pause () {
		if (!running) return;
		play.interrupt();
		running = false;
	}
	
	public void register (BoardView view) {
		listener = view;
	}
	
	public HeroShip getHero () {
		return getShipFactory().getHeroShip();
	}
	
	public static Board createBoard (Context ctx, String file) {
		Board board = Board.create();
		BuilderWorld toLoad = BuilderWorld.importLevel(file, ctx);
		
		board.createBackground(toLoad.background,
				toLoad.background.getWidth(),
				toLoad.background.getHeight());
		
		toLoad.screenHeight = 1100;
		toLoad.screenWidth = 700;
		
		ShipType shipTypes[] = ShipType.values();
		ShipFactory sf = board.getShipFactory();
		float widthRatio = Board.WIDTH/toLoad.screenWidth;
		float heightRatio = Board.HEIGHT/toLoad.screenHeight;
		for (BuilderShip s : toLoad.ships) {
			float x = s.x*widthRatio;
			float y = s.yOrigin*heightRatio+Board.HEIGHT;
			EnnemyShip ennemy = sf.createEnnemy(shipTypes[s.ordinal], x, y);
			if (s.gesture != null) {
				for (PointF p : s.gesture) {
					p.x = p.x*widthRatio;
					p.y = p.y*heightRatio;
				}
			}
			ennemy.gesture = s.gesture;
			if (ennemy.body == null) Log.d("Init", "body null");
			ennemy.body.applyForceToCenter(new Vec2(0,-2));
		}
		board.getShipFactory().getHeroShip();
		
		return board;
	}
}
