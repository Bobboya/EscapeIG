package fr.umlv.escapeig.world;

import fr.umlv.escapeig.gesture.GestureHandler;

/**
 * A factory creating ships
 *
 */
public class ShipFactory {
	
	private final Board board;
	private HeroShip hero;
	
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
	public HeroShip getHeroShip () {
		if (hero != null) return hero;
		HeroShip ship = new HeroShip();
		ship.weaponFactory = board.getWeaponFactory();
		insertInWorld(ship);
		GestureHandler.self.listeners.add(ship.gestureListener);
		hero = ship;
		return ship;
	}
	
	EnnemyShip createEnnemy(ShipType type, float x, float y, boolean insert) {
		EnnemyShip ship = new EnnemyShip();
		ship.weaponFactory = board.getWeaponFactory();
		ship.init(x, y, type.desc());
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
	public EnnemyShip createEnnemy(ShipType type, float x, float y) {
		return createEnnemy(type, x, y, true);
	}
	
	void insertInWorld(Ship p) {
		p.body = board.world.createBody(p.bdef);
		p.body.createFixture(p.fdef);
		p.body.m_userData = p;
		board.actors.add(p);
	}
}