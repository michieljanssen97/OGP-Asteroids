package asteroids.model;

/**
 * 
 * A class that defines the ICollidable interface for the Asteroids game.
 *
 * @author Michiel Janssen & Jelle Pelgrims
 *
 */

public interface ICollidable {

	default double[] getCollisionPosition(ICollidable collidable) {
		if (collidable instanceof Entity) {
			return getCollisionPosition((Entity) collidable);
		} else if (collidable instanceof World) {
			return getCollisionPosition((World) collidable);
		}
		return null;
	}
	public double[] getCollisionPosition(Entity entity);
	public double[] getCollisionPosition(World world);
	
	default double getTimeToCollision(ICollidable collidable) {
		if (collidable instanceof Entity) {
			return getTimeToCollision((Entity) collidable);
		} else if (collidable instanceof World) {
			return getTimeToCollision((World) collidable);
		}
		return 0.0;
	}
	public double getTimeToCollision(Entity entity);
	public double getTimeToCollision(World world);
	
	public void defaultCollide(Entity entity);
	default void collide(ICollidable collidable) {
		if (collidable instanceof Entity) {
			collide((Entity) collidable);
		} else if (collidable instanceof World) {
			collide((World) collidable);
		}
	}
	public void collide(Entity entity);
	public void collide(World world);
	
	public boolean isDestroyed();
	public void terminate();
	public boolean isTerminated();
	
}
