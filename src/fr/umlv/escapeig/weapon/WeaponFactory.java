package fr.umlv.escapeig.weapon;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.world.Ship;

abstract class DescriptionWeapon {
	int image;
	int icon;
	Shape shape;
	Vec2 topLeft;
	Vec2 bottomRight;
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
			case FIREBALL:
				return new Fireball();
			case LASER:
				return new Laser();
			case MISSILE:
				return new Missile();
			case SHIBOLEET:
				return new Shiboleet();
			default:
				throw new WeaponCreationException();
		}
	}

}
