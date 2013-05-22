package fr.umlv.escapeig.world;

import org.jbox2d.common.MathUtils;

/**
 * An ennemy ship
 *
 */
public class EnnemyShip extends Ship {
	
	private static final int GROUP_INDEX = Board.WALL_INDEX;
	private static final float ANGLE = MathUtils.PI;
	private boolean isDead;
	
	EnnemyShip(float x, float y,
			DescriptionShip sd) {
		super(x, y, ANGLE, sd, GROUP_INDEX);
	}
	
	@Override
	public void fire(float theta) {
		if (isDead) return;
		loadWeapon();
		weapon.fire(3*(MathUtils.PI/2));
	}

	@Override
	public Type getType() {
		return Actor.Type.ENNEMY;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void touch(Actor actor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void resetWeapon() {
		// TODO Auto-generated method stub
		
	}
	
	
}
