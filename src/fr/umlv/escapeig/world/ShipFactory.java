package fr.umlv.escapeig.world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

abstract class DescriptionShip {
	int image;
	PolygonShape shape;
	Vec2 topLeft;
	Vec2 bottomRight;
	Actor.Type actor;
}

/**
 * A factory creating ships
 *
 */
public class ShipFactory {
	
	private final Board board;
	
	/**
	 * @param board the world to create the weapons on
	 */
	ShipFactory (Board board) {
		this.board = board;
	}
	
	/**
	 * Create a hero ship in the GameWorld
	 * @param maxLife life of the hero ship
	 * @return the hero ship
	 */
	public HeroShip createHeroShip (int maxLife) {
		HeroShip ship = new HeroShip(maxLife);
		insertInWorld(ship);
		return ship;
	}
	
	EnnemyShip createEnnemy(ShipType type, float x, float y, boolean insert) throws ShipCreationException {
		EnnemyShip ship = new EnnemyShip(x, y, type.desc());
		if (insert)insertInWorld(ship);
		return ship;
	}
	
	/**
	 * Create ennemy on the world
	 * @param type type of ennemy
	 * @param x position in the world
	 * @param y position in the world
	 * @return the xreated ship
	 * @throws ShipCreationException
	 */
	public EnnemyShip createEnnemy(ShipType type, float x, float y) throws ShipCreationException {
		return createEnnemy(type, x, y, true);
	}
	
	void insertInWorld(Ship p) {
		p.body = board.world.createBody(p.bdef);
		p.body.createFixture(p.fdef);
		p.body.m_userData = p;
		board.actors.add(p);
	}
}