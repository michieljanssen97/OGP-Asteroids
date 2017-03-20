package asteroids.model;

import asteroids.util.ModelException;

public interface IEntity {

	public boolean isTerminated();
	
	public double getPositionX() throws ModelException;
	public double getPositionY() throws ModelException;
	public double getVelocityX() throws ModelException;
	public double getVelocityY() throws ModelException;
	public double getRadius() throws ModelException;
	public double getOrientation() throws ModelException;
	public double getMass();
	
	public boolean isPartOfWorld();
	public void makePartOfWorld();
	public World getWorld();
	
	public double getDistanceBetween(Entity other);
	public boolean overlap(Entity other) throws NullPointerException;
	public double getTimeToCollision(Entity other);
	public double[] getCollisionPosition(Entity other);
	
	public boolean significantOverlap(Entity other);
	public boolean withinBoundaries(Entity other);
	public boolean withinBoundaries(World world);
	public boolean apparentlyCollide(Entity other);
}
