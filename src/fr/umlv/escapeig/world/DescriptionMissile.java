package fr.umlv.escapeig.world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.R;


class DescriptionMissile extends DescriptionWeapon {

	DescriptionMissile () {
		image = R.drawable.missile;
		icon = R.drawable.iconmissile;
		
		PolygonShape pShape = new PolygonShape();
		pShape.setAsBox(2, 3.125f);
		shape = pShape;
		
		topLeft = new Vec2(-2, 3.125f);
		bottomRight = new Vec2(2,-3.125f);
	}
}
