package fr.umlv.escapeig.world;

/**
 * Implement this interface to handle contact
 * 
 */
public interface ContactListener {
	/**
	 * When a contact is produce the Object ContactListener execute the method touch on WorldActor specified in argument.
	 * 
	 * @param actor 	WorldActor you are touching
	 */
	void touch(Actor actor);
}