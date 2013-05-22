package fr.umlv.escapeig.weapon;

import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.world.Actor;
import fr.umlv.escapeig.world.Actor.Type;


/**
 * Implements this interface to create a new weapon
 *
 */
public interface Weapon {
	public boolean fire(float theta);
	public Type getType();
	public Vec2 getPosition();
	public float getAngle();
	public void touch(Actor actor);
	public void step();
}
