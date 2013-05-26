package fr.umlv.escapeig.world;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;


public abstract class Ship implements Actor {
	
	protected final BodyDef bdef = new BodyDef();
	protected final FixtureDef fdef = new FixtureDef();
	final Vec2 tl = new Vec2();
	final Vec2 br = new Vec2();
	
	Body body;
	Weapon weapon;
	DescriptionShip shipDescription;
	WeaponFactory weaponFactory;
	public WeaponType defaultWeapon;
	
	protected Ship () {
		
	}
	
	protected Ship (
			float x, float y, float angle,
			DescriptionShip sd, int groupIndex) {
		
		tl.set(sd.topLeft.x, sd.topLeft.y);
		br.set(sd.bottomRight.x, sd.bottomRight.y);
		
		bdef.type = BodyType.DYNAMIC;
		bdef.position.set(x, y);
		bdef.angle = angle;
		
		fdef.restitution = 0;
		fdef.density = 0.00001f;
		fdef.shape = sd.shape;
		fdef.filter.groupIndex = groupIndex;
		
		shipDescription = sd;
		defaultWeapon = sd.defaultWeapon;
	}
	
	@Override
	public Vec2 getPosition () {
		return body.getPosition();
	}
	
	@Override
	public int getGroup() {
		return fdef.filter.groupIndex;
	}
	
	@Override
	public Vec2 getTopLeft () {
		return tl;
	}
	
	@Override
	public Vec2 getBottomRight () {
		return br;
	}
	
	@Override
	public float getAngle() {
		return body.getAngle();
	}

	public void fire(float x, float y) {
		if (weapon == null) return;
		boolean b = weapon.fire(x, y);
		if (b) {
			weapon = null;
		}
	}
	
	protected void kill () {
	}
	
	@Override
	public int getImage() {
		return shipDescription.image;
	}
	
	public void loadWeapon () {
		if (weapon != null) return;
		weapon = weaponFactory.create(defaultWeapon, this);
	}

}