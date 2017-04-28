package asteroids.tests.Part3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import asteroids.model.Planetoid;
import asteroids.model.World;
import asteroids.part3.facade.IFacade;
import asteroids.util.ModelException;

public class TestDiminishPlanetoid {
	
	private static final double EPSILON = 0.0001;
	IFacade facade;
	public World world;
	
	@Test
	  public void testDiminishPlanetoid1() throws Exception {
		  world = new World(500000000, 500000000);
	      Planetoid planetoid = new Planetoid(100, 120, 10,0, 50, 1000);
	      assertEquals(100, planetoid.getPositionX(),EPSILON);
	      assertEquals(120, planetoid.getPositionY(),EPSILON);
	      assertEquals(10, planetoid.getVelocityX(),EPSILON);
	      assertEquals(0, planetoid.getVelocityY(),EPSILON);
	      world.addEntity(planetoid);
	      assertTrue(world.getEntities().contains(planetoid));
	      assertEquals(1000, planetoid.getTotalTraveledDistance(), EPSILON);
	      assertEquals(50 - 1000* 0.000001, planetoid.getRadius(), EPSILON);
	      world.evolve(50,null);
	      assertEquals(1500, planetoid.getTotalTraveledDistance(), EPSILON);
	      assertEquals(50 - 1500*0.000001, planetoid.getRadius(), EPSILON);

	  }
	
	@Test 
	  public void testDiminishPlanetoid2() throws Exception {
		  world = new World(500000000, 500000000);
	      Planetoid planetoid = new Planetoid(100, 120, 1000, 0, 50, 0);
	      world.addEntity(planetoid);
	      world.evolve(50, null);
	      assertEquals(50 - 1000 * 50 * 0.000001, planetoid.getRadius(), EPSILON);
	}
	
	@Test 
	  public void testDiminishPlanetoid3() throws Exception {
		  world = new World(500000000, 500000000);
	      Planetoid planetoid = new Planetoid(100, 120, 100, 100, 50, 0);
	      world.addEntity(planetoid);
	      world.evolve(50, null);
	      assertEquals(50 - planetoid.getTotalTraveledDistance() * 0.000001, planetoid.getRadius(), EPSILON);
	}
}
