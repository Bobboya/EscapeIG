package fr.umlv.escapeig.world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.R;

class DescriptionMoonShip extends DescriptionShip {
	
	DescriptionMoonShip () {
		PolygonShape polygon = new PolygonShape();
		Vec2 vertices[] = new Vec2[8];
		vertices[0] = new Vec2(-1, 23);
		vertices[1] = new Vec2(-12, 8);
		vertices[2] = new Vec2(-12, -5);
		vertices[3] = new Vec2(-21, -13);
		vertices[4] = new Vec2(21, -13);
		vertices[5] = new Vec2(12, -5);
		vertices[6] = new Vec2(12, 8);
		vertices[7] = new Vec2(1, 23);
		polygon.set(vertices, vertices.length);
		polygon.m_centroid.set(0, 0);
		
		image = R.drawable.ship4;
		shape = polygon;
		topLeft = new Vec2(-21,23);
		bottomRight = new Vec2(21,-23);
	}
	
}
