package asteroids.model;

public class Bullet extends Entity {

	private double mass; 
	private World world; 
	private Ship ship; 
	private Ship source; 	

	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius) {}
	
	public double getMass(){return this.mass;}
	
	public World getWorld(){return this.world;}
	
	public Ship getShip(){return this.ship;}
	
	public Ship getSource(){return this.source;}

}




