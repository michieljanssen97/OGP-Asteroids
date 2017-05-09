package asteroids.model;

import java.util.Random;

public class Planetoid extends MinorPlanet {
	
	
	static double MAX_DENSITY = 0.917E12;
	private double totalTraveledDistance = 0;
	private double radiusUponCreation = 0;
	
	public Planetoid(double x, double y, double xVelocity, double yVelocity, double radius, double totalTraveledDistance) {
		super(x, y, xVelocity, yVelocity, radius);
		
		this.totalTraveledDistance = totalTraveledDistance;
		this.radiusUponCreation = radius;
	}
 	
 	public void spawnAsteroids() {
 			double magnitude = (1.5)*(Math.pow(this.getVelocityX(), 2)+Math.pow(this.getVelocityY(), 2));
 			double velX = Math.sqrt((Math.random()*magnitude));
 			double velY = Math.sqrt(((1-Math.random())*magnitude));
 			Asteroid asteroid1 = new Asteroid(this.getPositionX()-((this.radiusUponCreation/2)+1),this.getPositionY(),velX,velY,this.radiusUponCreation/2);
 			Asteroid asteroid2 = new Asteroid(this.getPositionX()+((this.radiusUponCreation/2)+1),this.getPositionY(),-velX,-velY,this.radiusUponCreation/2);
 			world.addEntity(asteroid1);
 			world.addEntity(asteroid2);
 	}

 	@Override
 	public void terminate() {
 		if (isPartOfWorld() && getRadius() >= 30) {
			spawnAsteroids();
		}
 		this.isTerminated = true;
 	}
 	
	@Override
	public double getMinDensity() {
		return MAX_DENSITY;
	}
	
	@Override
	public double getMass() {
		return (4/3)*Math.PI*Math.pow(getRadius(), 3)*MAX_DENSITY;
	}
	
	public double getTotalTraveledDistance() {
		return this.totalTraveledDistance;
	}
	
	public void increaseTotalTraveledDistance(double distance) {
		totalTraveledDistance += distance;
	}
	
	public double getRadiusUponCreation(){
		return this.radiusUponCreation;
	}
	
	@Override
	public void move(double duration) {
		super.move(duration);
		double distanceX = this.getVelocityX()*duration;
		double distanceY = this.getVelocityY()*duration;
		double distanceTraveled = Math.abs(distanceX) + Math.abs(distanceY); //Math.abs(Math.sqrt(Math.pow(distanceX, 2)+Math.pow(distanceY, 2)));
		increaseTotalTraveledDistance(distanceTraveled);
		
		double newRadius = getRadiusUponCreation() - (0.000001*getTotalTraveledDistance());

		if (newRadius < 5) {
			this.terminate();
		} else {
			setRadius(newRadius);
		}
		


	}
	
	public void collide(Entity entity) {
		if (entity instanceof Ship) {
			Random rand = new Random();
			double[] randomPosition = {rand.nextInt((int)((getWorld().getWidth()-getRadius()))), 
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
