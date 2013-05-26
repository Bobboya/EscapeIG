package fr.umlv.escapeig.world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.R;

class DescriptionMoonShip extends DescriptionShip {
	
	DescriptionMoonShip () {
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(5.25f, 5.75f);
		
		image = R.drawable.ship4;
		shape = polygon;
		topLeft = new Vec2(-5.25f,5.75f);
		bottomRight = new Vec2(5.25f,-5.75f);
	}
	
}
