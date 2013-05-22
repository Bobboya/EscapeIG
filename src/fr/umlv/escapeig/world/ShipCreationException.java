package fr.umlv.escapeig.world;

/**
 * Exception thrown when a ship can't be created
 *
 */
public class ShipCreationException extends Exception {
	
	public ShipCreationException() {
		super();
	}
	
	public ShipCreationException(String string) {
		super(string);
	}

	public ShipCreationException(String string, Throwable cause) {
		super(string,cause);
	}
	
	public ShipCreationException(Throwable cause) {
		super(cause);
	}

	private static final long serialVersionUID = 1L;

}
