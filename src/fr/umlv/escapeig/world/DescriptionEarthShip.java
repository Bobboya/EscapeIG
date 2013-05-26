package fr.umlv.escapeig.world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.R;

class DescriptionEarthShip extends DescriptionShip {
	
	DescriptionEarthShip () {
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(5.75f, 5.25f);
		
		image = R.drawable.ship3;
		shape = polygon;
		topLeft = new Vec2(-5.75f,5.25f);
		bottomRight = new Vec2(5.75f,-5.25f);
	}
}
