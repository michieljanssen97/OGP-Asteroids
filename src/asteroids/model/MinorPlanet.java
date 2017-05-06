package asteroids.model;

/**
 * A class that defines a bullet for the Asteroids game.
 * 
 * @author Michiel Janssen & Jelle Pelgrims
 *
 */

public abstract class MinorPlanet extends Entity implements ICollidable {

	static double MAX_SPEED =  300000;
	static final double MIN_RADIUS = 5;
	
	public MinorPlanet(double x, double y, double xVelocity, double yVelocity, double radius) {
		super(x, y, xVelocity, yVelocity, radius);
	}

	
	@Override
	public double getMaxSpeed() {
		return MAX_SPEED;
	}

	@Override
	public double getMinRadius() {
		return MIN_RADIUS;
	}

	@Override
	public double getMinDensity() {
		return 0;
	}

}