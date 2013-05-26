package fr.umlv.escapeig.world;

import org.jbox2d.common.MathUtils;
import org.jbox2d.dynamics.BodyType;

/**
 * An ennemy ship
 *
 */
public class EnnemyShip extends Ship {
	
	private static final int GROUP_INDEX = Board.WALL_INDEX;
	private static final float ANGLE = MathUtils.PI;
	private boolean isDead;

	@Override
	public void fire(float x, float y) {
		if (isDead) return;
		weapon.fire(x,y);
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
	
	void init (float x, float y, DescriptionShip sd) {
		bdef.type = BodyType.DYNAMIC;
		bdef.position.set(x, y);
		bdef.angle = ANGLE;
		
		fdef.restitution = 0;
		fdef.density = 0;
		fdef.shape = sd.shape;
		fdef.filter.groupIndex = GROUP_INDEX;
		
		shipDescription = sd;
	}
	
	
}
