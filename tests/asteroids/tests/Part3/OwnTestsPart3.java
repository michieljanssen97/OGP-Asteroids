package asteroids.tests.Part3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Bullet;
import asteroids.model.Entity;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.util.ModelException;

public class OwnTestsPart3 {
	
	private static double EPSILON = 0.0001;
	private Bullet bullet;
	private Ship ship;
	private World world;
	private Entity entity;
	

	@Before
	public void setUp() {
		ship = new Ship(100, 120, 10, 0, 50, Math.PI, 1.1E18);
		bullet = new Bullet(10, 10, 4, 3, 2);
		entity = new Bullet(10, 10, 4, 3, 2);
		world = new World(100, 100);

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateBullet() throws IllegalArgumentException {
		bullet = new Bullet(10, 10, 4, 3, -1);
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
		assertTrue(bullet.canHaveAsOwner(ship) && bullet.canHaveAsOwner(world));
		bullet.changeOwner(ship);
		assertFalse(bullet.canHaveAsOwner(ship) && bullet.canHaveAsOwner(world));
		assertTrue(bullet.isPartOfShip());
		bullet.removeFromShip();
		assertEquals(bullet.getShip(), null);
		bullet.changeOwner(world);
		assertTrue(bullet.isPartOfWorld());
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
		ship.changeOwner(world);
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
		ship.changeOwner(world);
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
	public void testBoundaryCollisionPosition() throws ModelException {
		// bovenste muur
		Ship ship = new Ship(25, 75, 1, 2, 11, 0, 1.0E20);
		assertEquals(ship.getTimeToCollision(world), 14/2, EPSILON);
		assertEquals(ship.getCollisionPosition(world)[0],32,EPSILON);
		assertEquals(ship.getCollisionPosition(world)[1],100,EPSILON);
		// onderste muur
		Ship ship1 = new Ship(25, 75, 1, -2, 11, 0, 1.0E20);
		assertEquals(ship1.getTimeToCollision(world),32, EPSILON);
		assertEquals(ship1.getCollisionPosition(world)[0],57,EPSILON);
		assertEquals(ship1.getCollisionPosition(world)[1],0,EPSILON);
		// linkse muur 
		Ship ship2 = new Ship(25, 75, -2, -0.5, 11, 0, 1.0E20);
		assertEquals(ship2.getTimeToCollision(world),7, EPSILON);
		assertEquals(ship2.getCollisionPosition(world)[0],0,EPSILON);
		assertEquals(ship2.getCollisionPosition(world)[1],75-3.5,EPSILON);
		// rechtse muur 
		Ship ship3 = new Ship(25, 75, 3, -0.5, 11, 0, 1.0E20);
		assertEquals(ship3.getTimeToCollision(world),64/3.0, EPSILON);
		assertEquals(ship3.getCollisionPosition(world)[0],100,EPSILON);
		assertEquals(ship3.getCollisionPosition(world)[1],75-(0.5*(64/3.0)),EPSILON);
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
	
	@SuppressWarnings("deprecation")
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
	public void testDestroyWorld() throws Exception {
		world = new World(100, 100);
		Ship ship = new Ship(75, 75, -1, 0, 20, 0, 1.0E20);
		Bullet bullet1 = new Bullet(10, 5, 1, 0, 2);
		world.addEntity(ship);
		world.addEntity(bullet1);
		world.terminate();
		assertFalse(world.getEntities().contains(ship));
		assertFalse(world.getEntities().contains(bullet1));
		assertFalse(ship.isTerminated());
		assertFalse(bullet1.isTerminated());
		assertTrue(world.isTerminated());
		ship.move(1.0);
		bullet1.move(1.0);
		assertEquals(74, ship.getPositionX(),EPSILON);
		assertEquals(11, bullet1.getPositionX(),EPSILON);
	}
	
	@Test
	public void testEvolve() throws Exception {
		
		// Entity collide with wall
		world = new World(100, 100);
		Ship ship = new Ship(21, 75, -1, 0, 20, 0, 1.0E20);
		world.addEntity(ship);
		assertEquals(1, world.getNextCollisionTime(), EPSILON);
		world.evolve(23.0, null);
		assertEquals(ship.getPositionX(), 42, EPSILON);
		assertEquals(ship.getPositionY(), 75, EPSILON);
		
		// Ship Collide with ship
		world = new World(100, 100);
		Ship ship1 = new Ship(21, 75, 1, 0, 20, 0, 1.0E20); 
		Ship ship2 = new Ship(75, 75, -1, 0, 20, 0, 1.0E20); 
		world.addEntity(ship1);
		world.addEntity(ship2);
		assertEquals(7, world.getNextCollisionTime(), EPSILON);

		world.evolve(7.0,null);
		assertEquals(28, ship1.getPositionX(), EPSILON);
		assertEquals(68, ship2.getPositionX(), EPSILON);
		world.evolve(10.0,null);
		assertEquals(22, ship1.getPositionX(), EPSILON);
		assertEquals(78, ship2.getPositionX(), EPSILON);
		
		// Ship collide with bullet
		world = new World(100, 100);
		Ship ship3 = new Ship(21, 75, 1, 0, 20, 0, 1.0E20);
		Bullet bullet1 = new Bullet(80, 75, -1, 0, 2);
		world.addEntity(ship3);
		world.addEntity(bullet1);
		assertEquals(18.5, ship3.getTimeToCollision(bullet1), EPSILON);
		world.evolve(18.5,null);
		assertTrue(ship3.isTerminated());
		assertTrue(bullet1.isTerminated());

	}
	
	

}
