package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class that defines a bullet for the Asteroids game.
 * 
 * @author Michiel Janssen & Jelle Pelgrims
 *
 */

public class Bullet extends Entity {
	
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
	
	/**
	 * Returns the minimum radius for this bullet
	 */
	public double getMinRadius() {return 1;}
	
	/**
	 * Returns the minimum density for this bullet
	 */
	public double getMinDensity() {return 7.8E12;}
	
	/**
	 * Terminates this bullet and removes any associations with a ship
	 * @See implementation
	 */
	public void terminate() {
		if (isPartOfShip()) {
			ship.removeBullet(this);
			ship = null;
		}
		super.terminate();
	}
	
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
	 * Returns the mass of the bullet.
	 * @see implementation
	 */
	@Basic
	public double getMass(){
		double mass = (4/3.0)*Math.PI*Math.pow(this.getRadius(), 3)*getMinDensity();
		return mass;
	}
	
	/**
	 * Returns the source of the bullet.
	 */
	@Basic
	public Ship getSource(){
		return this.source;
	}

	/**
	 * Returns the ship associated with this bullet or null if no associations exist
	 */
	public Ship getShip(){
		return this.ship;
	}
	
	/**
	 * Returns the owner (a ship or a world) of this bullet, if none returns null 
	 */
	public Object getOwner() {
		if (world != null) {
			return world;
		} else if (ship != null) {
			return ship;
		}
		return null;
	}
	
	/**
	 * Returns a boolean indicating if this bullet can have the given object as owner
	 * @see implementation
	 */
	public boolean canHaveAsOwner(Object object) {
		return !isOwned() && object != null;
		
	}
	
	/**
	 * Sets the owner and source of this bullet to the given ship
	 * @see implementation
	 */
	public void changeOwner(Ship ship) {
		if (canHaveAsOwner(ship)) {
			this.ship = ship;
			this.source = ship;
		}
	}
	
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
		return getOwner() instanceof Ship;
	}
	
	/**
	 * A function that resolves a collision event between and planetoid and another entity.
	 * 
	 * @param entity
	 * @param this
	 * @post This function executes in such a manner that ensures that, at the end of the function:
	 * 			* In the case that the entity is a Ship: if the bullet hits it's own ship the bullet is added to the collection
	 *            of bullets. Otherwise the ship is destroyed.
	 * 			* In all other cases, both the entity and the bullet are destroyed.
	 */
	public void collide(Entity entity) {
		if (entity instanceof Ship) {
			if (getSource() == entity) {
				setCounter(0);
				
				getWorld().removeEntity(this);
				this.world = null;
				setPosition(entity.getPositionX(), entity.getPositionY());
				((Ship) entity).loadBullets(this);
			} else {
				this.destroy();
				entity.destroy();
			}
		} else {
			this.destroy();
			entity.destroy();
		}
	}
	
	/**
	 * A function that resolves a collision event between and bullet and a world.

	 * @param world
	 * @post This function executes in such a manner that ensures that, at the end of the function:
	 * 			* Bounce a bullet when it hits a wall.
	 * 			* Destroy a bullet when it hits a wall for the thirth time.
	 */
	public void collide(World world) {
		world.defaultCollide(this);
		if (Counter() == true){
			world.removeEntity(this);
			this.terminate();
		}
	}
	
}




