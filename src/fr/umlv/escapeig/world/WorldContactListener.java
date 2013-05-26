package fr.umlv.escapeig.world;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

class WorldContactListener implements org.jbox2d.callbacks.ContactListener {

	@Override
	public void beginContact(Contact contact) {
		if (!contact.isTouching() || !contact.isEnabled()) return;
		Actor actorA = null;
		Actor actorB = null;
		
		if (contact.getFixtureA().getBody().m_userData instanceof Actor) {
			actorA = (Actor)contact.getFixtureA().getBody().m_userData;
		}
		if (contact.getFixtureB().getBody().m_userData instanceof Actor) {
			actorB = (Actor)contact.getFixtureB().getBody().m_userData;
		}
		if (actorA != null) actorA.touch(actorB);
		if (actorB != null) actorB.touch(actorA);
	}
	
	@Override
	public void endContact(Contact contact) {}
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {}
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {}
}
