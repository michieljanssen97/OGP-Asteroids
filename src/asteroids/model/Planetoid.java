package asteroids.model;

import java.util.Random;
import java.util.stream.Stream;

/**
 * A class that defines a Planetoid for the Asteroids game.
 * 
 * @author Michiel Janssen & Jelle Pelgrims
 *
 */
public class Planetoid extends MinorPlanet {
	
	private double totalTraveledDistance = 0;
	
	/**
	 * Initialize this new Planetoid with a given position, velocity, radius.
	 * 
	 * @see superclass constructor
	 */
	public Planetoid(double x, double y, double xVelocity, double yVelocity, double radius, double totalTraveledDistance) {
		super(x, y, xVelocity, yVelocity, radius);
		
		increaseTotalTraveledDistance(totalTraveledDistance);
	}
 	
	/**
	 * Returns the minimum density
	 */
	@Override
	public double getMinDensity() {return 0.917E12;}
	
	/**
	 * TODO
	 * @param world
	 */
 	public void spawnAsteroidsTerminate(World world) {
 			double magnitude = (1.5)*(Math.pow(this.getVelocityX(), 2)+Math.pow(this.getVelocityY(), 2));
 			double velX = Math.sqrt((Math.random()*magnitude));
 			double velY = Math.sqrt(((1-Math.random())*magnitude));
 			Asteroid asteroid1 = new Asteroid(this.getPositionX()-(this.getRadius()/2.0),this.getPositionY(),velX,velY,this.getRadius()/2.0);
 			Asteroid asteroid2 = new Asteroid(this.getPositionX()+(this.getRadius()/2.0),this.getPositionY(),-velX,-velY,this.getRadius()/2.0);
 			world.removeEntity(this);
 			
 			Stream.of(asteroid1, asteroid2)
 				.forEach( 
 					asteroid -> {
	 					if (!world.significantOverlap(asteroid) && asteroid.withinBoundaries(world)) {
	 						world.addEntity(asteroid);
	 					} else {
	 						asteroid.terminate();
	 					}
 					}
 				);
 	}

 	@Override
 	public void terminate() {
 		if (isPartOfWorld() && getRadius() >= 30) {
 			spawnAsteroidsTerminate(this.world);
		}
		super.terminate();
 	}
	
	public double getTotalTraveledDistance() {
		return this.totalTraveledDistance;
	}
	
	public void increaseTotalTraveledDistance(double distance) {
		totalTraveledDistance += distance;
		
		double newRadius = getRadius() - (0.000001*distance);

		if (newRadius < 5) {
			this.terminate();
		} else {
			setRadius(newRadius);
		}
	}
	
	@Override
	public void move(double duration) {
		super.move(duration);
		double distanceX = this.getVelocityX()*duration;
		double distanceY = this.getVelocityY()*duration;
		double distanceTraveled = Math.sqrt(Math.pow(distanceX, 2)+Math.pow(distanceY, 2));
		increaseTotalTraveledDistance(distanceTraveled);
	}
	
	public void collide(Entity entity) {
		if (entity instanceof Ship) {
			Random rand = new Random();
			double[] randomPosition = {rand.nextInt((int)((getWorld().getWidth())-getRadius())), 
								       rand.nextInt((int)((getWorld().getHeight()) - getRadius()))};
			entity.setPosition(randomPosition[0], randomPosition[1]);
			if (getWorld().significantOverlap(entity)) {
				entity.destroy();
			}
		}
		else if (entity instanceof Asteroid) {defaultCollide(entity);} 
		else if (entity instanceof Planetoid) {defaultCollide(entity);}
		else {
			entity.collide(this);
		}
	}

}
