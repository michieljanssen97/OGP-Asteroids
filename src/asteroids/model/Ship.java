package asteroids.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import asteroids.model.programs.FalseProgramException;
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
	
	private Set<Bullet> bullets = new HashSet<Bullet>();
	
	private boolean thrusterActive = false;
	
	/**
	 * Initialize this new ship with a given position, velocity, radius and orientation.
	 * @see superclass constructor
	 * @effect The parameters of the superclass are set using the given parameters for position, velocity and radius
	 * 		 | super(x, y, xVelocity, yVelocity, radius)
	 * @post  The orientation of this new ship is equal to the given orientation.
	 *        | new.getOrientation() == orientation 
	 * @post  The mass of this new ship is equal to the given orientation.
	 *        | new.getMass() == mass
	 * @throws IllegalArgumentException
	 */
	public Ship(double x, double y, double xVelocity, double yVelocity, double radius, double orientation, double mass) throws IllegalArgumentException {
		super(x, y, xVelocity, yVelocity, radius);
		try {
			setOrientation(orientation);
			setMass(mass);
		} catch (IllegalArgumentException e) {
			throw e;
		}
		
	};
	
	/**
	 * Return the maximum exerted force on a ship.
	 */
	public double getThrusterForce() {return 1.1E18;}
	public double getMinRadius() {return 10;}
	public double getMinDensity() {return 1.42E12;}
	
	/**
	 * This method moves the ship with a given valid duration.
	 * 
	 * Implemented defensively.
	 * 
	 * @param  duration
	 * @invar The duration must be valid
	 * 		  | isValidDuration(duration) == true
	 * @post The new xPosition of this ship is the current xPosition plus the current xVelocity*duration.
	 *       | new.getPositionX() == this.getPositionX() + this.getVelocityX()*duration
	 * @post The new yPosition of this ship is the current yPosition plus the current yVelocity*duration.
	 *       | new.getPositionY() == this.getPositionY() + this.getVelocityY()*duration
	 * @throws IllegalArgumentException
	 * 		   The duration is invalid
	 *         | ! isValidDuration(duration)
	 */
	@Override
	public void move(double duration) throws IllegalArgumentException {
		if (isValidDuration(duration)) {		
			if (IsThrusterActive()) {
				double amount = this.getAcceleration();
				if (amount < 0) {
					amount = 0;
				}
				this.thrust(amount*duration);
			}
			
			double deltaX = getVelocityX()*duration;
			double deltaY = getVelocityY()*duration;
			setPosition(getPositionX()+deltaX, getPositionY()+deltaY);
		} else {
			throw new IllegalArgumentException("Duration is not valid");
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
	 * 
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
		if (IsThrusterActive()) {
			return getThrusterForce()/(float)this.getMass();
		} else {
			return 0;
		}
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
	 * @invar The bullets must be part of the ship and must lie within the ship's bounds
	 * 		  | (bullet.canbePartOfShip(this) && bullet.withinBoundaries(this)) == true
	 * @post  The bullet(s) are added to a ship's set of bullets and the bullet(s) are also associated with that ship.
	 * 		  | for (bullet in bullets)
	 *        | 	bullet.makePartOfShip(this)
	 *        | 	this.bullets.add(bullet)
	 *        | 	bullet.isPartOfShip() == true
	 * @throws NullPointerException
	 *         | bullet == null
	 * @throws AssertionError
	 *        | (!bullet.canBePartOfShip(this)) || (!bullet.withinBoundaries(this))
	 */
	public void loadBullets(Bullet... bullets) throws AssertionError {
		for(Bullet bullet: bullets) {
			if (bullet.withinBoundaries(this) && bullet.canHaveAsOwner(this)){
				bullet.changeOwner(this);
				this.bullets.add(bullet);
			} else {
				throw new AssertionError("Either the bullet cannot be part of the ship or the bullet doesn't lie within the ship's boundaries");
			}
		}
	}

    /**
     * Removes a bullet from the set of bullets and removes the association with it's ship.
     * @param bullet
     * @see implementation
     */
	public void removeBullet(Bullet bullet) {
		bullet.removeFromShip();
		if (bullet != null && bullets.contains(bullet)) {
			this.bullets.remove(bullet);
		}
	}

    /**
     * This method makes it possible to fire a bullet from a ship.
     * @effect This function executes in a way that ensures that at the end of this function:
     * 		 * A bullet is only shot if getNbBulletsOnShip() > 0
     * 	     * The fired bullet is part of the world (and not of the ship) if it is fired from within the boundaries of the world
     * 		 * The fired bullet is positioned in front of the ship (depending on the ships orientation)
     *       * The fired bullets speed is equal to 250 km/s
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
					
					if (world.significantOverlap(bullet)) {
						Entity collidee = world.getEntities().stream()
							.filter(entity -> bullet.overlap(entity))
							.findFirst()
							.orElse(null);
						collidee.terminate();
						bullet.terminate();
					} else {
						this.world.addEntity(bullet);
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
	
	private Program program;
	private void setProgram(Program newProgram){
		this.program = newProgram;
	}
	
	public Program getProgram() {
		return this.program;
	}

	public List<Object> executeProgram(double dt) {
		try {
			getProgram().execute(dt, this, this.getWorld());
		} catch (FalseProgramException e) {
				
		}
		if (getProgram().getEndingSourceLocation() != null){
			return null;
		} else{
			return getProgram().getPrintedObjects();
		}
	}

	public void loadProgram(Program program) {
		setProgram(program);
	}
	
	public void collide(Entity entity) {
		if (entity instanceof Ship) {defaultCollide(entity);}
		else {entity.collide(this);}
	}

	@Override
	public void collide(World world) {
		world.defaultCollide(this);
	}
	
}
