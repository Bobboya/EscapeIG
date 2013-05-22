package fr.umlv.escapeig.weapon;

/**
 * This exception is thrown when a weapon can't be created
 */
public class WeaponCreationException extends Exception {
	
	public WeaponCreationException() {
		super();
	}
	
	public WeaponCreationException(String string) {
		super(string);
	}

	public WeaponCreationException(String string, Throwable cause) {
		super(string,cause);
	}
	
	public WeaponCreationException(Throwable cause) {
		super(cause);
	}

	private static final long serialVersionUID = 1L;

}
