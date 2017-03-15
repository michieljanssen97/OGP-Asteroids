package asteroids.model;

import java.util.HashSet;
import java.util.Set;

public class World {
	
	private static final double MAX_WIDTH = Double.MAX_VALUE;
	private static final double MAX_HEIGHT = Double.MAX_VALUE;
	
	public World(double width, double height) {
		setSize(width,height);
	}
	

	private boolean isTerminated;
	
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	public void terminate() {
		this.isTerminated = true;
	}

	
	private double width;
	private double height;
	
	public double getWidth(){
		return this.width;
	}
	public double getHeight(){
		return this.height;
	}
	
	private boolean isValidWidth(double width) {
		return (0 <= Math.abs(width) &&  Math.abs(width) <= MAX_WIDTH);
	}
	
	private boolean isValidHeight(double height) {
		return (0 <= Math.abs(height) &&  Math.abs(height) <= MAX_HEIGHT);
	}
	
	private void setSize(double width, double height){
		if (isValidWidth(width) && isValidHeight(height)) {
			this.width= Math.abs(width);
			this.height = Math.abs(height);
		} else if (isValidWidth(width)){
			this.width = Math.abs(width);
			this.height = Double.MAX_VALUE;
		} else {
			this.width = Double.MAX_VALUE;
			this.height = Math.abs(height);
		}
	}
	
	private Set<Ship> ships = new HashSet<Ship>();
	public Set<Ship> getShips(){
		return this.ships;
	}
	
	private Set<Bullet> bullets = new HashSet<Bullet>();
	public Set<Bullet> getBullets(){
		return this.bullets;
	}
	
	// implement defensively.
	
	public void addShip(World world, Ship ship) throws NullPointerException {
		if (world == null){
			throw new NullPointerException();
		} else {
			getShips().add(ship);
		}
		
	}
	public void removeShip(World world, Ship ship) throws NullPointerException {
		if (world == null){
			throw new NullPointerException();
		} else {
			getShips().remove(ship);
		}
	}
	
	public void addBullet(World world, Bullet bullet) throws NullPointerException {
		if (world == null){
			throw new NullPointerException();
		} else {
			getBullets().add(bullet);
		}
	}
	public void removeBullet(World world, Bullet bullet) throws NullPointerException {
		if (world == null){
			throw new NullPointerException();
		} else {
			getBullets().remove(bullet);
		}
	}
	
	public void evolve(double duration){}


}
