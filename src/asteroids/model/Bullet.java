package asteroids.model;

public class Bullet extends Entity {

	private double mass; 
	private World world; 
	private Ship ship; 
	private Ship source; 
	
	private boolean isTerminated;	

	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius) {}
	
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	public void terminate() {
		// To be completed.
		this.isTerminated = true;
	}
	
	public double getMass(){return this.mass;}
	
	public World getWorld(){return this.world;}
	
	public Ship getShip(){return this.ship;}
	
	public Ship getSource(){return this.source;}

}




