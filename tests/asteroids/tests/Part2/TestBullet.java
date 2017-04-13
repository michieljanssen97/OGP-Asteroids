package asteroids.tests.Part2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asteroids.facade.Facade;
import asteroids.model.Bullet;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part2.facade.IFacade;
import asteroids.util.ModelException;

public class TestBullet {

	private static final double EPSILON = 0.0001;
	private Bullet bullet;
	private Ship ship;
	private World world;

	@Before
	public void setUp() {
		bullet = new Bullet(10, 10, 4, 3, 2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateBullet() throws IllegalArgumentException {
		Bullet bullet = new Bullet(10, 10, 4, 3, -1);
	}
	
	@Test
	public void testBulletCounter() {
		assertEquals(bullet.getCounter(), 0);
		assertFalse(bullet.Counter()); // First collision
		assertFalse(bullet.Counter()); // Second collision
		assertTrue(bullet.Counter()); // Third collision
	}
	
	@Test
	public void testBulletMove() {
		bullet = new Bullet(0, 0, 0, 3, 2);
		bullet.move(10);
		assertEquals(bullet.getPositionY(), 30, EPSILON);
		bullet = new Bullet(0, 0, 6, 3, 2);
		bullet.move(5);
		assertEquals(bullet.getPositionX(), 30, EPSILON);
		assertEquals(bullet.getPositionY(), 15, EPSILON);
	}
	
	@Test
	public void testBulletAssociations() {
		ship = new Ship(25, 75, 1, 2, 11, 0, 1.0E20);
		world = new World(100, 100);
		assertTrue(bullet.canBePartOfShip() && bullet.canBePartOfWorld());
		bullet.makePartOfShip(ship);
		assertFalse(bullet.canBePartOfShip() && bullet.canBePartOfWorld());
		assertTrue(bullet.isPartOfShip());
		bullet.removeFromShip();
		assertEquals(bullet.getShip(), null);
		bullet.makePartOfWorld(world);
		assertTrue(bullet.isPartOfWorld());
	}
	
	

}
