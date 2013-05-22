package fr.umlv.escapeig.world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.R;

class DescriptionHeroShip extends DescriptionShip {

	DescriptionHeroShip () {
		PolygonShape polygon = new PolygonShape();
		Vec2 vertices[] = new Vec2[6];
		vertices[0] = new Vec2(-5, 11.5f);
		vertices[1] = new Vec2(-9.5f,0);
		vertices[2] = new Vec2(-4.5f, -6.5f);
		vertices[3] = new Vec2(4.5f, -6.5f);
		vertices[4] = new Vec2(9.5f, 0);
		vertices[5] = new Vec2(5f, 11.5f);
		polygon.set(vertices, 6);
		polygon.m_centroid.set(0, 0);
		
		image = R.drawable.ship2;
		shape = polygon;
		topLeft = new Vec2(-9.5f,11.5f);
		bottomRight = new Vec2(9.5f,-11.5f);
	}
}
