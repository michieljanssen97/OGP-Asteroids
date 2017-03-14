package asteroids.model;

public class World {
	
	public World(double width, double height) {}
	
	
	private boolean isTerminated;
	
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	public void terminate() {
		// To be completed.
		this.isTerminated = true;
	}

	
	private double size;
	public double getSize(){return this.size;}
	
	private double ships;
	public double getShips(){return this.ships;}
	
	private double bullets;
	public double getBullets(){return this.bullets;}
	
	public void addShip(World world, Ship ship) {}
	public void removeShip(World world, Ship ship) {}
	
	public void addBullet(World world, Bullet bullet) {}
	public void removeBullet(World world, Bullet bullet) {}


}
