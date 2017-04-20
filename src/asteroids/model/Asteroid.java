package asteroids.model;

public class Asteroid extends MinorPlanet {

	public Asteroid(double x, double y, double xVelocity, double yVelocity, double radius, String type) {
		super(x, y, xVelocity, yVelocity, radius, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getMaxSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMinRadius() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMinDensity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void move(double duration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collide() {
		// TODO Auto-generated method stub
		
	}

}
