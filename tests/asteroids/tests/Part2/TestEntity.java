package asteroids.tests.Part2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Bullet;
import asteroids.model.Entity;
import asteroids.model.World;

public class TestEntity {

	private static final double EPSILON = 0.0001;
	private Entity entity;
	private World world;

	@Before
	public void setUp() {
		entity = new Bullet(10, 10, 4, 3, 2);
	}
	
	@Test
	public void testWorldAssociations() {
		world = new World(100, 100);
		assertTrue(entity.canHaveAsOwner(world));
		assertFalse(entity.isPartOfWorld());
		entity.changeOwner(world);
		assertFalse(entity.canHaveAsOwner(world));
		assertTrue(entity.isPartOfWorld());
		entity.disown();
		assertEquals(entity.getWorld(), null);
	}
	
	@Test
	public void testDistanceBetween() {
		Entity entity1 = new Bullet(10, 10, 4, 3, 2);
		Entity entity2 = new Bullet(20, 10, 4, 3, 2);
		assertEquals(10, entity1.getDistanceBetween(entity2), EPSILON);
	}
	
	@Test
	public void testOverlap() {
		Entity entity1 = new Bullet(10, 10, 4, 3, 2);
		Entity entity2 = new Bullet(9, 10, 4, 3, 2);
		assertTrue(entity1.overlap(entity2));
	}
	
	@Test
	public void testTimeToCollision() {
		Entity entity1 = new Bullet(10, 10, 1, 0, 2);
		Entity entity2 = new Bullet(20, 10, -1, 0, 2);
		assertEquals(3, entity1.getTimeToCollision(entity2), EPSILON);
	}
	
	@Test
	public void testCollisionPosition() {
		Entity entity1 = new Bullet(10, 10, 1, 0, 2);
		Entity entity2 = new Bullet(20, 10, -1, 0, 2);
		assertEquals(15, entity1.getCollisionPosition(entity2)[0], EPSILON);
		assertEquals(10, entity1.getCollisionPosition(entity2)[1], EPSILON);
	}
	
	@Test
	public void testApparentlyCollide() {
		Entity entity1 = new Bullet(8, 10, 1, 0, 2);
		Entity entity2 = new Bullet(11.98, 10, -1, 0, 2);
		assertTrue(entity1.apparentlyCollide(entity2));
	}
	
	@Test
	public void significantOverlap() {
		Entity entity1 = new Bullet(10, 10, 4, 3, 2);
		Entity entity2 = new Bullet(9, 10, 4, 3, 2);
		assertTrue(entity1.significantOverlap(entity2));
	}
	
	@Test
	public void withinBoundaries() {
		Entity entity1 = new Bullet(10, 10, 4, 3, 5);
		Entity entity2 = new Bullet(10, 10, 4, 3, 2);
		assertTrue(entity1.withinBoundaries(entity2));
		world = new World(1000, 1000);
		assertTrue(entity1.withinBoundaries(world));
	}

}
