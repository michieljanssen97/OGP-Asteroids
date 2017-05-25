package asteroids.model;

/**
 * A class that defines an asteroid for the Asteroids game.
 * 
 * @author Michiel Janssen & Jelle Pelgrims
 *
 */

public class Asteroid extends MinorPlanet implements ICollidable {

	/**
	 * Initialize this new asteroid with a given position, velocity, radius.
	 * 
	 * @see superclass constructor
	 */
	public Asteroid(double x, double y, double xVelocity, double yVelocity, double radius) {
		super(x, y, xVelocity, yVelocity, radius);
	}
	
	/**
	 * Returns the minimum density for this asteroid
	 */
	public double getMinDensity() {return 2.65E12;}
	
	/**
	 * A function that resolves a collision event between and asteroid and another entity.
	 * 
	 * @param entity
	 * @param this
	 * @post This function executes in such a manner that ensures that, at the end of the function:
	 * 			* In the case that the entity is a Ship: the ship's is destroyed.
	 * 			* For other MinorPlanets we use the defaultCollide helper function.
	 * 			* For other entities we call their collision function
	 */
	public void collide(Entity entity) {
		if (entity instanceof Ship) {
			entity.destroy();
		}
		else if (entity instanceof Asteroid) {defaultCollide(entity);} 
		else if (entity instanceof Planetoid) {defaultCollide(entity);}
		else {
			entity.collide(this);
		}
	}

}
