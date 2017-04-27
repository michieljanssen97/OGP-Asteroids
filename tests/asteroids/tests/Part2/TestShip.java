package asteroids.tests.Part2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Bullet;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.util.ModelException;

public class TestShip {

	private static double EPSILON = 0.0001;
	private Bullet bullet;
	private Ship ship;
	private World world;

	@Before
	public void setUp() {
		ship = new Ship(100, 120, 10, 0, 50, Math.PI, 1.1E18);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateShip() throws IllegalArgumentException {
		ship = new Ship(25, 75, 1, 2, 9, 0, 1.0E20);
	}
		
	@Test
	public void testLoadBulletOnShipOverlappingBullets() throws ModelException {
		ship = new Ship(100, 120, 10, 5, 500, 0, 1.0E20);
		Bullet bullet1 = new Bullet(100, 120, 10, 5, 50);
		Bullet bullet2 = new Bullet(130, 110, 10, 5, 30);
		ship.loadBullets(bullet1);
		ship.loadBullets(bullet2);
		assertTrue(ship.getBullets().contains(bullet1));
		assertTrue(ship.getBullets().contains(bullet2));
		assertEquals(2, ship.getNbBulletsOnShip());
	}
	
	@Test
	public void testShipMove() {
		ship = new Ship(0, 0, 10, 0, 50, Math.PI, 1.1E18);
		ship.move(10);
		assertEquals(ship.getPositionX(), 100, EPSILON);
		ship = new Ship(0, 0, 10, 5, 50, Math.PI, 1.1E18);
		ship.move(5);
		assertEquals(ship.getPositionX(), 50, EPSILON);
		assertEquals(ship.getPositionY(), 25, EPSILON);
	}
	
	@Test
	public void testTurn() throws ModelException {
		ship.turn(Math.PI/2.0);
		double orientation = ship.getOrientation();
		assertNotNull(orientation);
		assertEquals(Math.PI*1.5, orientation, EPSILON);
	}
	
	@Test
	public void testEvolveShipWithActiveThruster() throws Exception, ModelException {
		world = new World(5000, 5000);
		world.addEntity(ship);
		ship.toggleThruster();
		assertEquals(1, ship.getAcceleration(), EPSILON);
		assertTrue(ship.IsThrusterActive());
		world.evolve(1,null);
		assertEquals(9, ship.getVelocityX(), EPSILON);
		assertEquals(0, ship.getVelocityY(), EPSILON);
	}
	
	@Test
	public void testMaxSpeed() throws ModelException {
		Ship ship = new Ship(0, 0, 300000, 100000, 20, 0, 23);
		double magnitude = Math.sqrt(Math.pow(ship.getVelocityX(), 2)+Math.pow(ship.getVelocityY(), 2));
		assertEquals(300000, magnitude, EPSILON);
		assertEquals(284604.9894, ship.getVelocityX(), EPSILON);
		assertEquals(94868.3298, ship.getVelocityY(), EPSILON);
	}

	@Test
	public void testThruster() throws ModelException {
		assertFalse(ship.IsThrusterActive());
		ship.toggleThruster();
		assertTrue(ship.IsThrusterActive());
	}
	
	@Test(expected = AssertionError.class)
	public void testLoadBulletsError() throws AssertionError {
		bullet = new Bullet(-2, -3434, 232, 232, 3);
		ship.loadBullets(bullet);
	}
	
	@Test
	public void testBullets() throws NullPointerException {
		world = new World(1000, 1000);
		ship = new Ship(100, 100, 10, 0, 50, Math.PI, 1.1E18);
		ship.makePartOfWorld(world);
		assertTrue(ship.isPartOfWorld());
		Bullet bullet1 = new Bullet(100, 100, 2, 2, 3);
		Bullet bullet2 = new Bullet(100, 101, 2, 2, 3);
		ship.loadBullets(bullet1);
		ship.loadBullets(bullet2);
		assertEquals(ship.getNbBulletsOnShip(), 2);
		assertTrue(ship.getBullets().contains(bullet1));
		ship.removeBullet(bullet1);
		assertFalse(ship.getBullets().contains(bullet1));
		assertEquals(ship.getNbBulletsOnShip(), 1);
		ship.fireBullet();
		assertFalse(ship.getBullets().contains(bullet2));
		assertEquals(ship.getNbBulletsOnShip(), 0);
	}
	
	@Test
	public void testBulletsEffectOnShipMass() throws NullPointerException {
		EPSILON = 1E6;
		world = new World(1000, 1000);
		ship = new Ship(100, 100, 10, 0, 50, Math.PI, 1.1E18);
		ship.makePartOfWorld(world);
		Bullet bullet1 = new Bullet(100, 100, 2, 2, 3);
		Bullet bullet2 = new Bullet(100, 101, 2, 2, 5);
		ship.loadBullets(bullet1);
		ship.loadBullets(bullet2);
		assertEquals(ship.getMass(),1.1E18+8.821592171E14+4.08407045E15,EPSILON);
		ship.removeBullet(bullet1);
		assertEquals(ship.getMass(),1.1E18+4.08407045E15,EPSILON);
	}
	
	@Test
	public void testLoadBullets() throws NullPointerException {
		ship = new Ship(0, 0, 10, 0, 50, Math.PI, 1.1E18);
		bullet = new Bullet(0, 0, 2, 2, 3);
		ship.loadBullets(bullet);
		assertTrue(ship.getBullets().contains(bullet));
	}
	
	@Test
	public void testLoadBulletsCollection() throws NullPointerException {
		ship = new Ship(100, 100, 10, 0, 50, Math.PI, 1.1E18);
		Bullet bullet1 = new Bullet(100, 100, 2, 2, 3);
		Bullet bullet2 = new Bullet(100, 101, 2, 2, 5);
		Bullet bullet3 = new Bullet(100, 102, 2, 2, 3);
		Bullet bullet4 = new Bullet(100, 103, 2, 2, 4);
		Bullet bullet5 = new Bullet(100, 104, 2, 2, 3);
		Bullet bullet6 = new Bullet(100, 105, 2, 2, 2);
		ship.loadBullets(bullet1,bullet2,bullet3,bullet4,bullet5,bullet6);
		assertEquals(ship.getNbBulletsOnShip(), 6);
		
		
	}


}
