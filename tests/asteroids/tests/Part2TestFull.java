package asteroids.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import asteroids.facade.Facade;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part2.facade.IFacade;
import asteroids.util.ModelException;

public class Part2TestFull {
	
	private static final double EPSILON = 0.0001;

	IFacade facade;

	@Before
	public void setUp() {
		facade = new Facade();
	}

	@Test
	public void testBoundaryCollision() throws ModelException {
		World world = facade.createWorld(100, 100);
		Ship ship = facade.createShip(25, 75, 1, 2, 11, 0, 1.0E20);
		assertEquals(ship.getTimeToCollision(world), 14/2, EPSILON);
		
		ship = facade.createShip(50, 50, 1, 1, 11, 0, 1.0E20);
		assertEquals(ship.getTimeToCollision(world), 39, EPSILON);
	}

}
