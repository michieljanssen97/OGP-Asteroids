package asteroids.model;

/**
 * A class that defines a bullet for the Asteroids game.
 * 
 * @author Michiel Janssen & Jelle Pelgrims
 *
 */

public abstract class MinorPlanet extends Entity implements ICollidable {
	
	public MinorPlanet(double x, double y, double xVelocity, double yVelocity, double radius) {
		super(x, y, xVelocity, yVelocity, radius);
	}

	public double getMinDensity() {
		return 0.0;
	}

	@Override
	public double getMass() {
		return (4.0/3.0)*Math.PI*Math.pow(getRadius(), 3)*getMinDensity();
	}

	@Override
	public double getMinRadius() {
		return 5;
	}

}