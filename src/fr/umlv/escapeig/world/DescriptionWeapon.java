package fr.umlv.escapeig.world;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;

public abstract class DescriptionWeapon {
	public int image;
	public int icon;
	Shape shape;
	Vec2 topLeft;
	Vec2 bottomRight;
}