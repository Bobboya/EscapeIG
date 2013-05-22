package fr.umlv.escapeig.weapon;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import fr.umlv.escapeig.R;
import fr.umlv.escapeig.world.Actor;
import fr.umlv.escapeig.world.Actor.Type;

class Laser implements Weapon {
	
	private static final int SPEED = 50;
	private static final int MARGIN = 20;
	private static final int COUNTDOWN = 3;
	private static final float MAX_SIZE = 15;
	private static final int LOADING_TIME = 10;
	private static final float ROTATION_SPEED = MathUtils.PI;
	
	//private final GameWorld world;
	private final Actor actor;
	private final int imageLoading;
	private final int imageLoaded;
	private final int groupIndex;
	
//	private AnimatedWorldPainter painter;
//	private Behavior behavior;
	private Body body;
	private float size;
	
	Laser (Actor actor, int groupIndex) {
		this.imageLoading = R.drawable.loadinglaser;
		this.imageLoaded = R.drawable.laser;
		this.actor = actor;
		//this.world = actor.getWorld();
		this.groupIndex = groupIndex;
//		this.painter = new AnimatedWorldPainter() {
//			private final float padding = MAX_SIZE/(LOADING_TIME*GameWorld.STEP_PER_SECOND);
//
//			@Override
//			public void apply() {
//				if (size > MAX_SIZE) return;
//				size += padding;
//			}
//			@Override
//			public void render(WorldGraphics2D painter) {
//				painter.save();
//				painter.translateFromWorld(getPosition());
//				painter.rotate(getAngle());
//				painter.drawRectWithImage(new Vec2(-size, size), new Vec2(size, -size), imageLoading);
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
		Vec2 pos = new Vec2(actor.getPosition());
		pos.y += MARGIN+size;
		return pos;
	}

	@Override
	public float getAngle() {
		if (body != null) return body.getAngle();
		return actor.getAngle();
	}

	@Override
	public void touch(Actor actor) {}

	@Override
	public boolean fire(float theta) {
//		if (size < MAX_SIZE) return false;
//		
//		BodyDef bdef = new BodyDef();
//		bdef.type = BodyType.DYNAMIC;
//		bdef.position.set(getPosition());
//		bdef.userData = this;
//		bdef.linearVelocity = new Vec2(0, SPEED);
//		
//		int width = imageLoading.getWidth(null);
//		final int halfWidth = width/2;
//		int height = imageLoading.getHeight(null);
//		final int halfHeight = height/2;
//		
//		PolygonShape shape = new PolygonShape();
//		shape.setAsBox(width, height);
//		
//		FixtureDef fdef = new FixtureDef();
//		fdef.restitution = 0;
//		fdef.density = 0;
//		fdef.shape = shape;
//		fdef.isSensor = true;
//		fdef.filter.groupIndex = groupIndex;
//		
//		body = world.addSingleBodiedActor(this, bdef);
//		body.createFixture(fdef);
//		
//		this.painter = new AnimatedWorldPainter() {
//			@Override
//			public void apply() {}
//			@Override
//			public void render(WorldGraphics2D painter) {
//				painter.save();
//				painter.translateFromWorld(getPosition());
//				painter.rotate(getAngle());
//				painter.drawRectWithImage(new Vec2(-halfWidth, halfHeight),
//						new Vec2(halfWidth, -halfHeight), imageLoading);
//				painter.restore();
//			}
//		};
//		
//		Behavior stop = new Behavior () {
//			@Override
//			public void apply() {
//				body.m_linearVelocity.set(0,0);
//				body.m_angularVelocity = ROTATION_SPEED;
//				final int width = imageLoaded.getWidth(null)/2;
//				final int height = imageLoaded.getHeight(null)/2;
//				painter = new AnimatedWorldPainter() {
//					@Override
//					public void render(WorldGraphics2D painter) {
//						painter.save();
//						painter.translateFromWorld(getPosition());
//						painter.rotate(getAngle());
//						painter.drawRectWithImage(new Vec2(-width, height),
//								new Vec2(width, -height), imageLoaded);
//						painter.restore();
//					}
//					@Override
//					public void apply() {}
//				};
//			
//				PolygonShape shape = (PolygonShape)body.getFixtureList().getShape();
//				shape.setAsBox(width, height);
//				body.getFixtureList().m_shape = shape;
//				body.resetMassData();
//			}
//		};
//		world.scheduledBehaviorIn(COUNTDOWN, stop);
//		
//		final WorldActor self = this;
//		Behavior destroy = new Behavior () {
//			@Override
//			public void apply() {
//				world.destroyActor(self);
//				world.destroyBody(body);
//				return;
//			}
//		};
//		world.scheduledBehaviorIn(COUNTDOWN*2, destroy);
//		
		return true;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

}
