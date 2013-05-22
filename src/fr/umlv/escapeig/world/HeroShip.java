package fr.umlv.escapeig.world;

import org.jbox2d.common.Vec2;

import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;


/**
 * The hero ship
 *
 */
public class HeroShip extends Ship {

	public static final int GROUP_INDEX = -1;
	private static final float ANGLE = 0;
	
	private final int maxLife;
	private int life;
	private boolean isDead;
	private int score;
	private Vec2 pos = new Vec2();
	
	SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {
		@Override
		public boolean onScroll (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			Log.d("Gesture", "e1 = "+e1.getX()+", "+e1.getY());
			Log.d("Gesture", "e2 = "+e1.getX()+", "+e1.getY());
			Log.d("Gesture", "di = "+distanceX+", "+distanceY);
			return true;
		}
	};

	HeroShip(int maxLife) {
		super(50f, 50f, ANGLE,
			ShipType.HERO.desc(),
			GROUP_INDEX);
		
		this.maxLife = maxLife;
		this.life = maxLife;
		this.isDead = false;
	}

//	@Override
//	public void gestureReceived(Gesture g) {
//		switch (g.type()) {
//		case LINE:
//			lineGesture(g);
//			break;
//		case POINT:
//			pointGesture(g);
//			break;
//		case LOOPING:
//			loopingGesture(g);
//			break;
//		default:
//			break;
//		}
//	}

//	private void lineGesture (Gesture g) {
//		//Check if the gesture line append on the weapon
//		Point2D clickDownPoint = g.getOrigin();
//		AABB box = getWorld().worldBoxFromScreen(new Vec2((float)clickDownPoint.getX(),
//				(float)clickDownPoint.getY()), WEAPON_GESTURE_TOLERANCE);
//
//		AABB center = new AABB(getWeapon().getPosition(), getWeapon().getPosition());
//
//		//fire the weapon
//		if (center.contains(box)) {
//			Point2D clickUpPoint = g.getEnd();
//			Vec2 clickOrigin = getWorld().worldCoordinateFromScreen((float)clickDownPoint.getX(),
//					(float)clickDownPoint.getY());
//			Vec2 clickDest = getWorld().worldCoordinateFromScreen((float)clickUpPoint.getX(),
//					(float)clickUpPoint.getY());
//			float theta = MathUtils.fastAtan2(clickDest.y-clickOrigin.y,
//					clickDest.x-clickOrigin.x);
//			
//			fire(theta);
//			return;
//		}
//		
//		//apply linear move to the ship
//		BodyBehaviors.createMovementFor(getWorld(), getBody(), g.getDirection().theta(), g.value(), 1);
//	}

//	private void pointGesture (Gesture g) {
//		//Check if the click  append on the ship
//		Point2D clickDownPoint = g.getOrigin();
//		AABB box = getWorld().worldBoxFromScreen(new Vec2((float)clickDownPoint.getX(),
//				(float)clickDownPoint.getY()), WEAPON_GESTURE_TOLERANCE);
//
//		AABB center = new AABB(getPosition(), getPosition());
//
//		//Create the weapon
//		if (center.contains(box)) {
//			System.out.println("click");
//			loadWeapon();
//			return;
//		}
//	}
//	
//	private void loopingGesture (Gesture g) {
//		final Fixture fixt = getBody().getFixtureList();
//		final Filter filter = fixt.getFilterData();
//		final AnimatedWorldPainter painter = getPainter();
//		
//		Behavior godOn = new Behavior () {
//			@Override
//			public void apply() {
//				setPainter(LoopingPainter.create(getBody(), LOOPING_TIME));
//				getBody().setLinearVelocity(new Vec2());
//				filter.groupIndex = GameWorld.WALL_INDEX;
//				fixt.setFilterData(filter);
//			}
//		};
//		getWorld().scheduledBehaviorIn(0, godOn);
//		
//		Behavior godOff = new Behavior () {
//			@Override
//			public void apply() {
//				setPainter(painter);
//				filter.groupIndex = GROUP_INDEX;
//				fixt.setFilterData(filter);
//			}
//		};
//		getWorld().scheduledBehaviorIn(LOOPING_TIME+1, godOff);
//	}

//	@Override
//	public void touch(Actor actor) {
//		if (actor.getType() == WorldActor.Type.ENNEMY || actor.getType() == WorldActor.Type.WEAPON) {
//			if (--life == 0) kill();
//		}
//	}

	public int group() {
		return GROUP_INDEX;
	}
	
	@Override
	public void kill() {
		//super.kill();
		//isDead = true;
	}
	
	/**
	 * The maximum life of the ship
	 * @return maximum life of the ship
	 */
	public int getMaxLife() {
		return maxLife;
	}
	
	/**
	 * 
	 * @return current life of the ship
	 */
	public int getLife() {
		return life;
	}
	
	/**
	 * 
	 * @return return true if the hero is dead
	 */
	public boolean isDead () {
		return isDead;
	}
	
	/**
	 * return the score of the hero
	 * @return the score of the ship
	 */
	public int getScore () {
		return score;
	}

	@Override
	public Type getType() {
		return Actor.Type.HERO;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void touch(Actor actor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void resetWeapon() {
		// TODO Auto-generated method stub
		
	}
}


