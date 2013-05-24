package fr.umlv.escapeig.world;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.escapeig.weapon.Weapon;

public abstract class Ship implements Actor {
	
	protected final BodyDef bdef = new BodyDef();
	protected final FixtureDef fdef = new FixtureDef();
	private final Vec2 tl = new Vec2();
	private final Vec2 br = new Vec2();
	
	Body body;
	Weapon weapon;
	DescriptionShip shipDescription;
	
	protected Ship () {
		
	}
	
	protected Ship (
			float x, float y, float angle,
			DescriptionShip sd, int groupIndex) {
		
		bdef.type = BodyType.DYNAMIC;
		bdef.position.set(x, y);
		bdef.angle = angle;
		
		fdef.restitution = 0;
		fdef.density = 0;
		fdef.shape = sd.shape;
		fdef.filter.groupIndex = groupIndex;
		
		shipDescription = sd;
	}
	
	public Vec2 getPosition () {
		return body.getPosition();
	}
	
	@Override
	public int getGroup() {
		return fdef.filter.groupIndex;
	}
	
	@Override
	public Vec2 getTopLeft () {
		Vec2 pos = body.getPosition();
		tl.x = pos.x+shipDescription.topLeft.x;
		tl.y = pos.y+shipDescription.topLeft.y;
		return tl;
	}
	
	@Override
	public Vec2 getBottomRight () {
		Vec2 pos = body.getPosition();
		br.x = pos.x+shipDescription.bottomRight.x;
		br.y = pos.y+shipDescription.bottomRight.y;
		return br;
	}
	
	@Override
	public float getAngle() {
		return body.getAngle();
	}

	public void fire(float theta) {
		weapon.fire(theta);
	}
	
	protected void kill () {
	}
	
	@Override
	public int getImage() {
		return shipDescription.image;
	}

}