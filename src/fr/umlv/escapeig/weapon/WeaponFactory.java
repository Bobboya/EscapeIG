package fr.umlv.escapeig.weapon;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.world.Ship;

abstract class WeaponFactoryDescription {
	protected int image;
	protected int icon;
	protected Shape shape;
	protected Vec2 topLeft;
	protected Vec2 bottomRight;
	
	int image() {return image;}
	int icon() {return icon;}
	Shape shape() {return shape;}
	Vec2 topLeft() {return topLeft;}
	Vec2 bottomRight() {return bottomRight;}
}

/**
 * Create weapons
 *
 */
public class WeaponFactory {
	
	/**
	 * Create a weapon for a ship
	 * @param type type of weapon to create
	 * @param ship the ship getting the weapon
	 * @return the created weapon
	 * @throws WeaponCreationException
	 */
	public static Weapon create(WeaponType type, Ship ship) throws WeaponCreationException {
		switch (type) {
//		case FIREBALL:
//			return new Fireball(ship, ship.group());
//		case LASER:
//			return new Laser(ship, ship.group());
//		case MISSILE:
//			return new Missile(ship, ship.group());
//		case SHIBOLEET:
//			return new Shiboleet(ship, ship.group());
		default:
			throw new WeaponCreationException();
		}
	}

}
