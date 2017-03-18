package asteroids.model;

import java.util.HashSet;
import java.util.Set;

import asteroids.model.entity.Entity;
import asteroids.model.entity.IEntity;

public class World {
	
	private static final double MAX_WIDTH = Double.MAX_VALUE;
	private static final double MAX_HEIGHT = Double.MAX_VALUE;
	
	private Set<Ship> ships = new HashSet<Ship>();
	private Set<Bullet> bullets = new HashSet<Bullet>();
	
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
	
	
	public Set<Ship> getShips(){
		return this.ships;
	}
	
	public Set<Bullet> getBullets(){
		return this.bullets;
	}
	
	// implement defensively.
	
	public void addEntity(Entity entity) throws NullPointerException {
		if (entity == null){
			throw new NullPointerException();
		} else if (entity.isPartOfWorld() || this.significantOverlap(entity)){
			throw new IllegalArgumentException();
		} else {
			if (entity instanceof Ship) {
			ships.add((Ship) entity);
			} else if (entity instanceof Bullet) {
			bullets.add((Bullet) entity);
			}
		}
		
	}
	public void removeEntity(Entity entity) throws NullPointerException {
		if (entity == null){
			throw new NullPointerException();
		} else {
			if (entity instanceof Ship) {
			ships.remove(entity);
			} else if (entity instanceof Bullet) {
			bullets.remove(entity);
			}
		}
	}
	
	public void evolve(double duration){}
	
	public IEntity getEntityAtPosition(double x, double y) {
		Entity temp = null;
		return temp;
	}
	
	private boolean significantOverlap(Entity entity) {
		return false;
	}
	
	private boolean withinOverlap(Entity object, Entity container) {
		return false;
	}


}
