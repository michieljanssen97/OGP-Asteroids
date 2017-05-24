package asteroids.model;

/**
 * 
 * This interface defines an IOwnable for the Asteroids game.
 *
 * @author Michiel Janssen & Jelle Pelgrims
 *
 */

public interface IOwnable {

	public Object getOwner();
	public void changeOwner(Object object);
	public void disown();
	
	public default boolean isOwned() {return !(getOwner() == null);}
	public default boolean hasAsOwner(Object object) {return object == getOwner() && object != null;}
	public default boolean canHaveAsOwner(Object object) {return !isOwned();}
}

