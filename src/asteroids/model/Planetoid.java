package asteroids.model;

public class Planetoid extends MinorPlanet {
	
	
	static double MAX_DENSITY = 0.917E12;
	double totalTraveledDistance = 0;

	public Planetoid(double x, double y, double xVelocity, double yVelocity, double radius, double totalTraveledDistance) {
		super(x, y, xVelocity, yVelocity, radius);
		
		this.totalTraveledDistance = totalTraveledDistance;
	}
	
 	@Override
 	public double getRadius() {
			return this.radius - (0.0001*totalTraveledDistance);
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
	
}
