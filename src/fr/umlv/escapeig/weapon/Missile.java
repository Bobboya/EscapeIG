package fr.umlv.escapeig.weapon;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.escapeig.R;
import fr.umlv.escapeig.world.Actor;
import fr.umlv.escapeig.world.Actor.Type;

class Missile implements Weapon {
	
	private static final int WEAPON_PADDING = 25;

	//private final Board world;
	private final Actor actor;
	private final int image;
	private final int groupIndex;
	//private AnimatedWorldPainter painter;
	//private Behavior behavior;
	private Body body;
	
	Missile (Actor actor, int groupIndex) {
		this.image = R.drawable.missile;
		//this.world = actor.getWorld();
		this.actor = actor;
		this.groupIndex = groupIndex;
//		this.painter = new AnimatedWorldPainter() {
//			@Override
//			public void apply() {
//				
//			}
//			@Override
//			public void render(WorldGraphics2D painter) {
//				painter.save();
//				painter.translateFromWorld(getPosition());
//				painter.rotate(getAngle());
//				painter.drawRectWithImage(new Vec2(-8, 25), new Vec2(8, -25), image);
//				painter.restore();
//			}
//		};
		
		//behavior = BodyBehaviors.INERTIA;
	}
	
	@Override
	public Type getType() {
		return Actor.Type.WEAPON;
	}

	@Override
	public Vec2 getPosition() {
		if (body != null) return new Vec2(body.getPosition());
		Vec2 pos = actor.getPosition();
		pos.y += WEAPON_PADDING;
		return pos;
	}

	@Override
	public float getAngle() {
		if (body != null) return body.getAngle();
		return 0;
	}

	@Override
	public void touch(Actor actor) {
//		world.destroyActor(this);
//		world.destroyBody(body);
//		painter = new AnimatedWorldPainter() {
//			@Override
//			public void apply() {}
//			@Override
//			public void render(WorldGraphics2D painter) {}
//		};
		
	}

	@Override
	public boolean fire(float theta) {
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.DYNAMIC;
		bdef.position.set(getPosition());
		bdef.userData = this;
		bdef.angle = 0;
		
		PolygonShape shape = new PolygonShape();
		shape.m_centroid.setZero();
		shape.setAsBox(25, 8);
		
		FixtureDef fdef = new FixtureDef();
		fdef.restitution = 0;
		fdef.density = 0;
		fdef.shape = shape;
		fdef.isSensor = true;
		fdef.filter.groupIndex = groupIndex;
		
//		body = world.addSingleBodiedActor(this, bdef);
//		if (body != null)
//			body.createFixture(fdef);
//		
//		behavior = new BodyLinearMove(body, theta, 100);
		return true;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

}
