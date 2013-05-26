package fr.umlv.escapeig.world;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.R;


class DescriptionFireball extends DescriptionWeapon {
	
	static final int RADIUS = 7;
	
	DescriptionFireball () {
		image = R.drawable.fireball;
		icon = R.drawable.iconfireball;
		
		CircleShape cshape = new CircleShape();
		cshape.m_radius = RADIUS;
		shape = cshape;
		
		topLeft = new Vec2(-RADIUS, RADIUS);
		bottomRight = new Vec2(RADIUS,-RADIUS);
	}
}
