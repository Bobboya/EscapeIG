package fr.umlv.escapeig.world;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.escapeig.weapon.Weapon;

public abstract class Ship implements Actor {
	
	protected final BodyDef bdef;
	protected final FixtureDef fdef;
	private final Vec2 tl;
	private final Vec2 br;
	
	final DescriptionShip shipDescription;
	
	Body body;
	Weapon weapon;
	
	protected Ship (
			float x, float y, float angle,
			DescriptionShip sd, int groupIndex) {
		
		bdef = new BodyDef();
		bdef.type = BodyType.DYNAMIC;
		bdef.position.set(x, y);
		bdef.angle = angle;
		
		fdef = new FixtureDef();
		fdef.restitution = 0;
		fdef.density = 0;
		fdef.shape = sd.shape;
		fdef.filter.groupIndex = groupIndex;
		
		shipDescription = sd;
		tl = new Vec2();
		br = new Vec2();
	}
	
	public Vec2 getPosition () {
		return body.getPosition();
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
	
	public float getAngle() {
		return body.getAngle();
	}

	public void fire(float theta) {
		if (weapon.fire(theta))
			resetWeapon();
	}

	public void loadWeapon() {
//		try {
//			setWeapon(WeaponFactory.create(weaponType, this));
//		} catch (WeaponCreationException e) {
//			setWeapon(fakeWeapon);
//		}
	}
	
	protected abstract void resetWeapon();
	
	protected void kill () {
		
	}
	
	@Override
	public int getImage() {
		return shipDescription.image;
	}

}