package fr.umlv.escapeig.world;

import org.jbox2d.common.Vec2;


class Missile extends Weapon {
	
	private final Vec2 impulse = new Vec2();

	@Override
	public boolean fire(float x, float y) {
		insertInWorld();
		impulse.set(x,y);
		body.applyLinearImpulse(impulse, body.getWorldCenter());
		return true;
	}

}
