package fr.umlv.escapeig.weapon;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.escapeig.world.Actor;
import fr.umlv.escapeig.world.Ship;


/**
 * Implements this interface to create a new weapon
 *
 */
public abstract class Weapon implements Actor {
	
	static final Vec2 ORIGIN_VEC2 = new Vec2();
	
	final BodyDef bdef = new BodyDef();
	final FixtureDef fdef = new FixtureDef();
	final Vec2 tl = new Vec2();
	final Vec2 br = new Vec2();
	
	Ship ship;
	int image;
	Body body;
	DescriptionWeapon weaponDescription;
	
	public abstract boolean fire(float theta);
	
	@Override
	public Vec2 getPosition () {
		if (body != null) return body.getPosition();
		ORIGIN_VEC2.setZero();
		return ORIGIN_VEC2;
	}
	
	@Override
	public Type getType() {
		return Actor.Type.WEAPON;
	}
	
	@Override
	public int getGroup() {
		return fdef.filter.groupIndex;
	}
	
	@Override
	public Vec2 getTopLeft () {
		Vec2 pos = body.getPosition();
		tl.x = pos.x+weaponDescription.topLeft.x;
		tl.y = pos.y+weaponDescription.topLeft.y;
		return tl;
	}
	
	@Override
	public Vec2 getBottomRight () {
		Vec2 pos = body.getPosition();
		br.x = pos.x+weaponDescription.bottomRight.x;
		br.y = pos.y+weaponDescription.bottomRight.y;
		return br;
	}
	
	@Override
	public float getAngle() {
		return body.getAngle();
	}
	
	@Override
	public int getImage() {
		return weaponDescription.image;
	}
	
	void init (Ship ship, DescriptionWeapon wd) {
		this.ship = ship;
		this.body = null;
		this.image = wd.image;
		this.weaponDescription = wd;
		
		bdef.type = BodyType.DYNAMIC;
		bdef.position.set(ship.getPosition().x,
				ship.getTopLeft().y+(wd.topLeft.y/2));
		bdef.angle = ship.getAngle();
		
		fdef.restitution = 0;
		fdef.density = 0;
		fdef.shape = wd.shape;
		fdef.filter.groupIndex = ship.getGroup();
	}
	
}
