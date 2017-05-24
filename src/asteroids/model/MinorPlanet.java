package asteroids.model;

/**
 * A class that defines a MinorPlanet for the Asteroids game.
 * 
 * @author Michiel Janssen & Jelle Pelgrims
 *
 */
public abstract class MinorPlanet extends Entity implements ICollidable {
	
	/**
	 * Initialize this new minorplanet with a given position, velocity, radius.
	 * 
	 * @see superclass constructor
	 */
	public MinorPlanet(double x, double y, double xVelocity, double yVelocity, double radius) {
		super(x, y, xVelocity, yVelocity, radius);
	}

	/**
	 * Return the minimum density of this minorplanet
	 */
	@Override
	public double getMinDensity() {
		return 0.0;
	}

	/**
	 * Return the mass of this minorplanet
	 */
	@Override
	public double getMass() {
		return (4.0/3.0)*Math.PI*Math.pow(getRadius(), 3)*getMinDensity();
	}

	/**
	 * Return the minimum radius of this minorplanet
	 */
	@Override
	public double getMinRadius() {
		return 5;
	}

}