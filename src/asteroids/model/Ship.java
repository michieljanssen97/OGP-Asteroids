package asteroids.model;

import java.awt.geom.Point2D;

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
	public void setOrientation(double angle) {this.orientation = angle;}
	
	private double radius;
	public double getRadius() {return this.radius;}
	public void setRadius(double radius) {this.radius = radius;}
	
	private Point2D.Double position;
	public Point2D.Double getPosition() {return this.position;}
	private Point2D.Double velocity;
	public Point2D.Double getVelocity() {return this.velocity;}
	
	public boolean isValidDuration (double duration) {
		return duration >= 0;
	}
	
	public Ship(double x, double y, double xVelocity, double yVelocity, double radius, double orientation) {
		
		position.setLocation(x, y);
		velocity.setLocation(xVelocity, yVelocity);
		setRadius(radius); // larger than 10 km
		setOrientation(orientation); //between 0 and 2pi
		
	};
	
	/**
	 * 
	 * @param duration
	 */
	public void move(double duration) throws Exception {
		// Implement defensively
		if (isValidDuration(duration)) {
			double deltaX = velocity.getX()*duration;
			double deltaY = velocity.getY()*duration;
			position.setLocation(position.getX()+deltaX, position.getY()+deltaY);
		} else {
			throw new Exception();
		}
		
	}
	
	public boolean isValidAngle(double angle) {
		return true;
	}
	
	public void turn(double angle) {
		// Implement nominally
		setOrientation(this.orientation+angle % 2*Math.PI);
	}
	public void thrust(double amount) {
		// Implement totally
		if (amount < 0) {
			amount = 0;
		}
		double newVelocityX = velocity.getX() + amount*Math.cos(getOrientation());
		double newVelocityY = velocity.getY() + amount*Math.sin(getOrientation());
		double newMagnitude = Math.sqrt(Math.pow(newVelocityX, 2)+Math.pow(newVelocityY, 2));
		
		if (newMagnitude > MAX_SPEED) {
			// reduce Vx and Vy without changing direction until magnitude < c
		}
				
		velocity.setLocation(newVelocityX, newVelocityY);
		
	}
	
	public double getDistanceBetween(Ship other) throws Exception {
		if (other == this) {
			return new Double(0);
		} else {
			double distance = Math.sqrt(Math.pow((other.position.getX()-this.position.getX()), 2)+Math.pow((other.position.getY()-this.position.getY()), 2));
			return distance;
		}
	}

	public boolean overlap(Ship other) throws Exception {
		// see if the distance between the centers of the two circles exceeds r1+r2. Then they cannot overlap in the plane. 
		// r1+r2 < sqrt( (x2-x1)**2 + (y2-y1)**2 ) 	
		double radiusSum = this.getRadius() + other.getRadius();
		double distance = Math.sqrt(Math.pow((other.position.getX()-this.position.getX()), 2)+Math.pow((other.position.getY()-this.position.getY()), 2));
		return radiusSum < distance;

	}
	public double getTimeToCollision(Ship other) throws Exception {return null;}
	public Point2D.Double getCollisionPosition(Ship other) throws Exception {return null;}
	
	
	

}
