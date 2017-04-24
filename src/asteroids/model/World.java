package asteroids.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
		this.isTerminated = true;
	}

	/**
	 * Destroys a world.
	 */
	public void destroyWorld(){
		entities.clear();
		this.terminate();
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
		for (Entity entity : this.getEntities()) {
			if (type.isInstance(entity)) {
				class_entities.add(type.cast(entity));
			}
		}
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
	 * A function that returns the next collision time
	 * 
	 * @post This function executes in such a manner that ensures that, at the end of the function:
	 *  		* The time to collision of the first collision is returned
	 *  		* If no collision will occur null is returned
	 */
	public Double getNextCollisionTime() {
		Double collisionTime = null;
		try {
			ICollidable[] collidables = getNextCollisionObjects();
			collisionTime = collidables[0].getTimeToCollision(collidables[1]);
		} catch (Exception e){
			// No harm done
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
		try {
			ICollidable[] collidables = getNextCollisionObjects();
			collisionPosition = collidables[0].getCollisionPosition(collidables[1]);
		} catch (Exception e) {
			// No harm done
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
				try {
					collisionTime = entity1.getTimeToCollision(entity2);
				} catch (Exception e) {
					collisionTime = Double.POSITIVE_INFINITY;
				}
			    if (collisionTime == Double.POSITIVE_INFINITY || collisionTime == null){continue;}
			    
			    ICollidable[] collisionArray = { entity1, entity2 };
		    	collisionMap.put(collisionArray, collisionTime);
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
	
	
	/**
	 * A function for advancing the game. No specification should be worked out according to the task explanation.
	 */
	public void evolve(double duration, CollisionListener collisionlistener) throws Exception {
		while (duration > 0) {
		
			// 1. Get first collision, if any
			// Calculate all collisions, immediately continue if an apparent Collision is found
			
			Double firstCollisionTime;
			ICollidable[] collidables = null;
			try {
				firstCollisionTime = getNextCollisionTime();
				collidables = getNextCollisionObjects();
			} catch (Exception e) {
				firstCollisionTime = null;
			}
			
			if (firstCollisionTime > duration) {
				advanceEntities(duration);
				return;
			} else {
				advanceEntities(firstCollisionTime);
				if (collidables[0] instanceof Entity && collidables[1] instanceof Entity) {
					//((Entity)collidables[0]).objectCollide((Entity)collidables[1]);
					//((Entity)collidables[0]).objectCollide((Entity)collidables[1], (Entity)collidables[0]);
//					System.out.println("First:" + collidables[0]);
//					System.out.println("Second:" + collidables[1]);
					collidables[0].collide(collidables[1]);
					double[] result = collidables[0].getCollisionPosition(collidables[1]);
					collisionlistener.objectCollision(collidables[0],collidables[1], result[0],result[1]);
					
				} else if (collidables[0] instanceof World && collidables[1] instanceof Entity) {
					//((Entity)collidables[1]).boundaryCollide((World)collidables[0]);
					//boundaryCollide((Entity)collidables[1], (World)collidables[0]);
					//((Entity)collidables[1]).boundaryCollide((Entity)collidables[1], (World)collidables[0]);
					collidables[1].collide(collidables[0]);
					double[] result = collidables[1].getCollisionPosition(collidables[0]);
					collisionlistener.boundaryCollision(collidables[1], result[0],result[1]);
					
				} else if (collidables[0] instanceof Entity && collidables[1] instanceof World) {
					//((Entity)collidables[0]).boundaryCollide((World)collidables[1]);
					//boundaryCollide((Entity)collidables[0], (World)collidables[1]);
					//((Entity)collidables[0]).boundaryCollide((Entity)collidables[0], (World)collidables[1]);
					collidables[0].collide(collidables[1]);
					double[] result = collidables[0].getCollisionPosition(collidables[1]);
					collisionlistener.boundaryCollision(collidables[0], result[0],result[1]);
					
				}
			
				//  Subtract firstTimeCollision from delta t and go to step 1.
				duration -= firstCollisionTime;
			}
		} 
	}
	
//	private void boundaryCollide(Entity entity1, World world) {
//
//		double distanceToLeftWall = entity1.getPositionX()-entity1.getRadius();
//		double distanceToRightWall = world.getWidth() - entity1.getPositionX()-entity1.getRadius();
//		double distanceToUpperWall = world.getHeight() - entity1.getPositionY()-entity1.getRadius();
//		double distanceToBottomWall = entity1.getPositionY()-entity1.getRadius();
//		
//		double minDistance = Math.min(Math.min(distanceToUpperWall, distanceToBottomWall), Math.min(distanceToLeftWall, distanceToRightWall));
//		if (minDistance == distanceToLeftWall || minDistance == distanceToRightWall) {
//			entity1.setVelocity(-entity1.getVelocityX(), entity1.getVelocityY());
//		} else if (minDistance == distanceToUpperWall || minDistance == distanceToBottomWall) {
//			entity1.setVelocity(entity1.getVelocityX(), -entity1.getVelocityY());
//		}
//		
//		if (entity1 instanceof Bullet){
//			if (((Bullet) entity1).Counter() == true){
//				world.removeEntity(((Bullet) entity1));
//			}
//		}
//	}
//	
//	private void objectCollide(Entity entity1, Entity entity2) {
//		if ((entity1 instanceof Ship && entity2 instanceof Ship) || (entity1 instanceof MinorPlanet && entity2 instanceof MinorPlanet) ) {
//			
//			double deltaPosX = entity2.getPositionX()-entity1.getPositionX();
//			double deltaPosY = entity2.getPositionY()-entity1.getPositionY();
//
//			double deltaVelX = entity2.getVelocityX()-entity1.getVelocityX();
//			double deltaVelY = entity2.getVelocityY()-entity1.getVelocityY();
//			
//			double deltaVR = (deltaVelX*deltaPosX)  + (deltaVelY*deltaPosY);
//			
//			double radiusSum = entity1.getRadius() + entity2.getRadius();
//			double J = (2*entity1.getMass()*entity2.getMass()*deltaVR)/((entity1.getMass()+entity2.getMass())*radiusSum);
//			
//			double Jx = (J*deltaPosX)/(radiusSum);	
//			double Jy = (J*deltaPosY)/(radiusSum);
//			
//			double newVelocityX1 = entity1.getVelocityX() + (Jx/entity1.getMass());
//			double newVelocityY1 = entity1.getVelocityY() + (Jy/entity1.getMass());
//			
//			double newVelocityX2 = entity2.getVelocityX() - (Jx/entity2.getMass());
//			double newVelocityY2 = entity2.getVelocityY() - (Jy/entity2.getMass());
//			
//			entity1.setVelocity(newVelocityX1, newVelocityY1);
//			entity2.setVelocity(newVelocityX2, newVelocityY2);
//			
//		} else if ((entity1 instanceof Entity && entity2 instanceof Bullet)) {
//			
//			if (((Bullet) entity2).getSource() == entity1) {
//				((Bullet) entity2).setCounter(0);
//				entity2.removeFromWorld();
//				entity2.setPosition(entity1.getPositionX(), entity1.getPositionY());
//				((Ship) entity1).loadBullets((Bullet) entity2);
//				this.removeEntity(entity2);
//			} else {
//				this.removeEntity(entity1);
//				this.removeEntity(entity2);
//				entity1.terminate();
//				entity2.terminate();
//		  } 
//		} else if ((entity2 instanceof Entity && entity1 instanceof Bullet)) {
//			
//			if (((Bullet) entity1).getSource() == entity2) {
//				((Bullet) entity1).setCounter(0);
//				entity1.removeFromWorld();
//				entity1.setPosition(entity2.getPositionX(), entity2.getPositionY());
//				((Ship) entity2).loadBullets((Bullet) entity1);
//				this.removeEntity(entity1);
//			} else {
//				this.removeEntity(entity2);
//				this.removeEntity(entity1);
//				entity1.terminate();
//				entity2.terminate();
//			}
//		} else if (entity2 instanceof Ship && entity1 instanceof Asteroid) {
//			this.removeEntity(entity2);
//			
//		} else if (entity1 instanceof Ship && entity2 instanceof Asteroid) {
//			this.removeEntity(entity1);
//			
//		} else if (entity1 instanceof Ship && entity2 instanceof Planetoid) {
//			double[] randomPosition = {(Math.random())*(this.getWidth()-entity1.getRadius()),(Math.random())*(this.getHeight()-entity1.getRadius())};
//			entity1.setPosition(randomPosition[0], randomPosition[1]);
//			if (this.significantOverlap(entity1))
//				this.removeEntity(entity1);
//		} else if (entity2 instanceof Ship && entity1 instanceof Planetoid) {
//			double[] randomPosition = {(Math.random())*(this.getWidth()-entity2.getRadius()),(Math.random())*(this.getHeight()-entity2.getRadius())};
//			entity2.setPosition(randomPosition[0], randomPosition[1]);
//			if (this.significantOverlap(entity2))
//				this.removeEntity(entity2);
//			
//		}
//	}
	/**
	 * Advances all entities in this world
	 * 
	 * @param duration
	 * @post This function executes in such a manner that ensures that, at the end of the function:
	 * 		 	* All entities have moved for the given duration
	 */
	public void advanceEntities(double duration) {
		//System.out.println("entities in world: "+		getEntities().toString().toString());
		for (Entity entity : this.getEntities()) {
			entity.move(duration);
		}
	}
	
	/**
	 * Returns the entity at the given position
	 * 
	 * @param x
	 * @param y
	 * @return The entity at the given position, or null if there is none
	 */
	public Entity getEntityAtPosition(double x, double y) {
		for (Entity entity : this.getEntities()) {
		    if ((entity.getPositionX() == x) && (entity.getPositionY() == y)) {
		    	return entity;
		    }
		} 
		return null;
	}
	
	/**
	 * Returns a boolean indicating whether there is significant overlap between this entity and other entities
	 * 
	 * @param entity
	 * @return this functions returns true if and only if the entity significantly overlaps with 
	 * 		   at least one other entity in this world and that entity is not equal to the given entity
	 */
	public boolean significantOverlap(Entity entity) {
		for (Entity other : this.getEntities()) {
		    if (entity.significantOverlap(other) && (entity != other)) {
		    	return true;
		    }
		} 
		return false;
	}

	/**
	 * A function that returns the position of a collision between this world and a collidable
	 * 
	 * @post This function executes in such a manner that ensures that, at the end of the function:
	 * 			* If the collidable is not a world, collidable.getCollisionPosition(this) is returned
	 * 			* If the collidable is a world, 0.0 is returned
	 */
	@Override
	public double[] getCollisionPosition(ICollidable collidable) {
		if (!(collidable instanceof World)) {
			return collidable.getCollisionPosition(this);
		} else {
			return new double[]{0, 0};
		}
	}

	/**
	 * A function that returns the time to collision between this world and a collidable
	 * 
	 * @post This function executes in such a manner that ensures that, at the end of the function:
	 * 			* If the collidable is not a world, collidable.getTimeToCollision(this) is returned
	 * 			* If the collidable is a world, 0.0 is returned
	 */
	@Override
	public double getTimeToCollision(ICollidable collidable) {
		if (!(collidable instanceof World)) {
			return collidable.getTimeToCollision(this);
		}
		return 0.0;
	}

	
	public void collide(ICollidable collidable) {		
	}
	
	
}
