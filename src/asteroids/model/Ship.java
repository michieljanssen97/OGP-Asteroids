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
		
		velocity.setLocation(newVelocityX, newVelocityY);
		
		if (newMagnitude > MAX_SPEED) {
			// reduce Vx and Vy without changing direction until magnitude < c
			// The tangens of the enclosed angle should remain constant
			// tan(alfa) = Vy/Vx = V'y/V'x = constant
			// sqrt(V'x^2 + V'y^2) = c followed by a substitution of V'y = V'x*tan(alfa) 
			// gets you V'x = sqrt(c^2/1+tan(alfa)^2)
			
			
			double constantAngle = Math.atan(newVelocityY/newVelocityX);
			double new2VelocityX = Math.sqrt((Math.pow(MAX_SPEED,2))/(1+Math.pow(Math.tan(constantAngle), 2)));
			double new2VelocityY = new2VelocityX * Math.tan(constantAngle) ; 
			
			velocity.setLocation(new2VelocityX, new2VelocityY);
		}
				
		
		
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
		// see if the distance between the centers of the two circles is less then r1+r2. Then they will overlap in the plane. 
		// r1+r2 > sqrt( (x2-x1)**2 + (y2-y1)**2 ) 	
		double radiusSum = this.getRadius() + other.getRadius();
		double distance = getDistanceBetween(other);
		return radiusSum >= distance;

	}
	public double getTimeToCollision(Ship other) throws Exception {
		
		// if two ships already overla, the time to collision is set to zero
		// if deltaV * deltaR >= 0, time to collision is set to infinity
		// else if d <= 0, time to collision is set to infinity
		// else deltaT = - ((deltaV*deltaR+sqrt(d))/(deltaV+deltaV))
		
		Point2D deltaV = new Point2D.Double(this.velocity.getX()-other.velocity.getX(),this.velocity.getY()-other.velocity.getY());
		
		double deltaVx = this.velocity.getX()-other.velocity.getX();
		double deltaVy = this.velocity.getY()-other.velocity.getY();
		double deltaRx = this.position.getX()-other.position.getX(); 
		double deltaRy = this.position.getY()-other.position.getY();
		
		double d = ();
		
		(Math.pow(deltaVx, 2)+Math.pow(deltaRy, 2))
	
		if (this.overlap(other)) {
			return new Double(0);
		} else {
			if ((deltaVx*deltaRx)+(deltaVy*deltaRy >= 0)){
				return Double.POSITIVE_INFINITY;
			} else if 
		}

	}
	public Point2D.Double getCollisionPosition(Ship other) throws Exception {return null;}
	
	
	

}
