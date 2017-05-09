package asteroids.model;

public class Asteroid extends MinorPlanet implements ICollidable {

	public Asteroid(double x, double y, double xVelocity, double yVelocity, double radius) {
		super(x, y, xVelocity, yVelocity, radius);
		DENSITY =2.65E12;
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
