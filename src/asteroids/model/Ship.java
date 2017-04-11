package asteroids.model;

import java.util.HashSet;
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
	private static final double MIN_DENSITY = 1.42E12;
	private static final double THRUSTER_FORCE = 1.1E21;
	
	
	private Set<Bullet> bullets = new HashSet<Bullet>();
	
	private boolean thrusterActive = false;
	/**
	 * Return the maximum exerted force on a ship.
	 */
	public double getThrusterForce() {return THRUSTER_FORCE;}
	
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
			double deltaX = getVelocityX()*duration;
			double deltaY = getVelocityY()*duration;
			setPosition(getPositionX()+deltaX, getPositionY()+deltaY);
			
			if (IsThrusterActive()) {
				double amount = this.getAcceleration();
				if (amount < 0) {
					amount = 0;
				}
				this.thrust(amount*duration);
			}
			
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
	/**
	 * This method toggles the thruster of a ship.
	 *@post If the thruster is active the thruster is set to non active.
	 * 	    | if (thrusterActive) 
	 *      |    then thrusterActive == false
	 *@post If the thruster is non active the thruster is set to active.
	 * 	    | if (!thrusterActive) 
	 *      |    then thrusterActive == true    
	 */
	public void toggleThruster() {
		if (thrusterActive) {
			thrusterActive = false;
		} else if (!thrusterActive) {
			thrusterActive = true;
		}
	}
	/**
	 * Returns the state of the thruster.
	 */
    @Basic
	public boolean IsThrusterActive() {
		return this.thrusterActive;
	}

    /**
     * Returns the amount or acceleration that a ship will feel.
     * @see implementation
     */
	public double getAcceleration() {
		return getThrusterForce()/(float)this.getMass();
	}

    /**
     * Returns a set of bullets that belongs to a ship.
     */
	public Set<Bullet> getBullets() {
		return this.bullets;
	}
	
	/**
	 * This method adds one or multiple bullet(s) to a certain ship if the bullet is a valid bullet.
	 * 
	 * @param bullets
	 *        The collection of bullets that needs to be added onto a ship.
	 * @post  The bullet(s) are added to a ship's set of bullets and the bullet(s) are also associated with that ship.
	 *        | bullet.makePartOfShip(this)
	 *        | this.bullets.add(bullet)
	 *        | bullet.isPartOfShip() == true
	 * @throws NullPointerException
	 *         | bullet == null
	 * @throws AssertionError
	 *        | (!bullet.makePartOfShip(this)) || (!bullet.withinBoundaries(this))
	 */
	public void loadBullets(Bullet... bullets) throws NullPointerException, AssertionError {
		try {
			for(Bullet bullet: bullets) {
				if (bullet != null) {
					if (bullet.withinBoundaries(this) && bullet.canBePartOfShip()){
						bullet.makePartOfShip(this);
						this.bullets.add(bullet);
					} else {
						throw new AssertionError("Either the bullet cannot be part of the ship or the bullet doesn't lie within the ship's boudnaries");
					}
				}
			}
		} catch (Exception e) {
			throw new NullPointerException();
		}
	}

    /**
     * Removes a bullet from the set of bullets and removes the association with it's ship.
     * @param bullet
     */
	public void removeBullet(Bullet bullet) {
		bullet.removeFromShip();
		this.bullets.remove(bullet);
	}

    /**
     * This method makes it possible to fire a bullet from a ship.
     * @post If the given bullet (random bullet chosen out of the set of bullets) is not equal to zero
     *       the ship can fire that bullet. The bullet gets a x- and y-coordinate as well as a x- and y-velocity.
     *       After the position and velocity is set, the bullet is removed from the collection of bullets.
     *       Then the method checks whether the bullet can be added to the world or not.
     *      
     *      WAARSCHIJNLIJK HIER OOK EEN DECLARATIEVE BESCHRIJVING BETER VAN TOEPASSING....
     */
	public void fireBullet() {
		if (this.isPartOfWorld() && (getNbBulletsOnShip() > 0)) {
			
			Bullet bullet = this.bullets.iterator().next();
			
			if (bullet != null) {

				double distanceBetweenBulletAndShip = 1.0;
				double posX = this.getPositionX()+(this.getRadius()+bullet.getRadius()+distanceBetweenBulletAndShip)*Math.cos(this.orientation);
				double posY = this.getPositionY()+(this.getRadius()+bullet.getRadius()+distanceBetweenBulletAndShip)*Math.sin(this.orientation);
				
				bullet.setPosition(posX, posY);
				bullet.setVelocity(250*Math.cos(this.orientation), 250*Math.sin(this.orientation));
				
				removeBullet(bullet);
				if (bullet.withinBoundaries(world)) {
					
					try {
						this.world.addEntity(bullet);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}	
				} else {
					bullet.terminate();
				}
				

			}
		}		
	}
   /**
    * Return the number of bullets that are associated with a ship.
    */
	@Basic
	public int getNbBulletsOnShip() {
		return this.bullets.size();
	}
	/**
	 * Return whether the given mass is a valid mass.
	 * @see implementation
	 */
	@Override
	protected boolean isValidMass(double mass) {
		if (mass >= (4.0/3.0)*Math.PI*Math.pow(this.getRadius(), 3)*getMinDensity() ) {
			return true;
		}
		return false;
	}
	/**
	 * Return the mass of a ship (ship + bullets loaded on that ship).
	 * @post  The given ship's total mass is the mass of the ship itself plus all the masses of the bullets.
	 *        | bullet_mass == 0
	 *        | for (Bullet bullet : this.getBullets())
			  |       bullet_mass += bullet.getMass();
			  | new.getMass() == this.getMass() + bullet_mass
	 */
	public double getMass() {
		double bullet_mass = 0;
		for (Bullet bullet : this.getBullets()) {
			bullet_mass += bullet.getMass();
		}
		
		return bullet_mass + this.mass;
		
	}



	
}
