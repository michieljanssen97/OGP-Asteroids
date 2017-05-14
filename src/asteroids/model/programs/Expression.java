package asteroids.model.programs;

import java.util.HashSet;
import java.util.Set;

import asteroids.model.*;
import asteroids.part3.programs.SourceLocation;

public class Expression<T> {
	
	public Expression(T value,SourceLocation sourceLocation) {
		this.value = value;
		this.sourceLocation = sourceLocation;
	}
	protected T value;
	public T getValue() { return this.value; }
	public void setValue(T value) { this.value = value; }
	private SourceLocation sourceLocation;
	public SourceLocation getSourceLocation() { return this.sourceLocation; }
	
	public Expression<?> read(Ship ship, World world, Program program) throws FalseProgramException {
		if (this.getValue() instanceof String) {
			if (program.getDoubleVariables().get(this.getValue()) != null){
				return new Expression<Double>((Double)program.getDoubleVariables().get(this.getValue()),getSourceLocation());
			}
			else if (program.getBooleanVariables().get(this.getValue()) != null){
				return new Expression<Boolean>((Boolean)program.getDoubleVariables().get(this.getValue()),getSourceLocation());
			}
			
			else if (program.getEntityVariables().get(this.getValue()) != null){
				return new Expression<Entity>((Entity)program.getDoubleVariables().get(this.getValue()),getSourceLocation());
			}
			else throw new FalseProgramException("No such type");
			
		}
		else if (this.getValue() instanceof SingleExpression) {
			return executeSingleExpression(ship, world, program);
		}
		else if (this.getValue() instanceof DoubleExpression) {
			return executeDoubleExpression(ship, world, program);
		}
		else return this;

	}
	
	public Expression<?> executeSingleExpression(Ship ship, World world,Program program) throws FalseProgramException {
		SingleExpression<Expression<T>> expression =  (SingleExpression<Expression<T>>)(this.getValue());
		Set<Entity> allEntities = ship.getWorld().getEntities();
		switch (expression.getOperator()) {
        case "-":  	 	  Expression<Double> minExpression = (Expression<Double>) expression.getValue().read(ship, world, program);
				     	  return new Expression<Double>((-1)*minExpression.getValue(),getSourceLocation());
        				  
        case "sqrt": 	  Expression<Double> sqrtExpression = (Expression<Double>) (expression.getValue().read(ship, world, program));
					 	  return new Expression<Double>(Math.sqrt(sqrtExpression.getValue()), getSourceLocation());
        			     
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
		case "getdir":    return new Expression<Double>(ship.getOrientation(),getSourceLocation());
		
		case "null":	return new Expression<Entity>(null,getSourceLocation());
		
		case "self":	return new Expression<Entity>(ship,getSourceLocation());
		
		case "ship":	allEntities.remove(ship);
						Ship closest = null;
						double distance = Double.POSITIVE_INFINITY;
						for(Entity e: allEntities){
							if (e instanceof Ship){
								if (ship.getDistanceBetween(e) < distance){
									distance = ship.getDistanceBetween(e);
									closest = (Ship) e;
								}
							}
						}
						return new Expression<Entity>(closest,getSourceLocation());
						
		case "asteroid":	Asteroid closestA = null;
							double distanceA = Double.POSITIVE_INFINITY;
							for(Entity e: allEntities){
								if (e instanceof Asteroid){
									if (ship.getDistanceBetween(e) < distanceA){
										distanceA = ship.getDistanceBetween(e);
										closestA = (Asteroid) e;
									}
								}
							}
							return new Expression<Entity>(closestA,getSourceLocation());
							
		case "planetoid":	Planetoid closestPL = null;
							double distancePL = Double.POSITIVE_INFINITY;
							for(Entity e: allEntities){
								if (e instanceof Planetoid){
									if (ship.getDistanceBetween(e) < distancePL){
										distancePL = ship.getDistanceBetween(e);
										closestPL = (Planetoid) e;
									}
								}
							}
							return new Expression<Entity>(closestPL,getSourceLocation());
							
		case "planet":		MinorPlanet closestP = null;
							double distanceP = Double.POSITIVE_INFINITY;
							for(Entity e: allEntities){
								if (e instanceof MinorPlanet){
									if (ship.getDistanceBetween(e) < distanceP){
										distanceP = ship.getDistanceBetween(e);
										closestP = (MinorPlanet) e;
									}
								}
							}
							return new Expression<Entity>(closestP,getSourceLocation());
							
		case "any":			return new Expression<Entity>((allEntities.iterator().next()),getSourceLocation());
		
		case "bullet":		Set<Bullet> wrongBullets = new HashSet<>();
							for (Entity e: allEntities){
								if (e instanceof Bullet){
									if (!(((Bullet) e).getSource() == ship)){
										wrongBullets.add((Bullet) e);
									}
								}
							}
							allEntities.removeAll(wrongBullets);
							allEntities.removeAll(ship.getBullets());
							Bullet firedBullet = null;
							for(Entity e: allEntities){
								if (e instanceof Bullet){
									firedBullet = (Bullet) e;
									break;
								}
							}
							return new Expression<Entity>(firedBullet,getSourceLocation());
							
		case "!": 			Expression<Boolean> bool = (Expression<Boolean>) expression.getValue().read(ship, world, program);
							return new Expression<Boolean>(!bool.getValue(),this.getSourceLocation());
			  
		default: 		  throw new FalseProgramException("Single expression is not correct declared");
		}
	}
	
	public Expression<?> executeDoubleExpression(Ship ship, World world,Program program) throws FalseProgramException{
		DoubleExpression<Expression<T>> doubleExpression = ((DoubleExpression<Expression<T>>) (this.getValue()));
		switch (doubleExpression.getOperator()) {
		
		case "+":		Expression<Double> left = (Expression<Double>) (doubleExpression.getLeftValue().read(ship, world, program));
					    Expression<Double> right = (Expression<Double>) (doubleExpression.getRightValue().read(ship, world, program));	   
				    	double sum = (left.getValue()) + (right.getValue());
				    	return new Expression<Double>(sum, this.getSourceLocation());
				    	
		case "*":		Expression<Double> left2 = (Expression<Double>) (doubleExpression.getLeftValue().read(ship, world, program));
	    				Expression<Double> right2 = (Expression<Double>) (doubleExpression.getRightValue().read(ship, world, program));	   
				    	double multiplication = (left2.getValue()) * (right2.getValue());
				    	return new Expression<Double>(multiplication, this.getSourceLocation());
				    	
		case "<":		Expression<Double> left3 = (Expression<Double>) (doubleExpression.getLeftValue().read(ship, world, program));	
	    				Expression<Double> right3 = (Expression<Double>) (doubleExpression.getRightValue().read(ship, world, program));	
	    				boolean result3 = (left3.getValue()) < (right3.getValue());
	    				return new Expression<Boolean>(result3, this.getSourceLocation());
			
		case "==":		Expression<?> left4 = (Expression<?>) (doubleExpression.getLeftValue().read(ship, world, program));
						Expression<?> right4 = (Expression<?>) (doubleExpression.getRightValue().read(ship, world, program));
						boolean result4 = (left4.getValue()) == (right4.getValue());
				    	return new Expression<Boolean>(result4, this.getSourceLocation());
		
		default:		throw new FalseProgramException("Double expression is not correct declared");
			
		}
	}
}
