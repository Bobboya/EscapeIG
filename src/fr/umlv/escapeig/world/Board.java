package fr.umlv.escapeig.world;


import java.util.ArrayList;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Class handling the representation and movement of the actors in the world
 *
 */
public class Board {
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = 1000;

	/**
	 * Number of Steps per second
	 */
	public static final int STEP_PER_SECOND = 60;
	/**
	 * Group index of the walls blocking the player
	 */
	public static final int WALL_INDEX = -2;
	private static final float TIME_STEP = 1.0f/STEP_PER_SECOND;
	private static final int VELOCITY_ITERATIONS = 4;
	private static final int POSITION_ITERATIONS = 2;

	final World world;
	public final ArrayList<Actor> actors;
	private ShipFactory sf;

	private Board (int width, int height) {
		this.world = new World(new Vec2(0,0));
		this.actors = new ArrayList<Actor>();
	}

	/**
	 * Make the world move by a step
	 * @return the 
	 */
	public boolean step() {
		for (int i=0; i<actors.size(); ++i) actors.get(i).step();
		world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		return true;
	}
	
	private void createWall (float x, float y, float width, float height) {
		final BodyDef wallBodyDef = new BodyDef();
		final FixtureDef wallFixtureDef = new FixtureDef();
		wallFixtureDef.restitution = 0.25f;
		wallFixtureDef.friction = 1f;
		wallFixtureDef.filter.groupIndex = WALL_INDEX;
		
		final EdgeShape groundShape = new EdgeShape();
		groundShape.set(new Vec2(0,0), new Vec2(width, height));
		wallBodyDef.position.set(x, y);
		
		final Body groundBody = world.createBody(wallBodyDef);
		wallFixtureDef.shape = groundShape;
		groundBody.createFixture(wallFixtureDef);
	}
	
	public ShipFactory getShipFactory () {
		return sf;
	}
	
	private static Board create(int width, int height) {
		Board board = new Board(width, height);
		board.createWall(0, 0, width, 0);
		board.createWall(0, height, width, 0);
		board.createWall(0, 0, 0, height);
		board.createWall(width, 0, 0, height);
		board.sf = new ShipFactory(board);
		return board;
	}
	
	public static Board create () {
		return create(Board.WIDTH, Board.HEIGHT);
	}
}
