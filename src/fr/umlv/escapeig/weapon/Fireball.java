package fr.umlv.escapeig.weapon;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.escapeig.R;
import fr.umlv.escapeig.world.Actor;
import fr.umlv.escapeig.world.Actor.Type;

class Fireball implements Weapon {

	private static final int IMAGE_PADDING = 20;
	private static final int SPEED = 100;
	private static final int LOADING_TIME = 10;

	private final int image;
	//private final GameWorld world;
	private final Actor actor;
	private final int groupIndex;
	
	//private AnimatedWorldPainter painter;
	private float painterAngle = Float.MAX_VALUE;
	//private Behavior behavior;
	private Body body;
	private float size;

	Fireball (final Actor actor, int groupIndex) {
		this.image = R.drawable.fireball;
		this.actor = actor;
		//this.world = actor.getWorld();
		this.groupIndex = groupIndex;
//		this.painter = new AnimatedWorldPainter() {
//			private final int time = LOADING_TIME*GameWorld.STEP_PER_SECOND;
//			private final float padding = 30f/time;
//			private int tick;
//
//			@Override
//			public void apply() {
//				painterAngle += 0.1;
//				if (painterAngle > MathUtils.PI*2) painterAngle = 0;
//				
//				if (tick > time) return;
//				++tick;
//				size += padding;
//				
//			}
//			@Override
//			public void render(WorldGraphics2D painter) {
//				painter.save();
//				painter.translateFromWorld(getPosition());
//				painter.rotate(getAngle());
//				painter.drawRectWithImage(new Vec2(-size, size), new Vec2(size, -size), image);
//				painter.restore();
//			}
//		};
//		
//		behavior = BodyBehaviors.INERTIA;
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
	public Type getType() {
		return Type.WEAPON;
	}
	
	@Override
	public boolean fire(float theta) {
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.DYNAMIC;
		bdef.position.set(getPosition());
		bdef.userData = this;
		bdef.angle = theta;
		
		CircleShape shape = new CircleShape();
		shape.m_p.setZero();
		shape.m_radius = size;
		
		FixtureDef fdef = new FixtureDef();
		fdef.restitution = 0;
		fdef.density = 0;
		fdef.shape = shape;
		fdef.isSensor = true;
		fdef.filter.groupIndex = groupIndex;
		
//		body = world.addSingleBodiedActor(this, bdef);
//		body.createFixture(fdef);
//		
//		behavior = new BodyLinearMove(body, theta, SPEED);
		return true;
	}

	@Override
	public Vec2 getPosition() {
		if (body != null) return new Vec2(body.getPosition());
		Vec2 pos = new Vec2(actor.getPosition());
		pos.y += size+IMAGE_PADDING;
		return pos;
	}

	@Override
	public float getAngle() {
		if (body != null) return body.getAngle();
		return painterAngle;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

}
