package fr.umlv.escapeig.world;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.R;

class DescriptionShiboleet extends DescriptionWeapon {
	
	DescriptionShiboleet () {
		image = R.drawable.shiboleet;
		icon = R.drawable.iconshiboleet;
		
		CircleShape pShape = new CircleShape();
		pShape.m_radius = Shiboleet.BALL_SIZE;
		
		topLeft = new Vec2(-2.5f, 2.5f);
		bottomRight = new Vec2(2.5f,-2.5f);
	}
}
