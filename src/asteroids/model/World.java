package asteroids.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class World {
	
	private static final double MAX_WIDTH = Double.MAX_VALUE;
	private static final double MAX_HEIGHT = Double.MAX_VALUE;
	
	private static final double DEFAULT_WIDTH = 1000;
	private static final double DEFAULT_HEIGHT = 1000;
	
	private Set<Ship> ships = new HashSet<Ship>();
	private Set<Bullet> bullets = new HashSet<Bullet>();
	
	private boolean isTerminated;
	
	private double width;
	private double height;
	
	public World(double width, double height) {
		setSize(width,height);
	}
	
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	public void terminate() {
		this.isTerminated = true;
	}

	public double getWidth(){
		return this.width;
	}
	public double getHeight(){
		return this.height;
	}
	
	private boolean isValidWidth(double width) {
		return (0 <= width &&  width <= MAX_WIDTH);
	}
	
	private boolean isValidHeight(double height) {
		return (0 <= height &&  height <= MAX_HEIGHT);
	}
	
	private void setSize(double width, double height){
		
		if (isValidWidth(width)) {
			this.width = width;
		} else {
			this.width = DEFAULT_WIDTH;
		}
		
		if (isValidHeight(height)) {
			this.height = height;
		} else {
			this.height = DEFAULT_HEIGHT;
		}
	}
	
	
	public Set<Ship> getShips(){
		return this.ships;
	}
	
	public Set<Bullet> getBullets(){
		return this.bullets;
	}
	
	public Set<Entity> getEntities(){
		Set<Entity> entities = new HashSet<Entity>();
		entities.addAll(this.bullets);
		entities.addAll(this.ships);
		return entities;
	}
	
	// implement defensively.
	
	public void addEntity(Entity entity) throws NullPointerException {
		if (entity == null){
			throw new NullPointerException();
		} else if (entity.isPartOfWorld() || significantOverlap(entity) || !entity.withinBoundaries(this)){
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
	
	public void evolve(double duration){
		
		while (duration > 0) {
		
			// 1. Get first collision, if any
			// Calculate all collisions, immediately continue if an apparent Collision is found
			
			Map<Double, Set<Entity>> map = new HashMap<Double, Set<Entity>>();
			boolean collision = false;
			Double collisionTime;
			
			for (Entity entity1 : this.getEntities()) {
				for (Entity entity2: this.getEntities()) {
					
					// Apparent collision
				    if (entity1.apparentlyCollide(entity2) && (entity1 != entity2)) {
				    	collisionTime = (double) 0;
				    	collision = true;
				    } else {
				    	collisionTime = entity1.getTimeToCollision(entity2);
				    	if (collisionTime != null) {
				    		collision = true;
				    	}
				    }
				    
				    // Collision found, add to map of collisions
				    if (collision) {
			    		Set<Entity> collisionSet = new HashSet<Entity>();
				    	collisionSet.add(entity1);
				    	collisionSet.add(entity2);
			    		map.put(collisionTime, collisionSet);
			    		collision = false;
				    }
				}
				
				if (map.containsKey((double) 0)) {
					// First collision found, break
					break;
				}
			} 
			
			// Get collision from map with lowest collisionTime/Key
		}
	}
	
	public IEntity getEntityAtPosition(double x, double y) {
		for (Entity entity : this.getEntities()) {
		    if ((entity.getPositionX() == x) && (entity.getPositionY() == y)) {
		    	return entity;
		    }
		} 
		return null;
	}
	
	private boolean significantOverlap(Entity entity) {
		
		for (Entity other : this.getEntities()) {
		    if (entity.significantOverlap(other) && (entity != other)) {
		    	return true;
		    }
		} 
		return false;
	}


}
