package fr.umlv.escapeig.world;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Filter;
import org.jbox2d.dynamics.Fixture;

import android.view.MotionEvent;
import fr.umlv.escapeig.gesture.ComplexOnGestureListener;

/**
 * The hero ship
 *
 */
public class HeroShip extends Ship {

	private static final int MAX_LIFE = 5;
	public static final int GROUP_INDEX = -1;
	private static final float SPEED = 0.2f;
	private static final int MOTION_MARGIN = 30;
	private static final float ANGLE = 0;
	private static final int LOOPING_DURATION = 2*Board.STEP_PER_SECOND;
	
	private final int maxLife;
	private int life;
	private Vec2 pos = new Vec2();
	private boolean godOn = false;
	private final int[] loopingImages;
	private int loopingCurrentTime;
	private int loopingPos;
	
	GestureListener gestureListener = new GestureListener(); 

	HeroShip() {
		super(50f, 50f, ANGLE,
			ShipType.HERO.desc(),
			GROUP_INDEX);
		this.loopingImages = ((DescriptionHeroShip)ShipType.HERO.desc()).loopingImages;
		this.maxLife = MAX_LIFE;
		this.life = maxLife;
	}
	
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

	@Override
	public Type getType() {
		return Actor.Type.HERO;
	}

	@Override
	public void step() {
		gestureListener.counter++;
		if (gestureListener.counter >= Board.STEP_PER_SECOND/3)
			gestureListener.onUp(null);
		
		if (godOn) {
			loopingCurrentTime++;
			loopingPos = (int)((float)(loopingCurrentTime*(loopingImages.length-1))/LOOPING_DURATION);
			if (loopingCurrentTime > LOOPING_DURATION) {
				godOff();
			}
		}
	}

	@Override
	public void touch(Actor actor) {
		
	}
	
	@Override
	public int getImage() {
		if (!godOn)	return shipDescription.image;
		return loopingImages[loopingPos];
	}
	
	private class GestureListener extends ComplexOnGestureListener {
		
		private MotionEvent last;
		int counter = 0;
		
		@Override
		public boolean onScroll (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			if (godOn) return false;
			counter = 0;
			if (last == null) last = e1;
			float dx = last.getX()*e2.getX();
			float dy = last.getY()*e2.getY();
			last = e2;
			//Avoid trembling
			//TODO dpi dependant
			if (dx < MOTION_MARGIN && dx > -MOTION_MARGIN) distanceX = 0;
			if (dy < MOTION_MARGIN && dy > -MOTION_MARGIN) distanceY = 0;
			
			pos.x = (SPEED)*(distanceX > 0 ? -1 : 1)*(distanceX == 0 ? 0 : 1);
			pos.y = (SPEED)*(distanceY > 0 ? 1 : -1)*(distanceY == 0 ? 0 : 1);
			body.applyForceToCenter(pos);
			return true;
		}
		
		@Override
		public boolean onUp (MotionEvent e1) {
			pos.x = 0;
			pos.y = 0;
			body.setLinearVelocity(pos);
			return true;
		}

		@Override
		public boolean onRing () {
			godOn();
			return true;
		}
		
		@Override
		public boolean onFling (MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			loadWeapon();
			fire(velocityX, -velocityY);
			return true;
		}
		
		@Override
		public boolean onSingleTapUp (MotionEvent e) {
			loadWeapon();
			return true;
		}
		
		@Override
		public boolean onDoubleTap (MotionEvent e) {
			int pos = defaultWeapon.ordinal()+1;
			WeaponType[] values = WeaponType.values();
			defaultWeapon = values[pos%values.length];
			return true;
		}
	}
	
	private void godOff () {
		godOn = false;
		Fixture fixt = body.getFixtureList();
		Filter filter = fixt.getFilterData();
		filter.groupIndex = GROUP_INDEX;
		fixt.setFilterData(filter);
	}
	
	private void godOn () {
		godOn = true;
		loopingPos = 0;
		loopingCurrentTime = 0;
		
		pos.x = 0;
		pos.y = 0;
		body.setLinearVelocity(pos);
		
		//God mode
		Fixture fixt = body.getFixtureList();
		Filter filter = fixt.getFilterData();
		filter.groupIndex = Board.WALL_INDEX;
		fixt.setFilterData(filter);
	}
	
}


