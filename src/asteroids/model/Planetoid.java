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
	 * This method spawns asteroids when, if ever, an planetoid dies and satisfies 
	 * the given specifications for spawning asteroids.
	 * 
	 * @param world
	 *        The world within which the entity lies
	 * @return This function returns two child asteroids, such that: 
	 * 				* The speed of their velocities is 1.5 times greater then the parent planetoid.
	 *  			* The direction of the velocity of the first child is determined at random. 
	 *  			* The other child moves in the opposite direction.
	 *  			* Their radius is equal to half of radius of their parent.
	 *  			* Both children are placed at a distance r/2 from the center of the parent planetoid.
	 *  			* This planetoid is removed from his world.
	 * @throws NullPointerException
	 *         The other entity does not exist.
	 *         | other == null
	 */
 	public void spawnAsteroids(World world) {
		double magnitude = (1.5)*(Math.pow(this.getVelocityX(), 2)+Math.pow(this.getVelocityY(), 2));
		double velX = Math.sqrt((Math.random()*magnitude));
		double velY = Math.sqrt(((1-Math.random())*magnitude));
		Asteroid asteroid1 = new Asteroid(this.getPositionX()-(this.getRadius()/2.0),this.getPositionY(),velX,velY,this.getRadius()/2.0);
		Asteroid asteroid2 = new Asteroid(this.getPositionX()+(this.getRadius()/2.0),this.getPositionY(),-velX,-velY,this.getRadius()/2.0);
		world.removeEntity(this);
		
		Stream.of(asteroid1, asteroid2)
			.forEach(asteroid -> {
				if (!world.significantOverlap(asteroid) && asteroid.withinBoundaries(world)) {
					world.addEntity(asteroid);
				} else {
					asteroid.terminate();
				}
			}
		);
 	}

 	/**
	 * This method terminates a planetoid.
	 * 
	 * @post Terminate a planetoid and occasionally spawn a asteroid.
	 * 		 | if (this.getRadius()>= 30) 
	 * 		 |    then spawnAsteroidTerminate(this.world)
	 * 		 | else 
	 * 		 |     super.terminate()
	 */
 	@Override
 	public void terminate() {
 		if (isPartOfWorld() && getRadius() >= 30) {
 			spawnAsteroids(this.world);
		}
		super.terminate();
 	}
 	/**
	 * This method returns the total distance traveled by a planetoid.
	 * 
	 * @see implementation
	 */
	public double getTotalTraveledDistance() {
		return this.totalTraveledDistance;
	}
	
	/**
	 * This method increases the total distance traveled by a planetoid.
	 * 
	 * @param distance
	 * 		  The distance a planetoid has travelled in a given time frame.
	 * 
	 * @post Decrease the radius of a planetoid and when smaller then 5, terminate.
	 *       | if (newRadius < 5)
	 *       |    then this.terminate()
	 *       | else 
	 *       |    setRadius(newRadius)
	 */
	public void increaseTotalTraveledDistance(double distance) {
		totalTraveledDistance += distance;
		
		double newRadius = getRadius() - (0.000001*distance);

		if (newRadius < 5) {
			this.terminate();
		} else {
			setRadius(newRadius);
		}
	}
	/**
	 * @see implementation Superclass.
	 * @post Total distance traveled increases by the traveled distance
	 */
	@Override
	public void move(double duration) {
		super.move(duration);
		double distanceX = this.getVelocityX()*duration;
		double distanceY = this.getVelocityY()*duration;
		double distanceTraveled = Math.sqrt(Math.pow(distanceX, 2)+Math.pow(distanceY, 2));
		increaseTotalTraveledDistance(distanceTraveled);
	}
	
	/**
	 * A function that resolves a collision event between and planetoid and another entity.
	 * @param this
	 * @post This function executes in such a manner that ensures that, at the end of the function:
	 * 			* In the case that the entity is a Ship: the ship is teleported, if it doesn't overlap with other 
	 * 			  entities, to a random location in the world.
	 * 			* For other MinorPlanets we use the defaultCollide helper function.
	 */
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
