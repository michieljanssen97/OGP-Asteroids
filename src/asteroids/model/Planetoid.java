package asteroids.model;

public class Planetoid extends MinorPlanet {

	public Planetoid(double x, double y, double xVelocity, double yVelocity, double radius, double totalTraveledDistance) {
		super(x, y, xVelocity, yVelocity, radius);
		
		this.totalTraveledDistance = totalTraveledDistance;
	}

	public double getTotalTraveledDistance() {
		// TODO Auto-generated method stub
		return this.totalTraveledDistance;
	}
	
}
