package fr.umlv.escapeig.world;

import fr.umlv.escapeig.weapon.WeaponType;

/**
 * The different types of ship
 *
 */
public enum ShipType {
	JUPITER(new DescriptionJupiterShip(), Actor.Type.ENNEMY, WeaponType.MISSILE),
	MOON(new DescriptionMoonShip(),  Actor.Type.ENNEMY, WeaponType.MISSILE),
	EARTH(new DescriptionEarthShip(),  Actor.Type.ENNEMY, WeaponType.FIREBALL),
	HERO(new DescriptionHeroShip(),  Actor.Type.ENNEMY, WeaponType.MISSILE);
	
	private final DescriptionShip desc;
	private final Actor.Type type;
	private final WeaponType weapon;
	
	ShipType(DescriptionShip desc, Actor.Type type, WeaponType weapon) {
		this.desc = desc;
		this.type = type;
		this.weapon = weapon;
	}
	
	DescriptionShip desc() {return desc;}
	Actor.Type type() {return type;}
	WeaponType weapon() {return weapon;}
	
}