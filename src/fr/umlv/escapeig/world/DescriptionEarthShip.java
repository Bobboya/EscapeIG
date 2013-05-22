package fr.umlv.escapeig.world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.R;

class DescriptionEarthShip extends DescriptionShip {
	
	DescriptionEarthShip () {
		PolygonShape polygon = new PolygonShape();
		Vec2 vertices[] = new Vec2[6];
		vertices[0] = new Vec2(-1, 21);
		vertices[1] = new Vec2(-8,-2);
		vertices[2] = new Vec2(-23, -14);
		vertices[3] = new Vec2(23, -14);
		vertices[4] = new Vec2(8, -2);
		vertices[5] = new Vec2(1,21);
		polygon.set(vertices, vertices.length);
		polygon.m_centroid.set(0, 0);
		
		image = R.drawable.ship3;
		shape = polygon;
		topLeft = new Vec2(-23,21);
		bottomRight = new Vec2(23,-21);
	}
}
