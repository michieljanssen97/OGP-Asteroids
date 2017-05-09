package asteroids.model;

public class Asteroid extends MinorPlanet implements ICollidable {

	static double MAX_DENSITY =2.65E12;

	public Asteroid(double x, double y, double xVelocity, double yVelocity, double radius) {
		super(x, y, xVelocity, yVelocity, radius);
	}
	
	public double getMaxDensity() {
		return MAX_DENSITY;
	}
	
	@Override
	public double getMass() {
		return (4/3)*Math.PI*Math.pow(getRadius(), 3)*getMaxDensity();
	}
	
	public void collide(Entity entity) {
		if (entity instanceof Ship) {
			entity.terminate();
		}
		else if (entity instanceof Asteroid) {defaultCollide(entity);} 
		else if (entity instanceof Planetoid) {defaultCollide(entity);}
		else {
			entity.collide(this);
		}
	}
	
	public void collide(World world) {
		world.defaultCollide(this);
	}

}
