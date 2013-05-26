package fr.umlv.escapeig.world;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;



/**
 * Implements this interface to create a new weapon
 *
 */
public abstract class Weapon implements Actor {
	
	private final Vec2 position = new Vec2();
	
	
	final BodyDef bdef = new BodyDef();
	final FixtureDef fdef = new FixtureDef();
	final Vec2 tl = new Vec2();
	final Vec2 br = new Vec2();
	
	Board board;
	Ship ship;
	int image;
	Body body;
	DescriptionWeapon weaponDescription;
	
	WeaponFactory wf;
	WeaponType type;
	
	public abstract boolean fire(float x, float y);
	
	@Override
	public void touch (Actor act) {
		wf.pools.get(type.ordinal()).add(this);
		board.actors.remove(this);
	}
	
	@Override
	public void step () {
		//Default is to do nothing
	}
	
	@Override
	public Vec2 getPosition () {
		if (body != null) return body.getPosition();
		
		position.set(ship.getPosition().x,
				ship.getPosition().y+ship.getTopLeft().y+weaponDescription.topLeft.y);
		return position;
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
		return tl;
	}
	
	@Override
	public Vec2 getBottomRight () {
		return br;
	}
	
	@Override
	public float getAngle() {
		return (body == null ? ship.body.getAngle() : body.getAngle());
	}
	
	@Override
	public int getImage() {
		return weaponDescription.image;
	}
	
	void init (Ship ship, DescriptionWeapon wd) {
		tl.set(wd.topLeft.x, wd.topLeft.y);
		br.set(wd.bottomRight.x, wd.bottomRight.y);
		
		this.ship = ship;
		this.body = null;
		this.image = wd.image;
		this.weaponDescription = wd;

		bdef.type = BodyType.DYNAMIC;
		bdef.position = getPosition();
		bdef.angle = ship.getAngle();
		
		fdef.restitution = 0;
		fdef.density = 0.0001f;
		fdef.shape = wd.shape;
		fdef.filter.groupIndex = ship.getGroup();
	}
	
	protected void insertInWorld() {
		while (body == null) {
			body = ship.body.getWorld().createBody(bdef);
		}
		body.createFixture(fdef);
		body.m_userData = this;
	}
	
}
