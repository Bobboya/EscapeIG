package fr.umlv.escapeig.world;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.R;

class Laser extends Weapon {
	
	private static final int SPEED = 3;
	private static final int LOADING_TIME = 5;
	private static final float ROTATION_SPEED = MathUtils.PI;
	
	private final int imageLoading;
	private final int imageLoaded;
	
	private int current;
	private final Vec2 impulse;
	
	private final Vec2 temptl = new Vec2();
	private final Vec2 tempbr = new Vec2();
	
	Laser () {
		this.imageLoading = R.drawable.loadinglaser;
		this.imageLoaded = R.drawable.laser;
		impulse = new Vec2();
	}
	
	@Override
	public int getImage () {
		if (body == null) return imageLoading;
		return imageLoaded;
	}
	
	@Override
	public Vec2 getTopLeft () {
		if (body != null) return super.getTopLeft();
		float val = current*DescriptionLaser.RADIUS/LOADING_TIME;
		temptl.x = -val;
		temptl.y = +val;
		return tl;
	}
	
	@Override
	public Vec2 getBottomRight () {
		if (body != null) return super.getBottomRight();
		Vec2 pos = getPosition();
		float val = current*DescriptionLaser.RADIUS/LOADING_TIME;
		tempbr.x = pos.x+val;
		tempbr.y = pos.y-val;
		return br;
	}

	@Override
	public void touch(Actor actor) {}
	
	@Override
	public void step() {
		if (current < LOADING_TIME) current++;
	}
	
	@Override
	public void init(Ship ship, DescriptionWeapon wd) {
		super.init(ship, wd);
		bdef.userData = this;
		current = 0;
	}

	@Override
	public boolean fire(float x, float y) {
		if (current < LOADING_TIME) return false;
		insertInWorld();
		impulse.set(SPEED*(x<0?-1:1),SPEED*(y<0?-1:1));
		body.applyLinearImpulse(impulse, body.getWorldCenter());
		body.applyTorque(ROTATION_SPEED);
		return true;
	}

}
