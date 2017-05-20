package asteroids.model.programs;

import asteroids.model.*;
import asteroids.part3.programs.SourceLocation;

public class ObjectExpression<T> extends Expression<T> {
	
	String objectName;
	
	public ObjectExpression(String objectName, SourceLocation sourceLocation) {
		super(null, sourceLocation);
		this.objectName = objectName;
	}

	public Entity getClosest(Ship ship, World world, Class<? extends Entity> type) {
		return world.getEntities(type).stream()
				.min((s1, s2) -> Double.compare(ship.getDistanceBetween(s1), ship.getDistanceBetween(s2)))
				.orElse(null);
	}

	public T read(Ship ship, World world, Program<?,?> program) throws FalseProgramException {
		switch(objectName) {		
			case "null":	return (T) null;
			case "self":	return (T) ship;
			case "ship":	return (T) getClosest(ship, world, Ship.class);
			case "asteroid": return (T) getClosest(ship, world, Asteroid.class);
			case "planetoid": return (T) getClosest(ship, world, Planetoid.class);	
			case "planet": return (T) getClosest(ship, world, MinorPlanet.class);
			case "bullet": return (T) getClosest(ship, world, Bullet.class);
			case "any": return (T) getClosest(ship, world, Entity.class);
			default: return null;
		}
	}
}
