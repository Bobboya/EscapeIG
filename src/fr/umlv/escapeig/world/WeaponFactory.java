package fr.umlv.escapeig.world;

import java.util.ArrayList;


/**
 * Create weapons
 *
 */
public class WeaponFactory {
	
	static final int POOL_SIZE = 10;
	private final Board board;

	ArrayList<ArrayList<Weapon>> pools = new ArrayList<ArrayList<Weapon>>(WeaponType.values().length);
	
	/**
	 * @param board the world to create the weapons on
	 */
	WeaponFactory (Board board) {
		this.board = board;
		for (int i=0; i<WeaponType.values().length;++i) {
			ArrayList<Weapon> alw = new ArrayList<Weapon>(POOL_SIZE);
			pools.add(alw);
			
			for (int j=0; j<POOL_SIZE; ++j) {
				Weapon w = create(WeaponType.values()[i]);
				alw.add(w);
			}
		}
	}
	
	Weapon create (WeaponType type) {
		Weapon w;
		switch (type) {
			case FIREBALL:
				w = new Fireball();
				break;
			case LASER:
				w = new Laser();
				break;
			case MISSILE:
				w = new Missile();
				break;
			case SHIBOLEET:
				w =  new Shiboleet(board);
				break;
			default:
				w = null;
				break;
		}
		w.type = type;
		w.wf = this;
		if (w != null) w.board = board;
		return w;
	}
	
	/**
	 * Create a weapon for a ship
	 * @param type type of weapon to create
	 * @param ship the ship getting the weapon
	 * @return the created weapon
	 * @throws WeaponCreationException
	 */
	public Weapon create(WeaponType type, Ship ship) {
		ArrayList<Weapon> alw = pools.get(type.ordinal());
		if (alw.size() <= 0) return null;
		
		Weapon w = alw.get(0);
		if (w == null) return null;
		alw.remove(0);
		
		w.init(ship, type.wd);
		board.actors.add(w);
		if (ship.body == null) return null;
		if (w.fdef == null) return null;
		if (w.fdef.shape == null) return null;
		ship.body.createFixture(w.fdef);
		
		return w;
	}

}
