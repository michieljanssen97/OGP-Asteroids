package asteroids.model.programs.expressions;

import asteroids.model.Entity;
import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.FalseProgramException;
import asteroids.part3.programs.SourceLocation;

public class SingleExpression<T> extends Expression<T> {
	
	public SingleExpression(Expression<T> value, String operator, SourceLocation sourceLocation) {
		super(value, sourceLocation);
		this.operator = operator;
	}
	
	private String operator;
	public String getOperator() {return this.operator;}
	
	public Entity getEntityOrThrowError(Ship ship, World world, Program<?,?> program) throws FalseProgramException {
		Entity entity = (Entity) getValue().read(ship, world, program);
		if (entity == null){
   		  throw new FalseProgramException("Cannot calculate on a null entity");}
		else {
			return entity;
		}
	}
	
	public T read(Ship ship, World world, Program<?,?> program) throws FalseProgramException {
		Object result = null;
		switch (getOperator()) {
	        case "-":  	 	  result = (-1) * ( (Double) getValue().read(ship, world, program)); break;
	        case "sqrt": 	  result = Math.sqrt((Double) getValue().read(ship, world, program)); break;
	        case "getx": 	  result = getEntityOrThrowError(ship, world, program).getPositionX(); break;
	        case "gety": 	  result = getEntityOrThrowError(ship, world, program).getPositionY(); break;
	        case "getvx":     result = getEntityOrThrowError(ship, world, program).getVelocityX(); break;
	        case "getvy":	  result = getEntityOrThrowError(ship, world, program).getVelocityY(); break;
	        case "getradius": result = getEntityOrThrowError(ship, world, program).getRadius();	break;
	        case "getdir":    result = ship.getOrientation(); break;
			case "!": 		  result = ! ((Boolean) getValue().read(ship, world, program)); break;
			default: 		  throw new FalseProgramException("Invalid expression");
		}
		return (T) result;
	}	
}
