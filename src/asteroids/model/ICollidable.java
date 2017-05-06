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
	public double getTimeToCollision(ICollidable collidable);
	
	public void defaultCollide(Entity entity);
	
	default void collide(ICollidable collidable) {
		if (collidable instanceof Entity) {
			collide((Entity) collidable);
		}
		else if (collidable instanceof World) {
			collide((World) collidable);
		} else {
			System.out.print("Sheeeeit");
		}
	}
	public void collide(Entity entity);
	public void collide(World world);
	
}
