package fr.umlv.escapeig.world;

import java.util.ArrayList;

import org.jbox2d.dynamics.BodyType;

import android.graphics.PointF;

/**
 * An ennemy ship
 *
 */
public class EnnemyShip extends Ship {
	
	private static final int GROUP_INDEX = Board.WALL_INDEX;
	private static final float ANGLE = 0;
	private boolean isDead;
	ArrayList<PointF> gesture;
	Board board;

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
		board.actors.remove(this);
		board.world.destroyBody(body);
	}
	
	@Override
	public float getAngle() {
		return super.getAngle()+180;
	}
	
	void init (float x, float y, DescriptionShip sd) {
		tl.set(sd.topLeft.x, sd.topLeft.y);
		br.set(sd.bottomRight.x, sd.bottomRight.y);
		
		bdef.type = BodyType.DYNAMIC;
		bdef.position.set(x, y);
		bdef.angle = ANGLE;
		
		fdef.restitution = 0;
		fdef.density = 0.00001f;
		fdef.shape = sd.shape;
		fdef.filter.groupIndex = GROUP_INDEX;
		
		shipDescription = sd;
	}
	
	
}
