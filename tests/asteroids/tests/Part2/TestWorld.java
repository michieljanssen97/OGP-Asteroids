package asteroids.tests.Part2;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import asteroids.facade.Facade;
import asteroids.model.Bullet;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part2.facade.IFacade;
import asteroids.util.ModelException;

public class TestWorld {
	
	private static final double EPSILON = 0.0001;
	private Bullet bullet;
	private Ship ship;
	private World world;

	@Before
	public void setUp() {
		world = new World(100, 100);
	}
	
	@Test
	public void testCreateWorld() throws IllegalArgumentException {
		World world = new World(10, -10);
		assertEquals(world.getHeight(), 1000, EPSILON);
	}

	@Test
	public void testBoundaryCollision() throws ModelException {
		Ship ship = new Ship(25, 75, 1, 2, 11, 0, 1.0E20);
		assertEquals(ship.getTimeToCollision(world), 14/2, EPSILON);
		
		ship = new Ship(50, 50, 0, -1, 11, 0, 1.0E20);
		assertEquals(ship.getTimeToCollision(world), 39, EPSILON);
		
		ship = new Ship(50, 50, -1, 2, 11, 0, 1.0E20);
		assertEquals(ship.getTimeToCollision(world), 19.5, EPSILON);
		
		ship = new Ship(50, 50, -1, -2, 11, 0, 1.0E20);
		assertEquals(ship.getTimeToCollision(world), 19.5, EPSILON);
		
		ship = new Ship(50, 50, 1, -2, 11, 0, 1.0E20);
		assertEquals(ship.getTimeToCollision(world), 19.5, EPSILON);
		
		ship = new Ship(40, 50, -10, -2, 11, 0, 1.0E20);
		assertEquals(ship.getTimeToCollision(world), 2.9, EPSILON);
	}
	
	
	@Test
	public void testAddEntity() throws IllegalArgumentException {
		Ship ship = new Ship(25, 75, 1, 2, 11, 0, 1.0E20);
		world.addEntity(ship);
		assertTrue(world.getEntities().contains(ship));
		assertTrue(ship.isPartOfWorld());
		
		Bullet bullet = new Bullet(24, 4, 1, 2, 3);
		world.addEntity(bullet);
		assertTrue(world.getEntities().contains(bullet));
		assertTrue(bullet.isPartOfWorld());
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void testAddEntityError() throws IllegalArgumentException {
		Ship ship = new Ship(-25, 75, 1, 2, 11, 0, 1.0E20);
		world.addEntity(ship);
	}
	
	@Test
	public void RemoveEntity() {
		Bullet bullet = new Bullet(24, 4, 1, 2, 3);
		world.addEntity(bullet);
		
		world.removeEntity(bullet);
		assertFalse(bullet.isPartOfWorld());
		assertFalse(world.getEntities().contains(bullet));
	}
	
	@Test
	public void testNextCollisionTime() {
		Bullet bullet1 = new Bullet(10, 5, 1, 0, 2);
		Bullet bullet2 = new Bullet(20, 5, -1, 0, 2);
		world.addEntity(bullet1);
		world.addEntity(bullet2);
		assertEquals(3, world.getNextCollisionTime(), EPSILON);
	}
	
	@Test
	public void testNextCollisionPosition() {
		Bullet bullet1 = new Bullet(10, 5, 1, 0, 2);
		Bullet bullet2 = new Bullet(20, 5, -1, 0, 2);
		world.addEntity(bullet1);
		world.addEntity(bullet2);
		assertEquals(15, world.getNextCollisionPosition()[0], EPSILON);
		assertEquals(5, world.getNextCollisionPosition()[1], EPSILON);
	}
	
	@Test
	public void testNextCollisionObjects() {
		Bullet bullet1 = new Bullet(10, 5, 1, 0, 2);
		Bullet bullet2 = new Bullet(20, 5, -1, 0, 2);
		world.addEntity(bullet1);
		world.addEntity(bullet2);
		assertTrue(Arrays.asList(world.getNextCollisionObjects()).contains(bullet1));
		assertTrue(Arrays.asList(world.getNextCollisionObjects()).contains(bullet2));
	}
	
	@Test
	public void testNoNextCollisionNull() {
		assertEquals(null, world.getNextCollisionTime());
		assertEquals(null, world.getNextCollisionPosition());
		assertEquals(null, world.getNextCollisionObjects());
	}
	
	@Test
	public void testEntityAtPosition() {
		Bullet bullet1 = new Bullet(10, 5, 1, 0, 2);
		world.addEntity(bullet1);
		assertEquals(bullet1, world.getEntityAtPosition(10, 5));
		assertEquals(null, world.getEntityAtPosition(123, 4));
	}
	
	@Test
	public void testSignificantOverlap() {
		Bullet bullet1 = new Bullet(10, 5, 1, 0, 2);
		Bullet bullet2 = new Bullet(10, 6, -1, 0, 2);
		Bullet bullet3 = new Bullet(20, 26, -1, 0, 2);
		world.addEntity(bullet1);
		assertTrue(world.significantOverlap(bullet2));
		assertFalse(world.significantOverlap(bullet3));

	}
	
	@Test
	public void testEvolve() throws Exception {
		
		// Entity collide with wall
		world = new World(100, 100);
		Ship ship = new Ship(21, 75, -1, 0, 20, 0, 1.0E20);
		world.addEntity(ship);
		assertEquals(1, world.getNextCollisionTime(), EPSILON);
		world.evolve(23.0);
		assertEquals(ship.getPositionX(), 42, EPSILON);
		assertEquals(ship.getPositionY(), 75, EPSILON);
		
		// Ship Collide with ship
		world = new World(100, 100);
		Ship ship1 = new Ship(21, 75, 1, 0, 20, 0, 1.0E20); 
		Ship ship2 = new Ship(75, 75, -1, 0, 20, 0, 1.0E20); 
		world.addEntity(ship1);
		world.addEntity(ship2);
		assertEquals(7, world.getNextCollisionTime(), EPSILON);
		world.evolve(20.0);
		assertEquals(25, ship1.getPositionX(), EPSILON);
		assertEquals(79, ship2.getPositionX(), EPSILON);
		
		// Ship collide with bullet
		// ...
	}
	


}
