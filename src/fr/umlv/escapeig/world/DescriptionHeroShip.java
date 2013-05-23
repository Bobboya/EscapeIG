package fr.umlv.escapeig.world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.R;

class DescriptionHeroShip extends DescriptionShip {
	
	int loopingImages[] = new int[11];

	DescriptionHeroShip () {
		PolygonShape polygon = new PolygonShape();
		Vec2 vertices[] = new Vec2[6];
		vertices[0] = new Vec2(-2.5f, 5.75f);
		vertices[1] = new Vec2(-4.75f,0);
		vertices[2] = new Vec2(-2.25f, -3.255f);
		vertices[3] = new Vec2(2.25f, -3.25f);
		vertices[4] = new Vec2(4.75f, 0);
		vertices[5] = new Vec2(2.5f, 5.75f);
		polygon.set(vertices, 6);
		polygon.m_centroid.set(0, 0);
		
		image = R.drawable.ship2;
		shape = polygon;
		topLeft = new Vec2(-4.75f,5.75f);
		bottomRight = new Vec2(4.75f,-5.75f);
		
		loopingImages[0] = R.drawable.ship2_1;
		loopingImages[1] = R.drawable.ship2_2;
		loopingImages[2] = R.drawable.ship2_3;
		loopingImages[3] = R.drawable.ship2_4;
		loopingImages[4] = R.drawable.ship2_5;
		loopingImages[5] = R.drawable.ship2_6;
		loopingImages[6] = R.drawable.ship2_7;
		loopingImages[7] = R.drawable.ship2_8;
		loopingImages[8] = R.drawable.ship2_9;
		loopingImages[9] = R.drawable.ship2_10;
		loopingImages[10] = R.drawable.ship2_11;
	}
}
