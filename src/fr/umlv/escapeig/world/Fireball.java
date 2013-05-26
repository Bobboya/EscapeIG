package fr.umlv.escapeig.world;

import org.jbox2d.common.Vec2;

import android.util.Log;


class Fireball extends Weapon {

	private static final int LOADING_TIME = 5*Board.STEP_PER_SECOND;

	private int current;
	private final Vec2 impulse = new Vec2();
	private final Vec2 temptl = new Vec2();
	private final Vec2 tempbr = new Vec2();
	
	@Override
	public boolean fire(float x, float y) {
		if (current < LOADING_TIME) return false;
		fdef.shape.m_radius = current*DescriptionFireball.RADIUS/LOADING_TIME;
		insertInWorld();
		impulse.set(x,y);
		body.applyLinearImpulse(impulse, body.getWorldCenter());
		return true;
	}
	
	@Override
	public Vec2 getPosition() {
		Log.d("Weapon", super.getPosition().toString());
		return super.getPosition();
	}
	
	@Override
	public Vec2 getTopLeft () {
		temptl.x = -current*DescriptionFireball.RADIUS/LOADING_TIME;
		temptl.y = current*DescriptionFireball.RADIUS/LOADING_TIME;
		return temptl;
	}
	
	@Override
	public Vec2 getBottomRight () {
		tempbr.x = current*DescriptionFireball.RADIUS/LOADING_TIME;
		tempbr.y = -current*DescriptionFireball.RADIUS/LOADING_TIME;
		return tempbr;
	}
	
	@Override
	public void step() {
		if (current < LOADING_TIME) current++;
	}

	@Override
	public float getAngle() {
		if (body != null) return body.getAngle();
		return 0;
	}

	@Override
	public void init(Ship ship, DescriptionWeapon wd) {
		super.init(ship, wd);
		bdef.userData = this;
		current = 0;
	}
}
