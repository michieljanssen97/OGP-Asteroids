package asteroids.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import asteroids.part2.CollisionListener;

/**
 * 
 * A class that defines a world for the Asteroids game.
 *
 * @author Michiel Janssen & Jelle Pelgrims
 *
 */

public class World implements ICollidable {
	
	private static final double MAX_WIDTH = Double.MAX_VALUE;
	private static final double MAX_HEIGHT = Double.MAX_VALUE;
	
	private static final double DEFAULT_WIDTH = 1000;
	private static final double DEFAULT_HEIGHT = 1000;
	
	private Set<Entity> entities = new HashSet<Entity>();
	
	private boolean isTerminated;
	
	private double width;
	private double height;
	
	/**
	 * Instantiate a new world with a given width and height
	 * 
	 * @param width
	 * @param height
	 * @post The width of the new world is equal to the given width
	 * 	     | new.getWidth() == width
	 * @post The height of the new world is equal to the given height
	 * 		 | new.getheight() == height
	 */
	public World(double width, double height) {
		setSize(width,height);
	}
	
	/**
	 * Returns a boolean indicating whether this particular world is terminated
	 * @see implementation
	 */
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * Terminates this world
	 * @post ...
	 * 		 | new.isTerminated() == true
	 */
	public void terminate() {
		getEntities().forEach(entity->entity.removeFromWorld());
		entities.clear();
		this.isTerminated = true;
	}
	
	/**
	 * Returns the width of this world
	 * @see implementation
	 */
	public double getWidth(){
		return this.width;
	}
	
	/**
	 * Returns the height of this world
	 * @see implementation
	 */
	public double getHeight(){
		return this.height;
	}
	
	/**
	 * Returns the maximum width of this world
	 * @see implementation
	 */
	public double getMaxWidth(){return MAX_WIDTH;}
	
	/**
	 * Returns the maximum height of this world
	 * @see implementation
	 */
	public double getMaxHeight(){return MAX_HEIGHT;}
	
	/**
	 * Returns a boolean indicating whether the given width is valid
	 * 
	 * @param width
	 * @invar The given width should be larger than or equal to zero
	 * 		  | 0 <= width
	 * @invar The given width should be smaller than or equal to the maximum width of this world
	 * 		  | width <= getMaxWidth()
	 * @return ...
	 * 		  | (0 <= width &&  width <= getMaxWidth())
	 * 		   
	 */
	private boolean isValidWidth(double width) {
		return (0 <= width &&  width <= getMaxWidth());
	}
	
	/**
	 * Returns a boolean indicating whether the given width is valid
	 * 
	 * @param height
	 * @invar The given height should be larger than or equal to zero
	 * 		  | 0 <= height
	 * @invar The given height should be smaller than or equal to the maximum height of this world
	 * 		  | width <= getMaxHeight()
	 * @return ...
	 * 		  | (0 <= height && height <= getMaxHeight())
	 * 		   
	 */
	private boolean isValidHeight(double height) {
		return (0 <= height &&  height <= getMaxHeight());
	}
	
	/**
	 * Sets this worlds dimensions to the given dimensions
	 * 
	 * @param width
	 * @param height
	 * @post The width of this world is equal to the given width given that it is valid, 
	 * 		 otherwise the width will be set to the default width
	 * 		 | if (isValidWidth(width))
	 * 		 | 	   new.getWidth() == width
	 * 		 | else
	 * 		 |     new.getWidth() == DEFAULT_WIDTH
	 * @post The height of this world is equal to the given height given that it is valid, 
	 * 		 otherwise the height will be set to the default height
	 * 		 | if (isValidHeight(height))
	 * 		 | 	   new.getHeight() == height
	 * 		 | else
	 * 		 |     new.getHeight() == DEFAULT_HEIGHT
	 */
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
	
	/**
	 * Returns the entities in this world
	 * @return All ships and bullets in the world are returned
	 *         | this.bullets + this.ships
	 */
	public Set<Entity> getEntities() {
		return this.entities;
	}
	
	public <T> Set<T> getEntities(Class<T> type) {
		Set<T> class_entities = new HashSet<T>();
		getEntities().stream()
                .filter(entity -> type.isInstance(entity))
                .forEach(entity -> class_entities.add(type.cast(entity)));
		return class_entities;
	}
	
	/**
	 * Adds an entity to this world
	 * 
	 * @param entity
	 * @post Associate the given entity with this world, if possible
	 * 		 | if entity.canBePartOfWorld()
	 * 		 |     entity.isPartOfWorld(this) == true
	 * @post Add the entity to the world
	 * 		 | if entity.canBePartOfWorld()
	 * 		 | 	   if (entity instanceof Ship)
	 * 	     |			this.ships.contains((Ship) entity) == true
	 * 		 |		else if (entity instanceof Bullet)
	 * 		 |          this.bullets.contains((Bullet) entity) == true
	 * @throws IllegalArgumentException
	 * 		   The given entity is null
	 * 		   | entity == null
	 * @throws NullPointerException
	 * 		   The given entity is already a part of this world, 
	 * 		   the given entity overlaps with another entity in this world
	 *         or the given entity does not completely lie within the boundaries of this world
	 * 		   | (entity.isPartOfWorld() || significantOverlap(entity) || !entity.withinBoundaries(this))
	 */
	public void addEntity(Entity entity) throws IllegalArgumentException, NullPointerException {
		if (entity == null){
			throw new NullPointerException();
		} else if (entity.isPartOfWorld()){
			throw new IllegalArgumentException("Entity is already part of world");
		} else if (significantOverlap(entity)) {
			throw new IllegalArgumentException("Entity overlaps with another entity");
		} else if (!entity.withinBoundaries(this)) {
			throw new IllegalArgumentException("Entity does not lie within the world's boundaries");
		} else {
			if (entity.canBePartOfWorld()) {
				entity.makePartOfWorld(this);
				entities.add(entity);
			}
		}
	}
	
	/**
	 * Removes an entity from this world
	 * 
	 * @param entity
	 * @post The entity is not associated with this world any longer
	 * 		 | entity.isPartOfWorld(this) == false
	 * @post The entity does not exist in this world any longer
	 * 		 | if (entity instanceof Ship)
	 * 	     |		this.ships.contains((Ship) entity) == false
	 * 		 |	else if (entity instanceof Bullet)
	 * 		 |      this.bullets.contains((Bullet) entity) == false
	 * @throws NullPointerException
	 * 		   The given entity is null
	 * 		   | entity == null
	 */
	public void removeEntity(Entity entity) throws NullPointerException {
		if (entity == null){
			throw new NullPointerException();
		} else {
			entity.removeFromWorld();
			entities.remove(entity);
		}
	}
	
	/**
	 * 
	 * A function that returns the next collision time
	 * 
	 * @post This function executes in such a manner that ensures that, at the end of the function:
	 *  		* The time to collision of the first collision is returned
	 *  		* If no collision will occur null is returned
	 */
	public Double getNextCollisionTime() {
		Double collisionTime = null;
		ICollidable[] collidables = getNextCollisionObjects();
		if (collidables != null) {
			collisionTime = collidables[0].getTimeToCollision(collidables[1]);
		}
		return collisionTime;

	}

	/**
	 * A function that returns the next collision position
	 * 
	 * 
	 * @post This function executes in such a manner that ensures that, at the end of the function:
	 *  		* The position of the first collision is returned
	 *  		* If no collision will occur null is returned
	 */
	public double[] getNextCollisionPosition() {
		double[] collisionPosition = null;
		ICollidable[] collidables = getNextCollisionObjects();
		if (collidables != null) {
			collisionPosition = collidables[0].getCollisionPosition(collidables[1]);
		}
		return collisionPosition;
	}
	
	/**
	 * Returns the two objects that will collide first, null if no collisions
	 * 
	 * @post This function executes in such a manner that ensures that, at the end of the function:
	 *  		* The two collidables that will collide first are returned
	 *  		* If no collision will occur null is returned
	 * 
	 * @return An ICollidable array containing the two colliding objects
	 */
	public ICollidable[] getNextCollisionObjects() {

		Map<ICollidable[], Double> collisionMap = new HashMap<ICollidable[], Double>();
		Double collisionTime;

		for (Entity entity1 : this.getEntities()) {
			// Calculate time of collision with other entities
			for (Entity entity2: this.getEntities()) {
				
				if (entity1 == entity2){
					continue;
				} else {
					collisionTime = entity1.getTimeToCollision(entity2);
					
					if (collisionTime == Double.POSITIVE_INFINITY || collisionTime == null){continue;}
				    
				    ICollidable[] collisionArray = { entity1, entity2 };
			    	collisionMap.put(collisionArray, collisionTime);
				}
			}
			
			// Calculate time of collision with world
			collisionTime = entity1.getTimeToCollision(this);
			ICollidable[] collisionArray = { entity1, this };
	    	collisionMap.put(collisionArray, collisionTime);
			
		}
		
		if (collisionMap.isEmpty()) {
			return null;
		} else {
			// Get collision from collisionMap with lowest collisionTime
			ICollidable[] firstCollisionArray = Collections.min(collisionMap.entrySet(), Map.Entry.comparingByValue()).getKey();
			return firstCollisionArray;
		}
		
	}
	
	public void showCollision(CollisionListener collisionListener, ICollidable[] collidables, double[] colPos) {
		if (collisionListener == null) {return;}
		else if (collidables[0] instanceof Entity && collidables[1] instanceof Entity){
			if (((Entity)collidables[0]).isDestroyed() == true && ((Entity)collidables[1]).isDestroyed() == true) {
				double [] result = ((Entity)collidables[0]).explosionPosition((Entity)collidables[1]);
				collisionListener.objectCollision((Entity)collidables[0],(Entity)collidables[1],result[0], result[1]);
			}
		} else {
			collisionListener.boundaryCollision(collidables[0], colPos[0], colPos[1]);	
		}
	}
	
	/**
	 * A function for advancing the game. No specification should be worked out according to the task explanation.
	 */
	public void evolve(double duration, CollisionListener collisionlistener) {		
		while (duration > 0 && !entities.isEmpty()) {
			// 1. Get first collision, if any
			// Calculate all collisions, immediately continue if an apparent Collision is found
			
			ICollidable[] collidables = getNextCollisionObjects();
			Double firstCollisionTime = collidables[0].getTimeToCollision(collidables[1]);

			if (firstCollisionTime == 0) {
				advanceEntities(duration);
			} else if (firstCollisionTime == null || firstCollisionTime > duration) {
				advanceEntities(duration);
			} else {
				advanceEntities(firstCollisionTime);
				double [] colPos = collidables[0].getCollisionPosition(collidables[1]);
				
				collidables[0].collide(collidables[1]);
				showCollision(collisionlistener, collidables, colPos);

			}
			terminateEntities();
			duration -= firstCollisionTime;
		} 
	}
	
	/**
	 * Advances all entities in this world
	 * 
	 * @param duration
	 * @post This function executes in such a manner that ensures that, at the end of the function:
	 * 		 	* All entities have moved for the given duration
	 */
	public void advanceEntities(double duration) {
		getEntities().stream().forEach(entity -> entity.move(duration));
		terminateEntities();
	}
	
	public void terminateEntities() {
		Set<Entity> destroyedEntities = getEntities().stream()
				.filter(entity -> entity.isDestroyed())
				.collect(Collectors.toSet());

		destroyedEntities.stream().forEach(entity -> entity.terminate());
	}
	/**
	 * Returns the entity at the given position
	 * 
	 * @param x
	 * @param y
	 * @return The entity at the given position, or null if there is none
	 */
	public Entity getEntityAtPosition(double x, double y) {
		return getEntities().stream()
			.filter(entity -> entity.getPositionX() == x && entity.getPositionY() == y)
			.findFirst()
			.orElse(null);
	}
	
	/**
	 * Returns a boolean indicating whether there is significant overlap between this entity and other entities
	 * 
	 * @param entity
	 * @return this functions returns true if and only if the entity significantly overlaps with 
	 * 		   at least one other entity in this world and that entity is not equal to the given entity
	 */
	public boolean significantOverlap(Entity entity) {
		return getEntities().stream()
				.filter(other -> entity.significantOverlap(other) && (entity != other))
				.findFirst()
				.isPresent();
	}
	

	/**
	 * A function that returns the position of a collision between this world and a collidable
	 * 
	 * @post This function executes in such a manner that ensures that, at the end of the function:
	 * 			* If the collidable is not a world, collidable.getCollisionPosition(this) is returned
	 * 			* If the collidable is a world, 0.0 is returned
	 */
	@Override
	public double[] getCollisionPosition(Entity entity) {
		return entity.getCollisionPosition(this);
	}

	@Override
	public double[] getCollisionPosition(World world) {
		return null;
	}

	/**
	 * A function that returns the time to collision between this world and a collidable
	 * 
	 * @post This function executes in such a manner that ensures that, at the end of the function:
	 * 			* If the collidable is not a world, collidable.getTimeToCollision(this) is returned
	 * 			* If the collidable is a world, 0.0 is returned
	 */
	@Override
	public double getTimeToCollision(World world) {
		return 0.0;
	}
	
	@Override
	public double getTimeToCollision(Entity entity) {
		return entity.getTimeToCollision(this);
	}
	
	/**
	 * A function that resolves a collision event between an entity and a boundary of a world
	 * 
	 * @param entity
	 * @param this
	 * @post This function executes in such a manner that ensures that, at the end of the function:
	 *			* In the case that the entity is a ship or bullet, its velocity in the direction of the collision is reversed
	 *			* In the case that the entity is a bullet that has already collided with a boundary two times, the entity is
	 *			  removed from the world
	 */
	
	public void collide(Entity entity) {
		if (entity instanceof Bullet) {entity.collide(this);} 
		else {defaultCollide(entity);}
	}
	
	public void defaultCollide(Entity entity) {
		double distanceToLeftWall = entity.getPositionX()-entity.getRadius();
		double distanceToRightWall = getWidth() - entity.getPositionX()- entity.getRadius();
		double distanceToUpperWall = getHeight() - entity.getPositionY()- entity.getRadius();
		double distanceToBottomWall = entity.getPositionY() - entity.getRadius();
		
		double minDistance = Math.min(Math.min(distanceToUpperWall, distanceToBottomWall), Math.min(distanceToLeftWall, distanceToRightWall));
		if (minDistance == distanceToLeftWall || minDistance == distanceToRightWall) {
			entity.setVelocity(-entity.getVelocityX(), entity.getVelocityY());
		} else if (minDistance == distanceToUpperWall || minDistance == distanceToBottomWall) {
			entity.setVelocity(entity.getVelocityX(), -entity.getVelocityY());
		}
	}

	@Override
	public void collide(World world) {
		// Shouldn't happen
	}

	@Override
	public boolean isDestroyed() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
