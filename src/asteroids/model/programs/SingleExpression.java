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
	
	public Object read(Ship ship, World world,Program program) throws FalseProgramException {
		Set<Entity> allEntities = ship.getWorld().getEntities();
		switch (getOperator()) {
	        case "-":  	 	  return ((-1)* ((Double) getValue()).read(ship, world, program));
	        case "sqrt": 	  Math.sqrt(getValue().read(ship, world, program));
	        			     
	        case "getx": 	  Expression<Entity> getXEntity = (Expression<Entity>) (expression.getValue().read(ship, world, program));
					     	  if (getXEntity.getValue() == null){
					     		  throw new FalseProgramException("Cannot calculate on a null entity");}
						 	  return new Expression<Double>((double)getXEntity.getValue().getPositionX(), getSourceLocation());
	        case "gety": 	  Expression<Entity> getYEntity = (Expression<Entity>) (expression.getValue().read(ship, world, program));
						 	  if (getYEntity.getValue() == null){
						 	 	  throw new FalseProgramException("Cannot calculate on a null entity");
						 	  }
						 	  return new Expression<Double>((double)getYEntity.getValue().getPositionY(), getSourceLocation());
	        case "getvx":     Expression<Entity> getVXEntity = (Expression<Entity>) (expression.getValue().read(ship, world, program));
							  if (getVXEntity.getValue() == null){
								  throw new FalseProgramException("Cannot calculate on a null entity");
							  }
							  return new Expression<Double>((double)getVXEntity.getValue().getVelocityX(), getSourceLocation());
	        case "getvy":	  Expression<Entity> getVYEntity = (Expression<Entity>) (expression.getValue().read(ship, world, program));
							  if (getVYEntity.getValue() == null){
								  throw new FalseProgramException("Cannot calculate on a null entity");
							  }
							  return new Expression<Double>((double)getVYEntity.getValue().getVelocityY(), getSourceLocation());
			case "getradius": Expression<Entity> getRadius = (Expression<Entity>) (expression.getValue().read(ship, world, program));
							  if (getRadius.getValue() == null){
								  throw new FalseProgramException("Cannot calculate on a null entity");
							  }
							  return new Expression<Double>((double)getRadius.getValue().getRadius(), getSourceLocation());
							  
			case "getdir":    return ship.getOrientation();	
			case "!": 		  return !getValue.read(ship, world, program);
			default: 		  throw new FalseProgramException("Single expression is not correct declared");
		}
	}
	
	
}
