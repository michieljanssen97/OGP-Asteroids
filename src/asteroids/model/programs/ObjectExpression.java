package asteroids.model.programs;

import java.util.HashSet;
import java.util.Set;

import asteroids.model.*;
import asteroids.part3.programs.SourceLocation;

public class ObjectExpression extends Expression {
	
	public ObjectExpression(Object value, SourceLocation sourceLocation) {
		super(value, sourceLocation);
	}

	public Entity getClosest(Ship ship, World world, Class<? extends Entity> type) {
		return world.getEntities(type).stream()
				.min((s1, s2) -> Double.compare(ship.getDistanceBetween(s1), ship.getDistanceBetween(s2)))
				.orElse(null);
	}

	public Object read(Ship ship, World world, Program program) throws FalseProgramException {
		switch((String) getValue()) {		
			case "null":	return null;
			case "self":	return ship;
			case "ship":	return getClosest(ship, world, Ship.class);
			case "asteroid": return getClosest(ship, world, Asteroid.class);
			case "planetoid": return getClosest(ship, world, Planetoid.class);	
			case "planet": return getClosest(ship, world, MinorPlanet.class);
			case "bullet": return getClosest(ship, world, Bullet.class);
			case "any": return getClosest(ship, world, Entity.class);
			default: return null;
		}
	}
}
