package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class that defines a bullet for the Asteroids game.
 * 
 * @author Michiel Janssen & Jelle Pelgrims
 *
 */

public class MinorPlanet extends Entity implements ICollidable {

	static double MAX_SPEED =  300000;
	static final double MIN_RADIUS = 5;
	
	String type;
	double totalTraveledDistance = 0;
	
	public MinorPlanet(double x, double y, double xVelocity, double yVelocity, double radius) {
		super(x, y, xVelocity, yVelocity, radius);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getRadius() {
		if (this.type == "asteroid") {
			return this.radius - (0.0001*totalTraveledDistance);
		}
		return super.getRadius();
		
		
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
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * This method moves the minor planet with a given valid duration.
	 * 
	 * Implemented defensively.
	 * 
	 * @param  duration
	 * @invar The given duration must be valid
	 * 		  | isValidDuration(duration) == true
	 * @post The new xPosition of this minor planet is the current xPosition plus the current xVelocity*duration.
	 *       | new.getPositionX() == this.getPositionX() + this.getVelocityX()*duration
	 * @post The new yPosition of this minor planet is the current yPosition plus the current yVelocity*duration.
	 *       | new.getPositionY() == this.getPositionY() + this.getVelocityY()*duration
	 * @throws IllegalArgumentException
	 * 		   The given duration was invalid
	 *         | ! isValidDuration(duration)
	 */
	@Override
	public void move(double duration) throws IllegalArgumentException {
		if (isValidDuration(duration)) {
			double newPosX = getPositionX() + (getVelocityX()*duration);
			double newPosY = getPositionY() + (getVelocityY()*duration);
			double distance = Math.sqrt(Math.pow((newPosX-getPositionX()), 2)+Math.pow((newPosY-getPositionY()), 2));
			
			setPosition(newPosX, newPosY);
			this.distanceTravelled += distance;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
}