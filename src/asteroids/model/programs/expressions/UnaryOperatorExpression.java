package asteroids.model.programs.expressions;

import asteroids.model.Entity;
import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.BreakException;
import asteroids.model.programs.FalseProgramException;
import asteroids.model.programs.FalseReturnException;
import asteroids.model.programs.NoMoreTimeException;
import asteroids.part3.programs.SourceLocation;

public class UnaryOperatorExpression<T> extends Expression<T> {
	
	protected Expression<?> value;
	private String operator;
	
	public UnaryOperatorExpression(Expression<?> value, String operator, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.operator = operator;
		this.value = value;
	}
	
	public Expression<?> getValue() { return this.value; }
	public String getOperator() {return this.operator;}
	
	public Entity getEntityOrThrowError(Ship ship, World world, Program program, Double deltaT) throws FalseProgramException, NoMoreTimeException, BreakException, FalseReturnException {
		Entity entity = (Entity) getValue().read(ship, world, program, deltaT);
		if (entity == null){
   		  throw new FalseProgramException("Cannot calculate on a null entity");}
		else {
			return entity;
		}
	}
	
	public T read(Ship ship, World world, Program program, Double deltaT) throws FalseProgramException, NoMoreTimeException, BreakException, FalseReturnException {
		Object result = null;
		switch (getOperator()) {
	        case "-":  	 	  result = (-1) * ( (Double) getValue().read(ship, world, program, deltaT)); break;
	        case "sqrt": 	  result = Math.sqrt((Double) getValue().read(ship, world, program, deltaT)); break;
	        case "getx": 	  result = getEntityOrThrowError(ship, world, program, deltaT).getPositionX(); break;
	        case "gety": 	  result = getEntityOrThrowError(ship, world, program, deltaT).getPositionY(); break;
	        case "getvx":     result = getEntityOrThrowError(ship, world, program, deltaT).getVelocityX(); break;
	        case "getvy":	  result = getEntityOrThrowError(ship, world, program, deltaT).getVelocityY(); break;
	        case "getradius": result = getEntityOrThrowError(ship, world, program, deltaT).getRadius();	break;
	        case "getdir":    result = ship.getOrientation(); break;
			case "!": 		  result = ! ((Boolean) getValue().read(ship, world, program, deltaT)); break;
			default: 		  throw new FalseProgramException("Invalid expression");
		}
		return (T) result;
	}	
}
