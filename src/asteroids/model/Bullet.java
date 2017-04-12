package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class that defines a bullet for the Asteroids game.
 * 
 * @author Michiel Janssen & Jelle Pelgrims
 *
 */

public class Bullet extends Entity {

	protected static final double MAX_SPEED = 300000;
	protected static final double MIN_RADIUS = 1;
	protected static final double DENSITY = 7.8E12;
	private int counter = 0;

	 
	private Ship ship; 
	private Ship source;

	/**
	 * Initialize this new bullet with a given position, velocity, radius.
	 * 
	 * @param x
	 *        The x-coordinate for this new bullet.
	 * @param y
	 * 		  The y-coordinate for this new bullet.
	 * @param xVelocity
	 *        The velocity in the x direction for this new bullet.
	 * @param yVelocity
	 *        The velocity in the y direction for this new bullet.
	 * @param radius
	 * 		  The given radius for this new bullet.
	 * @post  The position of this new bullet is equal to the given x- and y-coordinate.
	 * 		  | new.getPositionX() == x
	 * 	      | new.getPositionY() == y
	 * @post  The velocity of this new bullet is equal to the given x- and y-velocity.
	 *        | new.getVelocityX() == xVelocity
	 *        | new.getVelocityY() == yVelocity
	 * @post  The radius of this new ship is equal to the given radius.
	 *        | new.getRadius() == radius
	 */
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius) {
		setPosition(x, y);
		setVelocity(xVelocity, yVelocity);
		setRadius(radius);
	}
	
    /**
     * Checks when a bullet is about to hit a wall for the third time.
     * 
     * @see implementation
     */
	public boolean Counter(){
		if (counter == 2){		
			return true;
		} else {
			counter +=1;
			return false;
		}
	}
	
	/**
	 * Set the counter to the given counter.
	 * @param counter
	 */
	@Basic
	public void setCounter(int counter){
		this.counter = counter;
	}
	
	/**
	 * Returns the density of the bullet.
	 */
	@Basic
	public double getDensity() {return DENSITY;}
	
	/**
	 * Returns the mass of the bullet.
	 * 
	 * @see implementation
	 */
	@Basic
	public double getMass(){
		double mass = (4/3.0)*Math.PI*Math.pow(this.getRadius(), 3)*getDensity();
		return mass;
	}
	
	/**
	 * Returns the source of the bullet.
	 */
	@Basic
	public Ship getSource(){return this.source;}
	
	/**
	 * This method moves the bullet with a given valid duration.
	 * 
	 * Implemented defensively.
	 * 
	 * @post The new xPosition of this bullet is the current xPosition plus the current xVelocity*duration.
	 *       | new.getPositionX() == this.getPositionX() + this.getVelocityX()*duration
	 * @post The new yPosition of this bullet is the current yPosition plus the current yVelocity*duration.
	 *       | new.getPositionY() == this.getPositionY() + this.getVelocityY()*duration
	 *       
	 * @param  duration
	 * @throws IllegalArgumentException
	 *         | ! isValidDuration(duration)
	 */
	public void move(double duration) throws IllegalArgumentException {
		if (isValidDuration(duration)) {
			double deltaX = getVelocityX()*duration;
			double deltaY = getVelocityY()*duration;
			setPosition(getPositionX()+deltaX, getPositionY()+deltaY);
		} else {
			throw new IllegalArgumentException();
		}
		
	}
	
	/**
	 * Check whether a bullet can be part of a world.
	 * 
	 * @see implementation
	 */
	public boolean canBePartOfWorld() {
		if (isPartOfWorld() || isPartOfShip()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Check whether a bullet can be part of a ship.
	 * 
	 * @see implementation
	 */
	public boolean canBePartOfShip(){
		if (isPartOfWorld() || isPartOfShip()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Make a bullet part of a ship.
	 * 
	 * @see implementation
	 */
	public void makePartOfShip(Ship ship) {
		if (!isPartOfWorld() && !isPartOfShip()) {
			this.ship = ship;
			this.source = ship;
		}
	};
	
	/**
	 * Remove a bullet from a ship.
	 */
	@Basic
	public void removeFromShip() {
		this.ship = null; 
	};
	
	/**
	 * Check whether a bullet is part of a ship.
	 */
	public boolean isPartOfShip() {
		if (this.ship instanceof Ship) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Return the current ship a bullet belongs to.
	 */
	@Basic
	public Ship getShip() {return this.ship;}

}




