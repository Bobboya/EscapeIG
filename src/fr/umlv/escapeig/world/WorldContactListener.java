package fr.umlv.escapeig.world;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

class WorldContactListener implements org.jbox2d.callbacks.ContactListener {
	
	private WorldContactListener () {}
	
	public static void create(World world) {
		world.setContactListener(new WorldContactListener());
	}

	@Override
	public void beginContact(Contact contact) {
		if (!contact.isTouching() || !contact.isEnabled()) return;
		Actor actorA = null;
		Actor actorB = null;
		
		try {
			actorA = Actor.class.cast(contact.getFixtureA().getBody().m_userData);
			actorB = Actor.class.cast(contact.getFixtureB().getBody().m_userData);
		} catch (ClassCastException e) {
			actorA = null;
			actorB = null;
		}
		
		if (actorA == null || actorB == null) return;
		
		actorA.touch(actorB);
		actorB.touch(actorA);
	}
	
	@Override
	public void endContact(Contact contact) {}
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {}
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {}
}
