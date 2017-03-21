package asteroids.model;

public class Bullet extends Entity {

	protected static final double MAX_SPEED = 300000;
	protected static final double MIN_RADIUS = 1;
	
	private double mass; 
	private World world; 
	private Ship ship; 
	private Ship source; 	

	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius) {
		setPosition(x, y);
		setVelocity(xVelocity, yVelocity);
		setRadius(radius);
	}
	
	public double getMass(){return this.mass;}
	
	public World getWorld(){return this.world;}
	
	public Ship getShip(){return this.ship;}
	
	public Ship getSource(){return this.source;}
	
	public void move() {
		
	}

}




