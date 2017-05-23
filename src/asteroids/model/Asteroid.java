package asteroids.model;

public class Asteroid extends MinorPlanet implements ICollidable {

	/**
	 * Initialize this new asteroid with a given position, velocity, radius.
	 * 
	 * @see superclass constructor
	 */
	public Asteroid(double x, double y, double xVelocity, double yVelocity, double radius) {
		super(x, y, xVelocity, yVelocity, radius);
	}
	
	/**
	 * Returns the minimum density for this asteroid
	 */
	public double getMinDensity() {return 2.65E12;}
	
	public void collide(Entity entity) {
		if (entity instanceof Ship) {
			entity.destroy();
		}
		else if (entity instanceof Asteroid) {defaultCollide(entity);} 
		else if (entity instanceof Planetoid) {defaultCollide(entity);}
		else {
			entity.collide(this);
		}
	}

}
