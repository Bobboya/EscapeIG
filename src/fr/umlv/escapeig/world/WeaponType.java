package fr.umlv.escapeig.world;



/**
 * 
 * Enum listing the type of weapon
 *
 */
public enum WeaponType {
	
	MISSILE(new DescriptionMissile()),
	FIREBALL(new DescriptionFireball()),
	SHIBOLEET(new DescriptionShiboleet()),
	LASER(new DescriptionLaser());

	public final DescriptionWeapon wd;

	WeaponType(DescriptionWeapon wd) {
		this.wd = wd;
	}
}