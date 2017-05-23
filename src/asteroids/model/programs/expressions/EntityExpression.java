package asteroids.model.programs.expressions;

import asteroids.model.*;
import asteroids.model.programs.FalseProgramException;
import asteroids.part3.programs.SourceLocation;

public class EntityExpression<T> extends Expression<T> {
	
	String objectName;
	
	public EntityExpression(String objectName, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.objectName = objectName;
	}

	public Entity getClosest(Ship ship, World world, Class<? extends Entity> type) {
		return world.getEntities(type).stream()
				.filter(entity -> entity != ship)
				.min((s1, s2) -> Double.compare(ship.getDistanceBetween(s1), ship.getDistanceBetween(s2)))
				.orElse(null);
	}

	@SuppressWarnings("unchecked")
	public T read(Ship ship, World world, Program program, Double deltaT) throws FalseProgramException {
		switch(objectName) {		
			case "null":	return null;
			case "self":	return (T) ship;
			case "ship":	return (T) getClosest(ship, world, Ship.class);
			case "asteroid": return (T) getClosest(ship, world, Asteroid.class);
			case "planetoid": return (T) getClosest(ship, world, Planetoid.class);	
			case "planet": return (T) getClosest(ship, world, MinorPlanet.class);
			case "bullet": return (T) world.getEntities(Bullet.class).stream()
										.filter(bullet -> bullet.getSource() == ship)
										.findFirst()
										.orElse(null);
			case "any": return (T) world.getEntities().stream()
										.findAny()
										.orElse(null);
			default: throw new FalseProgramException("Invalid single expression");
		}
	}
}
