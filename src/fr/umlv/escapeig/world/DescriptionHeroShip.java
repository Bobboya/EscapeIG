package fr.umlv.escapeig.world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.R;

class DescriptionHeroShip extends DescriptionShip {

	DescriptionHeroShip () {
		PolygonShape polygon = new PolygonShape();
		Vec2 vertices[] = new Vec2[6];
		vertices[0] = new Vec2(-10, 23);
		vertices[1] = new Vec2(-19,0);
		vertices[2] = new Vec2(-9, -13);
		vertices[3] = new Vec2(9, -13);
		vertices[4] = new Vec2(19, 0);
		vertices[5] = new Vec2(10, 23);
		polygon.set(vertices, 6);
		polygon.m_centroid.set(0, 0);
		
		image = R.drawable.ship2;
		shape = polygon;
		topLeft = new Vec2(-19,23);
		bottomRight = new Vec2(19,-23);
	}
}
