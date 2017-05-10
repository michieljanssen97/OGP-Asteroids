package asteroids.model;

import java.util.Random;

public class Planetoid extends MinorPlanet {
	
	private double totalTraveledDistance = 0;
	private double radiusUponCreation = 0;
	
	public Planetoid(double x, double y, double xVelocity, double yVelocity, double radius, double totalTraveledDistance) {
		super(x, y, xVelocity, yVelocity, radius);
		
		increaseTotalTraveledDistance(totalTraveledDistance);
		this.radiusUponCreation = radius;
	}
 	
	@Override
	public double getMinDensity() {return 0.917E12;}
	
 	public void spawnAsteroids() {
 			double magnitude = (1.5)*(Math.pow(this.getVelocityX(), 2)+Math.pow(this.getVelocityY(), 2));
 			double velX = Math.sqrt((Math.random()*magnitude));
 			double velY = Math.sqrt(((1-Math.random())*magnitude));
 			Asteroid asteroid1 = new Asteroid(this.getPositionX()-((this.radiusUponCreation/2)+1),this.getPositionY(),velX,velY,this.radiusUponCreation/2);
 			Asteroid asteroid2 = new Asteroid(this.getPositionX()+((this.radiusUponCreation/2)+1),this.getPositionY(),-velX,-velY,this.radiusUponCreation/2);
 			
 			if (!world.significantOverlap(asteroid1)) {
 				world.addEntity(asteroid1);
 			} else {
 				asteroid1.destroy();
 			}
 			
 			if (!world.significantOverlap(asteroid2)) {
 				world.addEntity(asteroid2);
 			} else {
 				asteroid2.destroy();
 			}
 	}

 	@Override
 	public void terminate() {
 		if (isPartOfWorld() && getRadius() >= 30) {
			spawnAsteroids();
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
	
	public double getRadiusUponCreation(){
		return this.radiusUponCreation;
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
	
	public void collide (World world) {
		world.defaultCollide(this);
	}
}
