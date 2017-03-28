package asteroids.model;

public class Bullet extends Entity {

	protected static final double MAX_SPEED = 300000;
	protected static final double MIN_RADIUS = 1;
	protected static final double DENSITY = 7.8E12;
	
	private World world; 
	private Ship ship; 
	private Ship source; 	

	public Bullet(double x, double y, double xVelocity, double yVelocity, double radius) {
		setPosition(x, y);
		setVelocity(xVelocity, yVelocity);
		setRadius(radius);
	}
	
	public double getMass(){
		double mass = (4/3.0)*Math.PI*Math.pow(this.getRadius(), 3)*DENSITY;
		return mass;
	}
	
	public World getWorld(){return this.world;}
	
	public Ship getSource(){return this.source;}
	
	public void move(double duration) throws IllegalArgumentException {
		if (isValidDuration(duration)) {
			double deltaX = getVelocityX()*duration;
			double deltaY = getVelocityY()*duration;
			setPosition(getPositionX()+deltaX, getPositionY()+deltaY);
		} else {
			throw new IllegalArgumentException();
		}
		
	}
	
	public void makePartOfWorld(World world) {
		if (!isPartOfWorld() && !isPartOfShip()) {
			try {
				this.world = world;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void makePartOfShip(Ship ship) {
		if (!isPartOfWorld() && !isPartOfShip()) {
			this.ship = ship;
		}
	};
	
	public void removeFromShip() {
		this.ship = null;
	};
	
	public boolean isPartOfShip() {
		if (this.ship instanceof Ship) {
			return true;
		} else {
			return false;
		}
	}
	
	public Ship getShip() {return this.ship;}

}




