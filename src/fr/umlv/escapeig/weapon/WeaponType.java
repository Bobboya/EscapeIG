package fr.umlv.escapeig.weapon;

import fr.umlv.escapeig.R;


/**
 * 
 * Enum listing the type of weapon
 *
 */
public enum WeaponType {
	MISSILE(R.drawable.iconmissile),
	FIREBALL(R.drawable.iconfireball),
	SHIBOLEET(R.drawable.iconshiboleet),
	LASER(R.drawable.iconmissile);

	private final int icon;

	WeaponType(int img) {
		this.icon = img;
	}

	/**
	 * 
	 * @return icon representing the image
	 */
	int getIcon() {return icon;}
}