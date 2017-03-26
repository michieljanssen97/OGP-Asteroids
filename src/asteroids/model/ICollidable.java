package asteroids.model;

public interface ICollidable {

	public double[] getCollisionPosition(ICollidable collidable);
	public double getTimeToCollision(ICollidable collidable);
	
}
