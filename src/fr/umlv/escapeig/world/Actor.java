package fr.umlv.escapeig.world;

import org.jbox2d.common.Vec2;

/**
 * Implements this interface to be added in a GameWorld
 */
public interface Actor {
	
	/**
	 * 
	 * @return Type of the WorldActor
	 */
	Type getType();
	/**
	 * 
	 * @return position of the actor
	 */
	Vec2 getPosition();
	/**
	 * 
	 * @return angle of the actor
	 */
	float getAngle();
	void step();
	int getImage();
	int getGroup();
	void touch(Actor actor);
	
	/**
	 * Enum of all element of WorldActor
	 */
	public enum Type {
		/**
		 * Represent the Hero
		 */
		HERO,
		/**
		 * Represent all Enemy
		 */
		ENNEMY,
		/**
		 * Represent Bosses
		 */
		BOSS,
		/**
		 * Represent Bonus
		 */
		BONUS,
		/**
		 * Represent Weapons
		 */
		WEAPON
	}

	Vec2 getTopLeft();
	Vec2 getBottomRight();
}