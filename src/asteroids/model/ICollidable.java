package asteroids.model;

import asteroids.util.ModelException;

public interface ICollidable {

	public double getTimeToCollision(ICollidable other);
	public double[] getCollisionPosition(ICollidable other);

}
