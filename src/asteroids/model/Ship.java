package asteroids.model;


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
public class Ship {
	
	private static final double MAX_SPEED = 300000;
	private static final double MIN_RADIUS = 10;
	
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
	 *        | (! isValidPosition(x,y)) or (! isValidRadius(radius)) or (!isValidVelocity(xVelocity,yVelocity))
	 */
	public Ship(double x, double y, double xVelocity, double yVelocity, double radius, double orientation) throws IllegalArgumentException {
		try {
			setPosition(x, y);
			setVelocity(xVelocity, yVelocity);
			setRadius(radius);
			setOrientation(orientation);
		} catch (Exception e) {
			throw e;
		}
		
	};
	
	private double orientation;
	/**
	 * Return the orientation of this ship.
	 */
	public double getOrientation() {return this.orientation;}
	
	/**
	 * Implement nominally.
	 * 
	 * @pre The angle must be between 0 and 2*pi.
	 * 		| 0 <= angle < 2*pi
	 * @post The orientation of the ship is equal to the given angle.
	 *       | new.getOrientation() == angle
	 * @param angle
	 */
	public void setOrientation(double angle) {this.orientation = angle;}
	
	private double radius;
	/**
	 * Return the radius of this ship.
	 */
	public double getRadius() {return this.radius;}
	
	/**
	 * Check whether a radius is valid. The radius must be greater then 10.
	 * 
	 * @param radius
	 * @return (radius > MIN_RADIUS) && (! Double.isNaN(radius))
	 */
	public boolean isValidRadius(double radius) {
		return (radius > MIN_RADIUS) && (! Double.isNaN(radius));
	}
		
	/**
	 * Set the radius to a given valid radius, throws an error if the radius is not valid.
	 * 
	 * Implement defensively.
	 * 
	 * @param radius
	 * 
	 * @post  The radius of the ship is equal to the given radius.
	 *        | new.getRadius() == radius
	 *        
	 * @throws IllegalArgumentException
	 * 		   The given radius is not a valid radius.
	 *         | ! isValidRadius(radius)
	 */
	public void setRadius(double radius) throws IllegalArgumentException {
		if (isValidRadius(radius)) {
			this.radius = radius;
		} else {
			throw new IllegalArgumentException("Radius must be larger than 10 Km.");
		}
	}
	
	private double x;
	private double y;
	
	/**
	 * Check whether a given position is valid position by returning a boolean indicating validness.
	 * 
	 * @param x
	 * @param y
	 * @return !(Double.isNaN(x) || Double.isNaN(y))
	 */
	private boolean isValidPosition(double x, double y) {
		return !(Double.isNaN(x) || Double.isNaN(y));
	}
	
	/**
	 * Return the x-coordinate of this ship.
	 */
	public double getPositionX() {return this.x;}
	
	/**
	 * Return the x-coordinate of this ship.
	 */
	public double getPositionY() {return this.y;}
	
	/**
	 * Implement defensively
	 * 
	 * Set the ship to a valid position.
	 * @param x
	 * @param y
	 * @post  The position of the ship is equal to the given x- and y-coordinate.
	 *        | new.getPositionX() == x
	 *        | new.getPositionY() == y
	 *        
	 * @throws IllegalArgumentException
	 *         | ! isValidPosition(x,y)
	 */
	public void setPosition(double x, double y) throws IllegalArgumentException{
		if (isValidPosition(x, y)) {
			this.x = x;
			this.y = y;
		} else {
			throw new IllegalArgumentException("Position must not be NaN.");
		}
	}
	
	private double velocityX;
	private double velocityY;
	
	/**
	 * Check whether a given velocity is a valid velocity by returning a boolean indicating validness.
	 * 
	 * @param x
	 *        The x-coordinate of this ship.
	 * @param y
	 *        The y-coordinate of this ship.
	 * @return boolean
	 */
	private boolean isValidVelocity(double x, double y) {
		if (Double.isNaN(x) || Double.isNaN(y)) {
			return false;
		} else {
			double speed = Math.sqrt(Math.pow(this.getVelocityX(), 2)+Math.pow(this.getVelocityY(), 2));
			return 0 <= speed && speed <= MAX_SPEED;
		}
	}
	/**
	 * Return the xVelocity of this ship.
	 */
	public double getVelocityX() {return this.velocityX;}
	/**
	 * Return the yVelocity of this ship.
	 */
	public double getVelocityY() {return this.velocityY;}
	
	/**
	 * Set the velocity to a given valid velocity. If the given velocity is larger than the allowed maximum speed 
	 * it will be reduced until it is valid. If the given velocity is NaN the velocity will be set to zero.
	 * 
	 * The tangent of the enclosed angle should remain constant when reducing xVelocity and yVelocity!
	 * | tan(alfa) = this.getVelocityY/this.getVelocityX = new.getVelocityY/new.getVelocityX = constant
	 * | new.getVelocityX = sqrt((MAX_SPEED^2)/(1+tan(alfa)^2))
	 * | new.getVelocityY = sqrt(new.getVelocityX*tan(alfa))
	 * 
	 * Implemented totally.
	 * 
	 * @param x
	 * 	      The x-coordinate of this ship.
	 * @param y
	 *        The y-coordinate of this ship.
	 * @post  ...
	 *        | new.getVelocityX() == x
	 * @post  ...
	 *        | new.getVelocityY() == y
	 */
	public void setVelocity(double x, double y) {
		if (isValidVelocity(x, y)) {
			this.velocityX = x;
			this.velocityY = y;
		} else {
			if (! (Double.isNaN(x) || Double.isNaN(y))) {
				double constantAngle = Math.atan(x/y);
			    this.velocityX = Math.sqrt((Math.pow(MAX_SPEED,2))/(1+Math.pow(Math.tan(constantAngle), 2)));
				this.velocityY = this.velocityX * Math.tan(constantAngle) ; 
			} else {
				this.velocityX = 0;
				this.velocityY = 0;
			}
		}
	}
	
	/**
	 * Check whether the given duration is valid.
	 * 
	 * @param duration
	 * 		  The duration of this ship.
	 * @return duration >= 0
	 */
	public boolean isValidDuration (double duration) {
		return duration >= 0;
	}
	
	/**
	 * This method moves the ship with a given valid duration.
	 * 
	 * Implemented defensively.
	 * 
	 * @post The new xPosition of this ship is the current xPosition plus the current xVelocity equaled by its duration.
	 *       | new.getPositionX() = getPositionX() + getVelocityX()*duration
	 * @post The new yPosition of this ship is the current yPosition plus the current yVelocity equaled by its duration.
	 *       | new.getPositionY() = getPositionY() + getVelocityY()*duration
	 *       
	 * @param duration
	 * @throws IllegalArgumentException
	 *         | ! isValidDuration(duration)
	 */
	public void move(double duration) throws IllegalArgumentException {
		// Implement defensively
		if (isValidDuration(duration)) {
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
	 *      | new.getOrientation() == (this.orientation + angle) % (2*PI)
	 * @param angle
	 */
	public void turn(double angle) {
		setOrientation(this.orientation+ angle % (2*Math.PI));
	}
	
	/**
	 * This method accelerates the ship by a given amount.
	 * 
	 * Implemented totally.
	 * 
	 * @param amount
	 */
	public void thrust(double amount) {
		if (amount < 0) {
			amount = 0;
		}
		double newVelocityX = getVelocityX() + amount*Math.cos(getOrientation());
		double newVelocityY = getVelocityY() + amount*Math.sin(getOrientation());
		setVelocity(newVelocityX, newVelocityY);
				
	}
	
	public double getDistanceBetween(Ship other) throws NullPointerException {
		if (other == this) {
			return new Double(0);
		} else if (other == null) {
			throw new NullPointerException();
		}
		else {
			double distance = Math.sqrt(Math.pow((other.getPositionX()-this.getPositionX()), 2)+Math.pow((other.getPositionY()-this.getPositionY()), 2));
			return distance;
		}
	}

	public boolean overlap(Ship other) throws NullPointerException {
		// see if the distance between the centers of the two circles is less then r1+r2. Then they will overlap in the plane. 
		// r1+r2 > sqrt( (x2-x1)**2 + (y2-y1)**2 ) 	
		
		if (other == null) {
			throw new NullPointerException();
		} else {
			double radiusSum = this.getRadius() + other.getRadius();
			double distance = getDistanceBetween(other);
			return radiusSum >= distance;
		}
	}
	
public double getTimeToCollision(Ship other) throws IllegalArgumentException {
		
		// if two ships already overlap, the time to collision is set to zero
		// if deltaV * deltaR >= 0, time to collision is set to infinity
		// else if d <= 0, time to collision is set to infinity
		// else deltaT = - ((deltaV*deltaR+sqrt(d))/(deltaV+deltaV))
	
		if (other == null){
			throw new IllegalArgumentException();
		} else if (other == this){
			return new Double(0);
		} else {
			
			double deltaPositionX = this.getPositionX()-other.getPositionX();
			double deltaPositionY = this.getPositionY()-other.getPositionY();

			double deltaVelocityX = this.getVelocityX()-other.getVelocityX();
			double deltaVelocityY = this.getVelocityY()-other.getVelocityY();
			
			double deltaRR = Math.pow(deltaPositionX, 2) + Math.pow(deltaPositionY, 2);
			double deltaVV = Math.pow(deltaVelocityX, 2) + Math.pow(deltaVelocityY, 2);
			double deltaVR = (deltaVelocityX*deltaPositionX)  + (deltaVelocityY*deltaPositionY);
			double d = Math.pow(deltaVR, 2) - ((deltaVV)*(deltaRR - Math.pow(this.getDistanceBetween(other), 2)));
			
			
			if (this.overlap(other)) {
				return new Double(0);
			} 
			else {
				if (deltaVR >= 0){
					return Double.POSITIVE_INFINITY;
				} else if (d <=0){
					return Double.POSITIVE_INFINITY;
				} else {
					return - ((deltaVR + Math.sqrt(d))/(deltaVV));
				}
			}
		}
	}
	public double[] getCollisionPosition(Ship other) throws NullPointerException {
		
		// The Collision position is the current position plus the time to collision multiplied by its velocity, with is no acceleration
		// In math: x(t) = x0 + Vx * getTimeToCollision
		//          y(t) = y0 + VY * getTimeToCollision          
		
		if (other == null){
			throw new NullPointerException();
		} else if (this.overlap(other)) {
			return null;
		} 
		else {
			
			if (getTimeToCollision(other) != Double.POSITIVE_INFINITY ){
				double[] pos =  {this.getPositionX()+this.getVelocityX()*this.getTimeToCollision(other), this.getPositionY()+this.getVelocityY()*this.getTimeToCollision(other)};
				return pos;	
			} else {
				return null;
			}
		}
	}
}
