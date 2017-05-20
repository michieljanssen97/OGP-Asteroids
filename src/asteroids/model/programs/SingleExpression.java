package asteroids.model.programs;

import java.util.HashSet;
import java.util.Set;

import asteroids.model.Asteroid;
import asteroids.model.Bullet;
import asteroids.model.Entity;
import asteroids.model.MinorPlanet;
import asteroids.model.Planetoid;
import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part3.programs.SourceLocation;

public class SingleExpression<E> extends Expression {
	
	public SingleExpression(Expression value, String operator, SourceLocation sourceLocation) {
		super(value, sourceLocation);
		this.operator = operator;
	}
	
	private String operator;
	public String getOperator() {return this.operator;}
	
	public Entity getEntityOrThrowError(Ship ship, World world, Program program) {
		Entity entity = getValue().read(ship, world, program);
		if (entity == null){
   		  throw new FalseProgramException("Cannot calculate on a null entity");}
		else {
			return entity;
		}
	}
	
	public Object read(Ship ship, World world,Program program) throws FalseProgramException {
		Set<Entity> allEntities = ship.getWorld().getEntities();
		switch (getOperator()) {
	        case "-":  	 	  return ((-1)* ((Double) getValue()).read(ship, world, program));
	        case "sqrt": 	  Math.sqrt(getValue().read(ship, world, program));
	        case "getx": 	  return getEntityOrThrowError(ship, world, program).getPositionX();
	        case "gety": 	  return getEntityOrThrowError(ship, world, program).getPositionY();
	        case "getvx":     return getEntityOrThrowError(ship, world, program).getVelocityX();
	        case "getvy":	  return getEntityOrThrowError(ship, world, program).getVelocityY();
			case "getradius": return getEntityOrThrowError(ship, world, program).getRadius();				  
			case "getdir":    return ship.getOrientation();	
			case "!": 		  return !getValue.read(ship, world, program);
			default: 		  throw new FalseProgramException("Invalid expression");
		}
	}
	
	
}
