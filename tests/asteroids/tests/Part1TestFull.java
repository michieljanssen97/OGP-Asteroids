package asteroids.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Ship;
import asteroids.facade.Facade;
import asteroids.part1.facade.IFacade;
import asteroids.util.ModelException;

public class Part1TestFull {
	
	/**
	 * Write a test suite for the class Ship that tests each _public_ function
	 */
	
	private static final double EPSILON = 0.0001;

	IFacade facade;

	@Before
	public void setUp() {
		facade = new Facade();
	}
	
	@Test
	public void testCreateShip() throws ModelException {
		Ship ship = facade.createShip(100, 200, 10, -10, 20, Math.PI);
		assertNotNull(ship);
		double[] position = facade.getShipPosition(ship);
		assertNotNull(position);
		assertEquals(100, position[0], EPSILON);
		assertEquals(200, position[1], EPSILON);
		assertEquals(20, facade.getShipRadius(ship), EPSILON);
	}

	@Test(expected = ModelException.class)
	public void testCreateShipXIsNan() throws ModelException {
		facade.createShip(Double.NaN, 200, 10, -10, 20, -Math.PI);
	}

	@Test(expected = ModelException.class)
	public void testCreateShipRadiusIsNaN() throws ModelException {
		facade.createShip(100, 200, 10, 10, Double.NaN, -Math.PI);
	}
	
	@Test(expected = ModelException.class)
	public void testCreateShipOrientationIsNaN() throws ModelException {
		facade.createShip(100, 200, 10, 10, 10, Double.NaN);
	}
	
	@Test(expected = ModelException.class)
	public void testCreateShipRadiusNegative() throws ModelException {
		facade.createShip(100, 200, 10, -10, -20, -Math.PI);
	}


	@Test
	public void testMove() throws ModelException {
		Ship ship = facade.createShip(100, 100, 30, -15, 20, 0);
		facade.move(ship, 1);
		double[] position = facade.getShipPosition(ship);
		assertNotNull(position);
		assertEquals(130, position[0], EPSILON);
		assertEquals(85, position[1], EPSILON);
	}
	
	@Test
	public void testTurn() throws ModelException {
		Ship ship = facade.createShip(100, 100, 0, 0, 20, Math.PI);
		facade.turn(ship, Math.PI/2.0);
		double orientation = facade.getShipOrientation(ship);
		assertNotNull(orientation);
		assertEquals(Math.PI*1.5, orientation, EPSILON);
	}
	
	@Test
	public void testThrust() throws ModelException {
		Ship ship = facade.createShip(100, 100, 0, 0, 20, 0);
		facade.thrust(ship, 10);
		double[] velocity = facade.getShipVelocity(ship);
		assertNotNull(velocity);
		assertEquals(10, velocity[0], EPSILON);
	}
	
	@Test
	public void testGetDistanceBetween() throws ModelException {
		Ship ship1 = facade.createShip(100, 100, 30, -15, 20, 0);
		Ship ship2 = facade.createShip(50, 100, 30, -15, 20, 0);
		double distanceBetween = facade.getDistanceBetween(ship1, ship2);
		assertNotNull(distanceBetween);
		assertEquals(50, distanceBetween, EPSILON);
	}
	
	@Test
	public void testOverlap() throws ModelException {
		Ship ship1 = facade.createShip(100, 100, 30, -15, 20, 0);
		Ship ship2 = facade.createShip(100, 100, 30, -15, 20, 0);
		boolean overlap = facade.overlap(ship1, ship2);
		assertNotNull(overlap);
		assertEquals(true, overlap);
	}
	
	@Test
	public void testGetTimetoCollision() throws ModelException {
		Ship ship1 = facade.createShip(0, 0, 2, 0, 20, 0);
		Ship ship2 = facade.createShip(100, 0, 0, 0, 20, 0);
		double timeToCollision = facade.getTimeToCollision(ship1, ship2);
		assertNotNull(timeToCollision);
		assertEquals(30, timeToCollision, EPSILON);
	}
	
	@Test
	public void testGetCollisionPosition() throws ModelException {
		Ship ship1 = facade.createShip(0, 0, 2, 0, 20, 0);
		Ship ship2 = facade.createShip(100, 0, 0, 0, 20, 0);
		double[] collisionPosition = facade.getCollisionPosition(ship1, ship2);
		assertNotNull(collisionPosition);
		assertEquals(60, collisionPosition[0], EPSILON);
		assertEquals(0, collisionPosition[1], EPSILON);
	}
	
	
}
