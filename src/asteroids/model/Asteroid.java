package asteroids.model;

public class Asteroid extends MinorPlanet {

	static double MAX_DENSITY =2.65E12;

	public Asteroid(double x, double y, double xVelocity, double yVelocity, double radius) {
		super(x, y, xVelocity, yVelocity, radius);
	}
	

	@Override
	public double getMinDensity() {
		return MAX_DENSITY;
	}
	
	@Override
	public double getMass() {
		return (4/3)*Math.PI*Math.pow(getRadius(), 3)*MAX_DENSITY;
	}
	

	@Override
	public void move(double duration) {
		// TODO Auto-generated method stub
		
	}

}
