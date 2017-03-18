package asteroids.model.entity;

import asteroids.model.World;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

public class Entity implements IEntity {
	
	protected static final double MAX_SPEED = 300000;
	protected static final double MIN_RADIUS = 10;

	protected double x;
	protected double y;
	
	protected double velocityX;
	protected double velocityY;
	
	protected double radius;
	protected double orientation;
	
	/**
	 * Check whether a given position is valid position by returning a boolean indicating validness.
	 * 
	 * @param x
	 *        The x-coordinate for this new ship.
	 * @param y
	 * 		  The y-coordinate for this new ship.
	 * @return !(Double.isNaN(x) || Double.isNaN(y))
	 */
	protected boolean isValidPosition(double x, double y) {
		return !(Double.isNaN(x) || Double.isNaN(y));
	}
	
	/**
	 * Return the x-coordinate of this ship.
	 * 
	 * @return this.x
	 */
	@Basic @Immutable
	public double getPositionX() {return this.x;}
	
	/**
	 * Return the y-coordinate of this ship.
	 * 
	 * @return this.y
	 */
	@Basic @Immutable
	public double getPositionY() {return this.y;}
	
	/**
	 * Set the ship to a valid position.
	 * 
	 * Implement defensively
	 * 
	 * @param x
	 *        The x-coordinate for this new ship.
	 * @param y
	 *        The y-coordinate for this new ship.
	 * @invar x and y are real numbers. 
	 * @post  The position of the ship is equal to the given x- and y-coordinate.
	 *        | new.getPositionX() == x
	 *        | new.getPositionY() == y
	 *        
	 * @throws IllegalArgumentException
	 *         | ! isValidPosition(x,y)
	 */
	@Raw
	public void setPosition(double x, double y) throws IllegalArgumentException{
		if (isValidPosition(x, y)) {
			this.x = x;
			this.y = y;
		} else {
			throw new IllegalArgumentException("Position must not be NaN.");
		}
	}

	/**
	 * Check whether a given velocity is a valid velocity by 
	 * returning a boolean indicating validness.
	 * 
	 * @param x   
	 *        The x-velocity of this ship.
	 * @param y
	 *        The y-velocity of this ship.
	 * @return True if and only if the speed is greater then zero 
	 *         and the speed is less or equal to MAX_SPEED.
	 *         | result == 
	 *         |       (0 <= speed && speed <= MAX_SPEED)
	 */
	protected boolean isValidVelocity(double x, double y) {
		if (Double.isNaN(x) || Double.isNaN(y)) {
			return false;
		} else {
			double speed = Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
			return 0 <= speed && speed <= MAX_SPEED;
		}
	}

	/**
	 * Return the horizontal velocity of this ship.
	 * @return this.velocityX
	 */
	@Basic @Immutable
	public double getVelocityX() {
		return this.velocityX;
	}

	/**
	 * Return the vertical velocity of this ship.
	 * @return this.velocityY
	 */
	@Basic @Immutable
	public double getVelocityY() {
		return this.velocityY;
	}
	
	/**
	 * Set the velocity to a given valid velocity. If the magnitude of the velocity is larger than the allowed maximum speed (=300000) 
	 * it will be reduced until it is valid (=MAX_SPEED). If the given velocity is NaN the velocity will be set to zero.
	 * 
	 * Implemented totally.
	 * 
	 * @param x
	 * 	      The x-coordinate of this ship.
	 * @param y
	 *        The y-coordinate of this ship.
	 * @post  If the magnitude of the velocity is less then MAX_SPEED 
	 *        then we set the velocity to the given velocity.
	 *        | new.getVelocityX() == x
	 *        | new.getVelocityY() == y
	 * @post  If the magnitude of the velocity is greater then MAX_SPEED
	 *        then we reduce the magnitude until it becomes the MAX_SPEED.
	 *        The tangent of the enclosed angle should remain constant 
	 *        when reducing xVelocity and yVelocity.
	 *        | tan(alfa) = this.getVelocityY/this.getVelocityX = new.getVelocityY/new.getVelocityX = MAX_SPEED
	 *        | new.getVelocityX = sqrt((MAX_SPEED^2)/(1+tan(alfa)^2))
	 *        | new.getVelocityY = sqrt(new.getVelocityX*tan(alfa))
	 */
	public void setVelocity(double x, double y) {
		if (isValidVelocity(x, y)) {
			this.velocityX = x;
			this.velocityY = y;
		} else {
			if (! (Double.isNaN(x) || Double.isNaN(y))) {
				double constantAngle = Math.atan(y/x);
			    this.velocityX = Math.sqrt((Math.pow(MAX_SPEED,2))/(1+Math.pow(Math.tan(constantAngle), 2)));
				this.velocityY = this.velocityX * Math.tan(constantAngle) ; 
			} else {
				this.velocityX = 0;
				this.velocityY = 0;
			}
		}
	}

	/**
	 * Return the radius of this ship.
	 * @return this.radius
	 */
	@Basic @Immutable @Override
	public double getRadius() {
		return this.radius;
	}
	
	/**
	 * Check whether a radius is valid. The radius must be greater then 10.
	 * 
	 * @param radius
	 * @return (radius > MIN_RADIUS) && (! Double.isNaN(radius))
	 */
	protected boolean isValidRadius(double radius) {
		return (radius > MIN_RADIUS) && (! Double.isNaN(radius));
	}
		
	/**
	 * Set the radius to a given valid radius, throws an error if the radius is not valid.
	 * 
	 * Implement defensively.
	 * 
	 * @param radius
	 * @invar radius must be larger than 10
	 * 		  | 10 < radius
	 * @post  The radius of the ship is equal to the given radius.
	 *        | new.getRadius() == radius
	 * @throws IllegalArgumentException
	 * 		   The given radius is not a valid radius.
	 *         | ! isValidRadius(radius)
	 */
	@Raw
	public void setRadius(double radius) throws IllegalArgumentException {
		if (isValidRadius(radius)) {
			this.radius = radius;
		} else {
			throw new IllegalArgumentException("Radius must be larger than 10 Km.");
		}
	}
	

	/**
	 * Return the orientation of this entity.
	 * @return this.orientation
	 */
	@Basic @Immutable @Override
	public double getOrientation() {
		return this.orientation;
	}

	/**
	 * Sets the orientation to the given angle;
	 * 
	 * Implement nominally.
	 * 
	 * @pre The angle must be between 0 and 2*pi.
	 * 		| 0 <= angle < 2*pi
	 * @post The orientation of the ship is equal to the given angle.
	 *       | new.getOrientation() == angle
	 * @param angle
	 */
	public void setOrientation(double angle) {
		this.orientation = angle;
	}
	
	@Override
	public boolean isPartOfWorld() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method measures the distance between two given ships.
	 * 
	 * Implement defensively. 
	 * 
	 * @param  other
	 * 		   The second (other) ship. We use this ship to measure the distance.
	 * @return A distance if and only if the other ship is not null and
	 *         this ship is not the same as the other ship ((other != ship) && (other != null)).
	 * @throws NullPointerException
	 *         The other ship does not exist.
	 *         | other == null
	 */
	
	public double getDistanceBetween(Entity other) throws NullPointerException {
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
	
	/**
	 * This method determines whether there is overlap between two ships.
	 * 
	 * Implement defensively.
	 *  
	 * @param  other
	 *         The second (other) ship. We use this ship to determine the possibility of overlap.
	 * @return True if and only if two ships overlap.
	 *         This means that the other ship musn't be null.
	 *         Two ships will overlap (return true) if the sum of their radii is 
	 *         greater then the distance between these ships.
	 *          -> r1+r2 >= sqrt( (x2-x1)**2 + (y2-y1)**2 )    
	 *         | result =
	 *         |       (getRadius()+ other.getRadius) >=
	 *         |       (getDistanceBetween(other))
	 *         
	 * @throws NullPointerException
	 *         The other ship does not exist.
	 *         | other == null
	 */
	public boolean overlap(Entity other) throws NullPointerException {
		if (other == null) {
			throw new NullPointerException();
		} else {
			double radiusSum = this.getRadius() + other.getRadius();
			double distance = getDistanceBetween(other);
			return radiusSum >= distance;
		}
	}
	
	/**
	 * This method calculates when, if ever, two ships will collide. 
	 * 
	 * Implement defensively.
	 * 
	 * Declarative specification for the case where the method returns a finite value:
	 * 
	 *		In order to get the time to a possible collision we will first have to check if the two ships are not
	 *		one and the same. In that case the time to collision is null and an error is thrown.
	 *
	 * 		After we have verified that the two ships are different entities we need to perform another check to
	 * 		see if the two ships are not currently colliding. In that case the collision time is zero, since the
	 * 		ship already collided. 
	 * 
	 * 		If the ships are not one and the same and they are not currently colliding then we have to check if
	 * 		the two ships are on a collision course by using the formula given in the project specification.
	 * 
	 * 		If deltaVR (The dot product of the vector containing the difference in velocity between the ships 
	 * 		and the vector containing the difference in position between the ships) is not bigger or equal to 
	 * 		zero and the "d value" (given in the specification of the project) is not smaller or equal to zero 
	 * 		then the function will return a positive real collision time. 
	 * 
	 * @param other
	 *        The second (other) ship. We use this ship to determine the time to collision.
	 * @throws NullPointerException
	 *         The other ship does not exist.
	 *         | other == null
	 */
	public double getTimeToCollision(Entity other) throws NullPointerException {
		if (other == null){
			throw new NullPointerException();
		} else if (other == this){
			return new Double(0);
		} else {
			
			double deltaPosX = other.getPositionX()-this.getPositionX();
			double deltaPosY = other.getPositionY()-this.getPositionY();

			double deltaVelX = other.getVelocityX()-this.getVelocityX();
			double deltaVelY = other.getVelocityY()-this.getVelocityY();
			
			double deltaRR = Math.pow(deltaPosX, 2) + Math.pow(deltaPosY, 2);
			double deltaVV = Math.pow(deltaVelX, 2) + Math.pow(deltaVelY, 2);
			double deltaVR = (deltaVelX*deltaPosX)  + (deltaVelY*deltaPosY);
			double d = Math.pow(deltaVR, 2) - ((deltaVV)*(deltaRR - Math.pow(this.getRadius()+other.getRadius(), 2)));
			
			
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
	
	/**
	 * This method calculates where, if ever, two ships will collide.
	 * 
	 * The Collision position is the current position plus the time to collision multiplied by its velocity.
	 * In math: x(t) = this.getPostionX() + this.getVelocityX() * this.getTimeToCollision(other)
	 *          y(t) = this.getPostionY() + this.getVelocityY() * this.getTimeToCollision(other)
	 * In case the ship gets an acceleration, we measure the new velocity after the thrust has stopped.
	 * We use this new velocity to measure the new time to collision and the new collision position. 
	 * 
	 * Implement defensively.
	 * 
	 * @param other
	 *        The second (other) ship. We use this ship to determine the position of collision.
	 * @return A position if and only if the other ship is not null, the time 
	 *         to collision is not equal to infinity and the two ships 
	 *         are not overlapping.
	 * @return null
	 * 		   | this.overlap(other)
	 * @throws NullPointerException
	 *         The other ship does not exist.
	 *         | other == null
	 */
	public double[] getCollisionPosition(Entity other) throws NullPointerException {      
		if (other == null){
			throw new NullPointerException();
		} else if (this.overlap(other)) {
			return null;
		} 
		else {
			
			if (getTimeToCollision(other) != Double.POSITIVE_INFINITY ){
				double posX = this.getPositionX() + this.getTimeToCollision(other)*this.getVelocityX();
				double posY = this.getPositionY() + this.getTimeToCollision(other)*this.getVelocityY();
				double[] pos =  {posX, posY};
				return pos;	
			} else {
				return null;
			}
		}
	}
	

}
