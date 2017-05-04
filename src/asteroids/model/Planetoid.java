package asteroids.model;

public class Planetoid extends MinorPlanet {
	
	
	static double MAX_DENSITY = 0.917E12;
	double totalTraveledDistance=0;
	double radiusUponCreation = 0;
	
	public Planetoid(double x, double y, double xVelocity, double yVelocity, double radius, double totalTraveledDistance) {
		super(x, y, xVelocity, yVelocity, radius);
		
		this.totalTraveledDistance = totalTraveledDistance;
		this.radiusUponCreation = radius;
	}
	
 	@Override
 	public double getRadius() {
 		return this.radius- (0.01*getTotalTraveledDistance());
 	}
 	
 	
 	public void spawnAsteroids(){
 			double magnitude = (1.5)*(Math.pow(this.getVelocityX(), 2)+Math.pow(this.getVelocityY(), 2));
 			double velX = Math.sqrt((Math.random()*magnitude));
 			double velY = Math.sqrt(((1-Math.random())*magnitude));
 			Asteroid asteroid1 = new Asteroid(this.getPositionX()-this.radiusUponCreation/2,this.getPositionY(),velX,velY,this.radiusUponCreation/2);
 			Asteroid asteroid2 = new Asteroid(this.getPositionX()+this.radiusUponCreation/2,this.getPositionY(),-velX,-velY,this.radiusUponCreation/2);
 			this.getWorld().addEntity(asteroid1);
 			this.getWorld().addEntity(asteroid2);
 			this.getWorld().removeEntity(this);
 			this.terminate();
 	}

	@Override
	public double getMinDensity() {
		return MAX_DENSITY;
	}
	
	@Override
	public double getMass() {
		return (4/3)*Math.PI*Math.pow(getRadius(), 3)*MAX_DENSITY;
	}
	
	public double getTotalTraveledDistance(){
		return this.totalTraveledDistance;
	}
	
	public double setTotalTraveledDistance(double amount) {
		totalTraveledDistance+=amount;
		return totalTraveledDistance;
	}
	
	public double getRadiusUponCreation(){
		return this.radiusUponCreation;
	}
	
	
}
