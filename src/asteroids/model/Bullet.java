package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class that defines a bullet for the Asteroids game.
 * 
 * @author Michiel Janssen & Jelle Pelgrims
 *
 */

public class Bullet extends Entity {

	protected static double MAX_SPEED = 300000;
	protected static double MIN_RADIUS = 1;
	protected static double DENSITY = 7.8E12;
	
	private int counter = 0;
	private Ship ship; 
	private Ship source;

	/**
	 * Initialize this new bullet with a given position, velocity, radius.
	 * 
	 * @see superclass constructor
	 */
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius) {
		super(x, y, xVelocity, yVelocity, radius);
	}
	
	public void terminate() {
		if (isPartOfShip()) {
			ship.removeBullet(this);
			ship = null;
		}
		super.terminate();
	}
	
	/**
	 * Returns the maximum speed of a bullet
	 */
	@Override
	public double getMaxSpeed() {return MAX_SPEED;}

	/**
	 * Returns the minimum speed of a bullet
	 */
	@Override
	public double getMinRadius() {return MIN_RADIUS;}

	/**
	 * Returns the minimum density of a bullet
	 */
	@Override
	public double getMinDensity() {return MIN_DENSITY;}
	
    /**
     * Checks when a bullet is about to hit a wall for the third time.
     * @see implementation
     */
	public boolean Counter(){
		if (counter == 2){		
			return true;
		} else {
			setCounter(getCounter() + 1);
			return false;
		}
	}
	
	/**
	 * Get the value of the counter
	 * @see implementation
	 */
	public int getCounter() {
		return counter;
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
	 * Check whether a bullet can be part of a world.
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
	 * @see implementation
	 */
	public void makePartOfShip(Ship ship) {
		if (canBePartOfShip()) {
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
		
	public void collide(Entity entity) {
		if (entity instanceof Ship) {
			if (getSource() == entity) {
				setCounter(0);
				
				getWorld().removeEntity(this);
				this.world = null;
				setPosition(entity.getPositionX(), entity.getPositionY());
				((Ship) entity).loadBullets(this);
			} else {
				this.terminate();
				entity.terminate();
			}
		} else {
			this.terminate();
			entity.terminate();
		}
	}
	
	public void collide(World world) {
		world.defaultCollide(this);
		if (Counter() == true){
			world.removeEntity(this);
		}
	}
	
}




