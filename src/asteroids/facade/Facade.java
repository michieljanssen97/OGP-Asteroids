package asteroids.facade;

import java.awt.geom.Point2D;

import asteroids.model.Ship;

import asteroids.part1.facade.IFacade;
import asteroids.model.Ship;
import asteroids.util.ModelException;

public class Facade implements IFacade {

	@Override
	public Ship createShip() throws ModelException {
		try {
			return new Ship(0, 0, 0, 0, 11, 0);
		} catch (Exception error) {
			throw error;
		}
	};
	
	@Override
	public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius, double orientation)
			throws ModelException {
		try {
			return new Ship(x, y, xVelocity, yVelocity, radius, orientation);
		} catch (Exception error) {
			throw error;
		}
	}

	@Override
	public double[] getShipPosition(Ship ship) throws ModelException {
		double[] position = {ship.getPosition().x, ship.getPosition().y};
		return position;
	}

	@Override
	public double[] getShipVelocity(Ship ship) throws ModelException {
		double[] velocity = {ship.getVelocity().x, ship.getVelocity().y};
		return velocity;
	}

	@Override
	public double getShipRadius(Ship ship) throws ModelException {
		return ship.getRadius();
	}

	@Override
	public double getShipOrientation(Ship ship) throws ModelException {
		return ship.getOrientation();
	}

	@Override
	public void move(Ship ship, double dt) throws ModelException {
		try {
			ship.move(dt);
		} catch (Exception e) {
			throw new ModelException("temporary");
		}
	}

	@Override
	public void thrust(Ship ship, double amount) throws ModelException {
		ship.thrust(amount);
	}

	@Override
	public void turn(Ship ship, double angle) throws ModelException {
		ship.turn(angle);
	}

	@Override
	public double getDistanceBetween(Ship ship1, Ship ship2) throws ModelException {
		try {
			return ship1.getDistanceBetween(ship2);
		} catch (Exception e) {
			throw new ModelException("temporary");
		}
	}

	@Override
	public boolean overlap(Ship ship1, Ship ship2) throws ModelException {
		try {
			return ship1.overlap(ship2);
		} catch (Exception e) {
			throw new ModelException("temporary");
		}
	}

	@Override
	public double getTimeToCollision(Ship ship1, Ship ship2) throws ModelException {
		try {
			return ship1.getTimeToCollision(ship2);
		} catch (Exception e) {
			throw new ModelException("temporary");
		}
	}

	@Override
	public double[] getCollisionPosition(Ship ship1, Ship ship2) throws ModelException {
		try {
			Point2D.Double collPos = ship1.getCollisionPosition(ship2);
			double[] Pos =  {collPos.x, collPos.y};
			return Pos;
		} catch (Exception e) {
			throw new ModelException("temporary");
		}
	}

}
