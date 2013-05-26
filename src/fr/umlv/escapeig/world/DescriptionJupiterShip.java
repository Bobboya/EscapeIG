package fr.umlv.escapeig.world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.R;

class DescriptionJupiterShip extends DescriptionShip {
	
	DescriptionJupiterShip () {
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(4, 5.75f);
		
		image = R.drawable.ship1;
		shape = polygon;
		topLeft = new Vec2(-4,5.75f);
		bottomRight = new Vec2(4,-5.75f);
	}
}
