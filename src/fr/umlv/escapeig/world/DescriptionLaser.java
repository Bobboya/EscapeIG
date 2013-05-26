package fr.umlv.escapeig.world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.R;


class DescriptionLaser extends DescriptionWeapon {
	
	static final int RADIUS = 5;
	
	DescriptionLaser () {
		image = R.drawable.laser;
		icon = R.drawable.iconfireball;
		
		PolygonShape pshape = new PolygonShape();
		pshape.setAsBox(RADIUS, Board.HEIGHT);
		shape = pshape;
		
		float hRadius = RADIUS/2;
		float hHeight = Board.HEIGHT/2;
		topLeft = new Vec2(-hRadius, hHeight);
		bottomRight = new Vec2(hRadius,-hHeight);
	}
}
