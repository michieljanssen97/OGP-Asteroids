package asteroids.model;

/**
 * 
 * A class that defines the ICollidable interface for the Asteroids game.
 *
 * @author Michiel Janssen & Jelle Pelgrims
 *
 */

public interface ICollidable {

	public double[] getCollisionPosition(ICollidable collidable);
	public double[] getCollisionPosition(Entity entity);
	public double[] getCollisionPosition(World world);
	
	public double getTimeToCollision(ICollidable collidable);
	public double getTimeToCollision(Entity entity);
	public double getTimeToCollision(World world);
	
	public void defaultCollide(Entity entity);
	public void collide(ICollidable collidable);
	public void collide(Entity entity);
	public void collide(World world);
	
	public boolean isDestroyed();
	public void terminate();
	public boolean isTerminated();
}
