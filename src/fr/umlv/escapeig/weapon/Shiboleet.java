package fr.umlv.escapeig.weapon;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.escapeig.R;
import fr.umlv.escapeig.world.Actor;
import fr.umlv.escapeig.world.Actor.Type;
import fr.umlv.escapeig.world.Board;

class Shiboleet extends Weapon {
	
	private static final int LOADING_TIME = 2;
	private static final int BALL_SIZE = 5;
	private static final int BALL_NUMBER = 8;
	private static final int SPEED = 50;
	
	//private final Board world;
	private final Actor actor;
	private final int image;
	private final int groupIndex;
	//private final float maxSize;
	private final ArrayList<Body> bodies;
	
	//private AnimatedWorldPainter painter;
	//private Behavior behavior;
	
	private float size;

	Shiboleet (Actor actor, int groupIndex) {
		this.image = R.drawable.shiboleet;
		this.actor = actor;
		//this.maxSize = image.getHeight(null)/2;
		//this.world = acto.;
		this.groupIndex = groupIndex;
//		this.painter = new AnimatedWorldPainter() {
//			
//			private final float padding = maxSize/(LOADING_TIME*Board.STEP_PER_SECOND);
//
//			@Override
//			public void apply() {
//				if (size > maxSize) return;
//				size += padding;
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

		//behavior = BodyBehaviors.INERTIA;
		bodies = new ArrayList<Body>();
	}

	@Override
	public Type getType() {
		return Actor.Type.WEAPON;
	}

	@Override
	public Vec2 getPosition() { 
		Vec2 pos = new Vec2(actor.getPosition());
		return pos;
	}

	@Override
	public float getAngle() {
		return 0;
	}

	@Override
	public void touch(Actor actor) {}

	@Override
	public boolean fire(float theta) {
		//if (size < maxSize) return false;

		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.DYNAMIC;
		bdef.userData = this;
		bdef.position.set(getPosition());

		final CircleShape shape = new CircleShape();
		shape.m_radius = BALL_SIZE;

		FixtureDef fdef = new FixtureDef();
		fdef.restitution = 0;
		fdef.density = 0;
		fdef.shape = shape;
		fdef.isSensor = true;
		fdef.filter.groupIndex = groupIndex;
		
		float step = (2*MathUtils.PI)/BALL_NUMBER;
		for (int i=0; i<BALL_NUMBER; ++i) {
			float angle = i*step;
			//Body b = world.createBody(bdef);
			//b.createFixture(fdef);
			//Behavior behavior = new BodyLinearMove(b, angle, SPEED);
			//behavior.apply();
			//bodies.add(b);
		}
		
		//world.addActor(this);
		
//		painter = new AnimatedWorldPainter() {
//			
//			@Override
//			public void apply() {}
//			
//			@Override
//			public void render(WorldGraphics2D painter) {
//				for (Body b : bodies) {
//					painter.save();
//					//System.out.println(b.getPosition());
//					painter.translateFromWorld(b.getPosition());
//					painter.drawCircleWithImage(shape, image);
//					painter.restore();
//				}
//			}
//		};

		return true;
	}

	@Override
	public void step() {
		
	}

}
