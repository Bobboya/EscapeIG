package fr.umlv.escapeig.world;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import android.util.Log;
import fr.umlv.escapeig.builder.PointFL;

/**
 * An ennemy ship
 *
 */
public class EnnemyShip extends Ship {
	
	private static final int GROUP_INDEX = Board.WALL_INDEX;
	private static final float ANGLE = 0;
	private boolean isDead;
	ArrayList<PointFL> gesture;
	
	@Override
	public Vec2 getPosition () {
		Log.d("Ennemy", "pos: "+super.getPosition());
		return super.getPosition();
	}
	
	@Override
	public Vec2 getTopLeft() {
		Log.d("Ennemy", "tl: "+super.getTopLeft());
		return super.getTopLeft();
	}
	
	@Override
	public Vec2 getBottomRight () {
		Log.d("Ennemy", "br: "+super.getBottomRight());
		return super.getBottomRight();
	}

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
		tl.set(sd.topLeft.x, sd.topLeft.y);
		br.set(sd.bottomRight.x, sd.bottomRight.y);
		
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
