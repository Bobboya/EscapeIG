package fr.umlv.escapeig.world;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

public abstract class DescriptionShip {
	public int image;
	PolygonShape shape;
	Vec2 topLeft;
	Vec2 bottomRight;
	Actor.Type actor;
	WeaponType defaultWeapon = WeaponType.MISSILE;
}