package fr.umlv.escapeig.weapon;

import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.world.Actor;
import fr.umlv.escapeig.world.Actor.Type;

/**
 * A weapon doing nothing
 *
 */
public class FakeWeapon implements Weapon {
	
	@Override
	public Type getType() {
		return Type.WEAPON;
	}
	@Override
	public Vec2 getPosition() {
		return new Vec2();
	}
	@Override
	public float getAngle() {
		return 0;
	}
	@Override
	public void touch(Actor actor) {}
	@Override
	public boolean fire(float theta) {return true;}
	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

}
