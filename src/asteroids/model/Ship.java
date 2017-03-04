package asteroids.model;

public class Ship {
	
	/* General comments:
	 * position: kilometres
	 * velocity: km/s
	 * 
	 * 		- All aspects related to the position of the ship shall be worked out defensively
	 * 		- All aspects related to velocity must be worked out in a total manner.
	 * 		- All aspects related to the orientation of the ship must be worked out nominally
	 * 		- All aspects related to the radius must be worked out defensively.
	 * 
	 */
	public Ship(double x, double y, double xVelocity, double yVelocity, double radius, double orientation) throws IllegalArgumentException {
		
		setPosition(x, y);
		setVelocity(xVelocity, yVelocity);
		try {
			setRadius(radius);
		} catch (IllegalArgumentException e) {
			throw e;
		} 
		setOrientation(orientation);
		
	};
	private static final double MAX_SPEED = 300000;
	private static final double MIN_RADIUS = 10;

	private double orientation;
	public double getOrientation() {return this.orientation;}
	
	/**
	 * Implement nominally
	 * 
	 * @pre The angle must be between 0 and 2*pi.
	 * 		| 0 <= angle < 2*pi
	 * @post ...
	 *       | new.getOrientation() = angle
	 * @param angle
	 */
	public void setOrientation(double angle) {this.orientation = angle;}
	
	private double radius;
	public double getRadius() {return this.radius;}
	
	/**
	 * Check whether a radius is valid. The radius must be greater then 10.
	 * @param radius
	 * @return radius > 10
	 */
	public boolean isValidRadius(double radius){
		return radius > MIN_RADIUS;
		}
		
	/**
	 * Implement defensively
	 * Set the radius to a given valid radius.
	 * 
	 * @param radius
	 * 
	 * @post  ...
	 *        |new.getRadius() = radius
	 *        
	 * @throws IllegalArgumentException
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
	 * Check whether a position is valid.
	 * @param x
	 * @param y
	 * @return boolean 
	 */
	private boolean isValidPosition(double x, double y) {
		if (Double.isNaN(x) || Double.isNaN(y)) {
			return false;
		} else {
			return true;
		}
	}
	
	public double getPositionX() {return this.x;}
	public double getPositionY() {return this.y;}
	
	/**
	 * Implement defensively
	 * 
	 * Set the ship to a valid position.
	 * @param x
	 * @param y
	 * @post  ...
	 *        |new.getPosition() = position
	 *        
	 * @throws IllegalArgumentException
	 *         | isValidPostion(x,y)
	 */
	
	public void setPosition(double x, double y) throws IllegalArgumentException{
		if (isValidPosition(x, y)) {
			this.x = x;
			this.y = y;
		} else {
			throw new IllegalArgumentException("Position must not be null.");
		}
	}
	
	private double velocityX;
	private double velocityY;
	
	/**
	 * Implement totally
	 * 
	 * Check whether the given velocity is valid.
	 * @param x
	 * @param y
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
	
	public double getVelocityX() {return this.velocityX;}
	public double getVelocityY() {return this.velocityY;}
	
	/**
	 * Implement totally
	 * @param x
	 * @param y
	 * @pre   ...
	 *        | isValidVelocity(x,y)
	 * @post  ...
	 *        | new.getVelocityX = x
	 * @post  ...
	 *        | new.getVelocityY = y
	 */
	public void setVelocity(double x, double y) {
		if (isValidVelocity(x, y)) {
			this.velocityX = x;
			this.velocityY = y;
		} else {
			// reduce Vx and Vy without changing direction until magnitude < c
			// The tangens of the enclosed angle should remain constant
			// tan(alfa) = Vy/Vx = V'y/V'x = constant
			// sqrt(V'x^2 + V'y^2) = c followed by a substitution of V'y = V'x*tan(alfa) 
			// gets you V'x = sqrt(c^2/1+tan(alfa)^2)
						
			double constantAngle = Math.atan(x/y);
		    this.velocityX = Math.sqrt((Math.pow(MAX_SPEED,2))/(1+Math.pow(Math.tan(constantAngle), 2)));
			this.velocityY = this.velocityX * Math.tan(constantAngle) ; 
		}
	}
	/**
	 * Check whether the given duration is valid.
	 * @param duration
	 * @return boolean
	 */
	public boolean isValidDuration (double duration) {
		return duration >= 0;
	}
	
	/**
	 * Implement defensively
	 * 
	 * This method moves the ship with a given valid duration.
	 * 
	 * @pre  ...
	 *       | isValidDuration(duration)
	 *       
	 * @post ...
	 *       | new.getPositionX() = getPositionX() + getVelocityX()*duration
	 * @post ...
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
	
	public boolean isValidAngle(double angle) {
		return true;
	}
	
	public void turn(double angle) {
		// Implement nominally
		
		setOrientation(this.orientation+ angle % (2*Math.PI));
	}
	public void thrust(double amount) {
		// Implement totally
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
