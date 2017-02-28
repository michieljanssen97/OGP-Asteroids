package asteroids.model;

import java.awt.geom.Point2D;

import asteroids.util.ModelException;

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
	
	private static final double MAX_SPEED = 300000;
	private static final double MIN_RADIUS = 10;

	private double orientation;
	public double getOrientation() {return this.orientation;}
	
	/**
	 * Implement nominally (design by contract)
	 * @pre orientation must be between 0 and 2pi
	 * 		| 0 <= orientation <= 2pi
	 * @param orientation
	 */
	public void setOrientation(double angle) {this.orientation = angle;}
	
	private double radius;
	public double getRadius() {return this.radius;}
	
	// implement defensively
	
	public boolean isValidRadius(double radius) {
		return radius >= MIN_RADIUS;
	}
	public void setRadius(double radius) throws IllegalArgumentException {
		if (isValidRadius(radius)) {
			this.radius = radius;
		} else {
			throw new IllegalArgumentException("Radius must be larger than 10 Km.");
		}
	}
	
	private double x;
	private double y;
	
	// Each spaceship is located at a certain position (x,y) in an unbounded two-dimensional space.
	// Implement defensively
	private boolean isValidPosition(double x, double y) {
		if (Double.isNaN(x) || Double.isNaN(y)) {
			return false;
		} else {
			return true;
		}
	}
	
	public double getPositionX() {return this.x;}
	public double getPositionY() {return this.y;}
	
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
	
	//The speed of a ship shall never exceed the speed of light c, 300000km/s
	// Implement totally
	private boolean isValidVelocity(double x, double y) {
		double speed = Math.sqrt(Math.pow(this.getVelocityX(), 2)+Math.pow(this.getVelocityY(), 2));
		return 0 <= speed && speed <= MAX_SPEED;
	}
	
	public double getVelocityX() {return this.velocityX;}
	public double getVelocityY() {return this.velocityY;}
	
	// Implement totally
	public void setVelocity(double x, double y) {
		if (isValidVelocity(x, y)) {
			this.velocityX = x;
			this.velocityY = y;
		} else {
			// Reduce x and y until new speed below max speed
			// set velocity to those values
		}
	}
	
	public boolean isValidDuration (double duration) {
		return duration >= 0;
	}
	
	public Ship(double x, double y, double xVelocity, double yVelocity, double radius, double orientation) throws IllegalArgumentException {
		
		setPosition(x, y);
		setVelocity(xVelocity, yVelocity);
		try {
			setRadius(radius); // larger than 10 km
		} catch (IllegalArgumentException e) {
			throw e;
		} 
		setOrientation(orientation); //between 0 and 2pi
		
	};
	
	/**
	 * 
	 * @param duration
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
		double newMagnitude = Math.sqrt(Math.pow(newVelocityX, 2)+Math.pow(newVelocityY, 2));
		
		if (newMagnitude > MAX_SPEED) {
			// reduce Vx and Vy without changing direction until magnitude < c
			// The tangens of the enclosed angle should remain constant
			// tan(alfa) = Vy/Vx = V'y/V'x = constant
			// sqrt(V'x^2 + V'y^2) = c followed by a substitution of V'y = V'x*tan(alfa) 
			// gets you V'x = sqrt(c^2/1+tan(alfa)^2)
			
			double constantAngle = Math.atan(newVelocityY/newVelocityX);
			newVelocityX = Math.sqrt((Math.pow(MAX_SPEED,2))/(1+Math.pow(Math.tan(constantAngle), 2)));
			newVelocityY = newVelocityX * Math.tan(constantAngle) ; 
		}
		
		setVelocity(newVelocityX, newVelocityY);
				
	}
	
	public double getDistanceBetween(Ship other) throws Exception {
		if (other == this) {
			return new Double(0);
		} else {
			double distance = Math.sqrt(Math.pow((other.getPositionX()-this.getPositionX()), 2)+Math.pow((other.getPositionY()-this.getPositionY()), 2));
			return distance;
		}
	}

	public boolean overlap(Ship other) throws Exception {
		// see if the distance between the centers of the two circles is less then r1+r2. Then they will overlap in the plane. 
		// r1+r2 > sqrt( (x2-x1)**2 + (y2-y1)**2 ) 	
		double radiusSum = this.getRadius() + other.getRadius();
		double distance = getDistanceBetween(other);
		return radiusSum >= distance;

	}
	
public double getTimeToCollision(Ship other) throws Exception {
		
		// if two ships already overlap, the time to collision is set to zero
		// if deltaV * deltaR >= 0, time to collision is set to infinity
		// else if d <= 0, time to collision is set to infinity
		// else deltaT = - ((deltaV*deltaR+sqrt(d))/(deltaV+deltaV))
		
		Point2D deltaR = new Point2D.Double(this.getPositionX()-other.getPositionX(),this.getPositionY()-other.getPositionY());
		Point2D deltaV = new Point2D.Double(this.getVelocityX()-other.getVelocityX(),this.getVelocityY()-other.getVelocityY());
		
		double deltaRR = Math.pow(deltaR.getX(), 2) + Math.pow(deltaR.getY(), 2);
		double deltaVV = Math.pow(deltaV.getX(), 2) + Math.pow(deltaV.getY(), 2);
		double deltaVR = (deltaV.getX()*deltaR.getX())  + (deltaV.getY()*deltaR.getY());
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
	public double[] getCollisionPosition(Ship other) throws Exception {
		
		// The Collision position is the current position plus the time to collision multiplied by its velocity, with is no acceleration
		// In math: x(t) = x0 + Vx * getTimeToCollision
		//          y(t) = y0 + VY * getTimeToCollision          
		
		
		// implementatie is nog niet af!! Ik wil getTimeToCollision oproemen en dan vergelijken dat de tijd niet gelijk is aan infinity.
		// maar ! (not operator) werkt niet met een double... Dus anders oplossen.
		
		if (this.overlap(other)) {
			return null;
		} 
		else {
			double[] pos =  {this.getPositionX()+this.getVelocityX()*this.getTimeToCollision(other), this.getPositionY()+this.getVelocityY()*this.getTimeToCollision(other)};
			return pos;	
		}
		
		
	}
	
	
	

}
