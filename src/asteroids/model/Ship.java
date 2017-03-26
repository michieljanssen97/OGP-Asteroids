package asteroids.model;

import java.util.Collection;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class that defines a spaceship for the Asteroids game.
 * 
 * General conditions that must be satisfied: 
 * 		- Each position (both x and y) is expressed in kilometres. [km]
 * 		- Each velocity (both x and y) is expressed in kilometres per second. [km/s]
 * 		- All aspects related to the position of the ship shall be worked out defensively.
 *		- All aspects related to velocity must be worked out in a total manner.
 * 		- All aspects related to the orientation of the ship must be worked out nominally.
 * 		- All aspects related to the radius must be worked out defensively.
 * 
 * @author Michiel Janssen & Jelle Pelgrims
 *
 */
public class Ship extends Entity {
	
	private static final double MAX_SPEED = 300000;
	private static final double MIN_RADIUS = 10;
	private static final double THRUSTER_FORCE = 1 * Math.pow(10,21);
	
	private boolean thrusterActive = false;
	
	/**
	 * Initialize this new ship with a given position, velocity, radius and orientation.
	 * 
	 * @param x
	 *        The x-coordinate for this new ship.
	 * @param y
	 * 		  The y-coordinate for this new ship.
	 * @param xVelocity
	 *        The velocity in the x direction for this new ship.
	 * @param yVelocity
	 *        The velocity in the y direction for this new ship.
	 * @param radius
	 * 		  The given radius for this new ship.
	 * @param orientation
	 *        The given orientation for this new ship.
	 * @post  The position of this new ship is equal to the given x- and y-coordinate.
	 * 		  | new.getPositionX() == x
	 * 	      | new.getPositionY() == y
	 * @post  The velocity of this new ship is equal to the given x- and y-velocity.
	 *        | new.getVelocityX() == xVelocity
	 *        | new.getVelocityY() == yVelocity
	 * @post  The radius of this new ship is equal to the given radius.
	 *        | new.getRadius() == radius
	 * @post  The orientation of this new ship is equal to the given orientation.
	 *        | new.getOrientation() == orientation  
	 * @throws IllegalArgumentException
	 */
	public Ship(double x, double y, double xVelocity, double yVelocity, double radius, double orientation, double mass) throws IllegalArgumentException {
		try {
			setPosition(x, y);
			setVelocity(xVelocity, yVelocity);
			setRadius(radius);
			setOrientation(orientation);
			setMass(mass);
		} catch (IllegalArgumentException e) {
			throw e;
		}
		
	};
	
	
	/**
	 * This method moves the ship with a given valid duration.
	 * 
	 * Implemented defensively.
	 * 
	 * @post The new xPosition of this ship is the current xPosition plus the current xVelocity*duration.
	 *       | new.getPositionX() == this.getPositionX() + this.getVelocityX()*duration
	 * @post The new yPosition of this ship is the current yPosition plus the current yVelocity*duration.
	 *       | new.getPositionY() == this.getPositionY() + this.getVelocityY()*duration
	 *       
	 * @param  duration
	 * @throws IllegalArgumentException
	 *         | ! isValidDuration(duration)
	 */
	public void move(double duration) throws IllegalArgumentException {
		if (isValidDuration(duration)) {
			
			if (IsThrusterActive()) {
				double a = this.getAcceleration();
				if (a < 0) {
					a = 0;
				}
				double newVelocityX = getVelocityX() + a*Math.cos(getOrientation())*duration;
				double newVelocityY = getVelocityY() + a*Math.sin(getOrientation())*duration;
				setVelocity(newVelocityX, newVelocityY);
			}
			
			double deltaX = getVelocityX()*duration;
			double deltaY = getVelocityY()*duration;
			setPosition(getPositionX()+deltaX, getPositionY()+deltaY);
		} else {
			throw new IllegalArgumentException();
		}
		
	}
	
	/**
	 * This method turns the ship by a given angle.
	 * 
	 * Implemented nominally.
	 * 
	 * @pre The angle must be between 0 and 2*pi.
	 * 		| 0 <= angle < 2*pi 
	 * @post The new orientation of this is ship is the current orientation plus the given angle module 2*PI.
	 *      | new.getOrientation() == (getOrientation + angle) % (2*PI)
	 * @param angle
	 * 		| The given angle that will be added to the ship's current orientation
	 */
	public void turn(double angle) {
		setOrientation(this.orientation+ angle % (2*Math.PI));
	}
	
	/**
	 * This method accelerates the ship by a given amount.
	 * 
	 * Implemented totally.
	 * 
	 * @param  amount
	 *         The amount of thrust for this new ship.
	 * @post   If the given amount is less then zero (=negative amount) then the amount is set to zero.
	 * 	       | if (amount < 0) 
	 *         |    then amount == 0
	 * @post   If the given amount is positive the ship's velocity is set to a new velocity.
	 *         | if (amount > 0)
	 *         |     new.getVelocityX() = this.getVelocityX() + amount*Math.cos(this.getOrientation())
	 *         |     new.getVelocityY() = this.getVelocityY() + amount*Math.cos(this.getOrientation())    
	 * @effect The velocity is set to to new velocity.
	 *         | setVelocity(new.getVelocityX(),new.getVelocityY())
	 */
	public void thrust(double amount) {
		if (amount < 0) {
			amount = 0;
		}
		double newVelocityX = getVelocityX() + amount*Math.cos(getOrientation());
		double newVelocityY = getVelocityY() + amount*Math.sin(getOrientation());
		setVelocity(newVelocityX, newVelocityY);
				
	}
	

	private World world;
	
	public boolean isPartOfWorld() {
		if (this.world != null) {
			return true;
		}
		return false;
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public void thrustOn() {
		this.thrusterActive = true;
	}
	public void thrustOff() {
		this.thrusterActive = false;
	}


	public boolean IsThrusterActive() {
		return this.thrusterActive;
	}


	public double getAcceleration() {
		return THRUSTER_FORCE/this.getMass();
	}


	public Set<? extends Bullet> getBullets() {
		// TODO Auto-generated method stub
		return null;
	}

	public void loadBullets(Collection<Bullet> bullets) {
		// TODO Auto-generated method stub
		
	}


	public void removeBullet(Bullet bullet) {
		// TODO Auto-generated method stub
		
	}


	public void fireBullet() {
		// TODO Auto-generated method stub
		
	}


	public int getNbBulletsOnShip() {
		// TODO Auto-generated method stub
		return 0;
	}



	
}
