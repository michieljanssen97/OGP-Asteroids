package asteroids.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	
	private Set<Ship> ships = new HashSet<Ship>();
	private Set<Bullet> bullets = new HashSet<Bullet>();
	
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
	 * Returns the ships in this world
	 * @see implementation
	 */
	public Set<Ship> getShips(){
		return this.ships;
	}
	
	/**
	 * Returns the bullets in this world
	 * @see implementation
	 */
	public Set<Bullet> getBullets(){
		return this.bullets;
	}
	
	/**
	 * Returns the entities in this world
	 * @return All ships and bullets in the world are returned
	 *         | this.bullets + this.ships
	 */
	public Set<Entity> getEntities(){
		Set<Entity> entities = new HashSet<Entity>();
		entities.addAll(this.bullets);
		entities.addAll(this.ships);
		return entities;
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
		} else if (entity.isPartOfWorld() || significantOverlap(entity) || !entity.withinBoundaries(this)){
			throw new IllegalArgumentException();
		} else {
			if (entity.canBePartOfWorld()) {
				entity.makePartOfWorld(this);
				if (entity instanceof Ship) {
					ships.add((Ship) entity);
				} else if (entity instanceof Bullet) {
					bullets.add((Bullet) entity);
				}
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
			if (entity instanceof Ship) {
				ships.remove(entity);
			} else if (entity instanceof Bullet) {
				bullets.remove(entity);
			}
		}
	}
	
	/**
	 * Returns the next collision time, null if no collisions
	 * @see implementation
	 */
	public double getNextCollisionTime() {
		Double collisionTime = null;
		ICollidable[] collidables = getNextCollisionObjects();
		try {
			collisionTime = collidables[0].getTimeToCollision(collidables[1]);
		} catch (Exception e){
			// No harm done
		}
		return collisionTime;

	}

	/**
	 * Returns the next collision position, null if no collisions
	 * @see implementation 
	 */
	public double[] getNextCollisionPosition() {
		double[] collisionPosition = null;
		ICollidable[] collidables = getNextCollisionObjects();
		try {
			collisionPosition = collidables[0].getCollisionPosition(collidables[1]);
		} catch (Exception e) {
			// No harm done
		}
		return collisionPosition;
	}
	
	/**
	 * Returns the two objects that will collide first, null if no collisions
	 * 
	 * ???
	 * 
	 * @return An ICollidable array containing the two colliding objects, or null if no collisions occur 
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
	 * A function for advancing the game
	 * 
	 * @effect 
	 * 
	 * @param duration
	 * @throws Exception
	 */
	public void evolve(double duration) throws Exception {
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
					resolveCollision((Entity)collidables[0], (Entity)collidables[1]);
					((Entity)collidables[0]).move(duration);
					((Entity)collidables[1]).move(duration);
				} else if (collidables[0] instanceof World && collidables[1] instanceof Entity) {
					resolveCollision((Entity)collidables[0], (World)collidables[1]);
					((Entity)collidables[1]).move(duration);
					
				} else if (collidables[0] instanceof Entity && collidables[1] instanceof World) {
					resolveCollision((Entity)collidables[0], (World)collidables[1]);
					((Entity)collidables[0]).move(duration);
				}
			
				//  Subtract firstTimeCollision from delta t and go to step 1.
				duration -= firstCollisionTime;
			}
		} 
	}
	
	/**
	 * Advances all entities in this world
	 * 
	 * @param duration
	 * @post All entities in this world are advanced for the given duration
	 * 		 | for entity in this.GetEntities()
	 * 		 | 		entity.move(duration)
	 */
	public void advanceEntities(double duration) {
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
	 * @see implementation
	 */
	private boolean significantOverlap(Entity entity) {
		for (Entity other : this.getEntities()) {
		    if (entity.significantOverlap(other) && (entity != other)) {
		    	return true;
		    }
		} 
		return false;
	}
	
	private void resolveCollision(Entity entity, World world) {
		double distanceToLeftWall = entity.getPositionX();
		double distanceToRightWall = world.getWidth() - entity.getPositionX();
		double distanceToUpperWall = world.getHeight() - entity.getPositionY();
		double distanceToBottomWall = entity.getPositionY();
		
		double minDistance = Math.min(Math.min(distanceToUpperWall, distanceToBottomWall), Math.min(distanceToLeftWall, distanceToRightWall));
		
		if (minDistance == distanceToLeftWall || minDistance == distanceToRightWall) {
			entity.setVelocity(-entity.getVelocityX(), entity.getVelocityY());
		} else if (minDistance == distanceToUpperWall || minDistance == distanceToBottomWall) {
			entity.setVelocity(entity.getVelocityX(), -entity.getVelocityY());
		}
		
		if (entity instanceof Bullet){
			if (((Bullet) entity).Counter() == true){
				this.removeEntity(((Bullet) entity));
			}
		}
	}
		

	
	
	private void resolveCollision(Entity entity1, Entity entity2) {
		if (entity1 instanceof Ship && entity2 instanceof Ship) {
			
			double deltaPosX = entity2.getPositionX()-entity1.getPositionX();
			double deltaPosY = entity2.getPositionY()-entity1.getPositionY();

			double deltaVelX = entity2.getVelocityX()-entity1.getVelocityX();
			double deltaVelY = entity2.getVelocityY()-entity1.getVelocityY();
			
			double deltaVR = (deltaVelX*deltaPosX)  + (deltaVelY*deltaPosY);
			
			double radiusSum = entity1.getRadius() + entity2.getRadius();
			double J = (2*entity1.getMass()*entity2.getMass()*deltaVR)/((entity1.getMass()+entity2.getMass())*radiusSum);
			
			double Jx = (J*deltaPosX)/radiusSum;	
			double Jy = (J*deltaPosY)/radiusSum;
			
			double newVelocityX1 = entity1.getVelocityX() + (Jx/entity1.getMass());
			double newVelocityY1 = entity1.getVelocityY() + (Jy/entity1.getMass());
			
			double newVelocityX2 = entity2.getVelocityX() - (Jx/entity2.getMass());
			double newVelocityY2 = entity2.getVelocityY() - (Jy/entity2.getMass());
			
			entity1.setVelocity(newVelocityX1, newVelocityY1);
			entity2.setVelocity(newVelocityX2, newVelocityY2);
			
		} else if ((entity1 instanceof Ship && entity2 instanceof Bullet)) {
			
			if (((Bullet) entity2).getSource() == entity1) {
				((Bullet) entity2).setCounter(0);
				entity2.removeFromWorld();
				entity2.setPosition(entity1.getPositionX(), entity1.getPositionY());
				((Ship) entity1).loadBullets((Bullet) entity2);
				this.removeEntity(entity2);
			} else {
				this.removeEntity(entity1);
				this.removeEntity(entity2);
			}
			
		} else if ((entity2 instanceof Ship && entity1 instanceof Bullet)) {
			
			if (((Bullet) entity1).getSource() == entity2) {
				((Bullet) entity1).setCounter(0);
				entity1.removeFromWorld();
				entity1.setPosition(entity2.getPositionX(), entity2.getPositionY());
				((Ship) entity2).loadBullets((Bullet) entity1);
				this.removeEntity(entity1);
			} else {
				this.removeEntity(entity2);
				this.removeEntity(entity1);
			}
			
		} else if ((entity2 instanceof Bullet && entity1 instanceof Bullet)) {
			this.removeEntity(entity2);
			this.removeEntity(entity1);
		}
	}

	@Override
	public double[] getCollisionPosition(ICollidable collidable) {
		if (!(collidable instanceof World)) {
			return collidable.getCollisionPosition(this);
		} else {
			return new double[]{0, 0};
		}
	}

	@Override
	public double getTimeToCollision(ICollidable collidable) {
		if (!(collidable instanceof World)) {
			return collidable.getTimeToCollision(this);
		}
		return 0.0;
	}
}
