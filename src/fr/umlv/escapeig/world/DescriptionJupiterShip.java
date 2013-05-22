package fr.umlv.escapeig.world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.R;

class DescriptionJupiterShip extends DescriptionShip {
	
	DescriptionJupiterShip () {
		PolygonShape polygon = new PolygonShape();
		Vec2 vertices[] = new Vec2[6];
		vertices[0] = new Vec2(0, 23);
		vertices[1] = new Vec2(-16,0);
		vertices[2] = new Vec2(-16, -19);
		vertices[3] = new Vec2(0, -13);
		vertices[4] = new Vec2(16, -19);
		vertices[5] = new Vec2(16, 0);
		polygon.set(vertices, vertices.length);
		polygon.m_centroid.set(0, 0);
		
		image = R.drawable.ship1;
		shape = polygon;
		topLeft = new Vec2(-16,23);
		bottomRight = new Vec2(16,-23);
	}
}
