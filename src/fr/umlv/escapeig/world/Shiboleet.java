package fr.umlv.escapeig.world;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import fr.umlv.escapeig.R;

class Shiboleet extends Weapon {
	
	private static final int LOADING_TIME = 2*Board.STEP_PER_SECOND;
	static final int BALL_SIZE = 5;
	private static final int BALL_NUMBER = 8;
	private static final int SPEED = 50;
	
	private final Miniboleet bodies[];
	private int current;
	private final Vec2 force;
	private final Board board;
	
	private final Vec2 temptl = new Vec2();
	private final Vec2 tempbr = new Vec2();
	
	private static class Miniboleet implements Actor {
		
		private final static BodyDef bdef = new BodyDef();
		private final static FixtureDef fdef = new FixtureDef();
		
		Body body;
		final Vec2 topLeft = new Vec2();
		final Vec2 bottomRight = new Vec2();
		final Shiboleet shiboleet;
		
		static {
			final CircleShape shape = new CircleShape();
			shape.m_radius = BALL_SIZE;
			
			fdef.restitution = 0;
			fdef.density = 0.00001f;
			fdef.shape = shape;
			fdef.isSensor = true;
			
			bdef.type = BodyType.DYNAMIC;
		}
		
		Miniboleet (Shiboleet s) {
			shiboleet = s;
		}

		@Override
		public void touch(Actor actor) {
			shiboleet.board.actors.remove(this);
			shiboleet.board.world.destroyBody(body);
		}

		@Override
		public Type getType() {
			return Actor.Type.WEAPON;
		}

		@Override
		public Vec2 getPosition() {
			return body.getPosition();
		}

		@Override
		public float getAngle() {
			return 0;
		}

		@Override
		public void step() {}

		@Override
		public int getImage() {
			return R.drawable.shiboleet;
		}

		@Override
		public int getGroup() {
			return fdef.filter.groupIndex;
		}

		@Override
		public Vec2 getTopLeft() {
			topLeft.set(-BALL_SIZE, +BALL_SIZE);
			return topLeft;
		}

		@Override
		public Vec2 getBottomRight() {
			bottomRight.set(BALL_SIZE, -BALL_SIZE);
			return bottomRight;
		}
		
		void init () {
			fdef.filter.groupIndex = shiboleet.getGroup();
			bdef.position = shiboleet.getPosition();
			body = shiboleet.board.world.createBody(bdef);
			body.createFixture(fdef);
		}
	}

	Shiboleet (Board board) {
		this.board = board;
		bodies = new Miniboleet[BALL_NUMBER];
		for (int i=0; i<bodies.length; ++i) {
			bodies[i] = new Miniboleet(this);
		}
		force = new Vec2();
	}
	
	@Override
	public Vec2 getTopLeft () {
		Vec2 res = super.getTopLeft();
		temptl.x = current*res.x/LOADING_TIME;
		temptl.y = current*res.y/LOADING_TIME;
		return temptl;
	}
	
	@Override
	public Vec2 getBottomRight () {
		Vec2 res = super.getBottomRight();
		tempbr.x = current*res.x/LOADING_TIME;
		tempbr.y = current*res.y/LOADING_TIME;
		return tempbr;
	}

	@Override
	public void touch(Actor actor) {}

	@Override
	public boolean fire(float x, float y) {
		if (current < LOADING_TIME) return false;
		
		float step = (2*MathUtils.PI)/BALL_NUMBER;
		for (int i=0; i<BALL_NUMBER; ++i) {
			float angle = i*step;
			Miniboleet boleet = bodies[i];
			boleet.init();
			boleet.body.applyForceToCenter(force.set(MathUtils.cos(angle)*SPEED, MathUtils.sin(angle)*SPEED));
			board.actors.add(boleet);
		}
		board.actors.remove(this);
		
		return true;
	}

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

}
