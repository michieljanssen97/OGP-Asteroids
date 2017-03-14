package asteroids.model;


public class Bullet {
	
	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius) {}
	
	private boolean isTerminated;

	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	public void terminate() {
		// To be completed.
		this.isTerminated = true;
	}
	
	private double orientation;
	
	public double getOrientation() {
		return this.orientation;
	}
	public void setOrientation(double angle) {
		
	}
	
	private double radius;

	public double getRadius() {
		return this.radius;
	}
	
	public void setRadius(double radius) throws IllegalArgumentException {}
	
	private double x;
	private double y;
	
	public double getPositionX() {return this.x;}
	public double getPositionY() {return this.y;}
	
	public void setPosition(double x, double y) throws IllegalArgumentException{}
	
	private double velocityX;
	private double velocityY;

	public double getVelocityX() {
		return this.velocityX;
	}
	public double getVelocityY() {
		return this.velocityY;
	}
	
	public void setVelocity(double x, double y) {}
	
	private double mass; 
	
	public double getMass(){return this.mass;}
	
	private double world; 
	public double getWorld(){return this.world;}
	
	private double ship; 
	public double getShip(){return this.ship;}
	
	private double source; 
	public double getSource(){return this.source;}
}




