package asteroids.model;

import asteroids.part2.CollisionListener;

/**
 * 
 * A class that defines the ICollidable interface for the Asteroids game.
 *
 * @author Michiel Janssen & Jelle Pelgrims
 *
 */

public interface ICollidable {

	public double[] getCollisionPosition(ICollidable collidable);
	public double getTimeToCollision(ICollidable collidable);
	public void collide(ICollidable collidable, CollisionListener collisionListener);
	
}
