package asteroids.model;
import java.awt.Point;

// Point implementatie in doubles of apart doubles x en y

public class Ship {
	
	/* General comments:
	 * 		- All aspects related to the position of the ship shall be worked out defensively
	 * 		- All aspects related to velocity must be worked out in a total manner.
	 * 		- All aspects related to the orientation of the ship must be worked out nominally
	 * 		- All aspects related to the radius must be worked out defensively.
	 * 
	 */
	
	public Ship(double x, double y, double xVelocity, double yVelocity, double radius, double orientation) {
		
	};
	
	private static final double MAX_SPEED = 300000;
	
	private Point position;
	public Point getPosition(){return this.position;}
	private void setPosition(Point position) {this.position = position;}
	
	private Point velocity;
	public Point getVelocity(){return this.velocity;}
	private void setVelocity(Point velocity) {this.velocity = velocity;}
	
	public void move(double duration) {}
	public void turn(double angle) {}
	public void thrust(double thrust) {}
	
	public double getDistanceBetween(Ship other) {return null;}
	public boolean overlap(Ship other) {return false;}
	public double getTimeToCollision(Ship other) {return null;}
	public Point getCollisionPosition(Ship other) {return null;}
	
	
	

}


