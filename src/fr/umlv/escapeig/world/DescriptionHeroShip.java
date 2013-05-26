package fr.umlv.escapeig.world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

import fr.umlv.escapeig.R;

class DescriptionHeroShip extends DescriptionShip {
	
	int loopingImages[] = new int[11];

	DescriptionHeroShip () {
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(4.75f, 5.75f);
		
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
