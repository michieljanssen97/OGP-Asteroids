package asteroids.model.programs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
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
	
	public Expression<?> read(Program program) throws FalseProgramException {
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
		else return this;
	}
	
	public Expression<Double> calculateExpression(Ship ship, World world,Program program) throws FalseProgramException{
		if (!((this.getValue() instanceof Double) || (this.getValue() instanceof SingleExpression) || (this.getValue() instanceof DoubleExpression)))
			throw new FalseProgramException("Didn't receive a valid expression.");
		else if (this.getValue() instanceof Double)
			return (Expression<Double>) this;
		else if (this.getValue() instanceof SingleExpression){
			
			SingleExpression<Expression<T>> expression =  (SingleExpression<Expression<T>>)(this.getValue());

			switch (expression.getOperator()) {
            case "-":  	 	  Expression<Double> minExpression = expression.getValue().read(program).calculateExpression(ship, world, program);
					     	  return new Expression<Double>((-1)*minExpression.getValue(),getSourceLocation());
            				  
            case "sqrt": 	  Expression<Double> sqrtExpression = (expression.getValue().read(program).calculateExpression(ship, world, program) );
						 	  return new Expression<Double>(Math.sqrt(sqrtExpression.getValue()), getSourceLocation());
            			     
            case "getx": 	  Expression<Entity> getXEntity = (expression.getValue().read(program).searchEntity(world,ship, program) );
					     	  if (getXEntity.getValue() == null){
					     		  throw new FalseProgramException("Cannot calculate on a null entity");}
						 	  return new Expression<Double>((double)getXEntity.getValue().getPositionX(), getSourceLocation());
            case "gety": 	  Expression<Entity> getYEntity = (expression.getValue().read(program).searchEntity(world,ship, program) );
						 	  if (getYEntity.getValue() == null){
						 	 	  throw new FalseProgramException("Cannot calculate on a null entity");
						 	  }
						 	  return new Expression<Double>((double)getYEntity.getValue().getPositionY(), getSourceLocation());
            case "getvx":     Expression<Entity> getVXEntity = (expression.getValue().read(program).searchEntity(world,ship, program) );
							  if (getVXEntity.getValue() == null){
								  throw new FalseProgramException("Cannot calculate on a null entity");
							  }
							  return new Expression<Double>((double)getVXEntity.getValue().getVelocityX(), getSourceLocation());
            case "getvy":	  Expression<Entity> getVYEntity = (expression.getValue().read(program).searchEntity(world,ship, program) );
							  if (getVYEntity.getValue() == null){
								  throw new FalseProgramException("Cannot calculate on a null entity");
							  }
							  return new Expression<Double>((double)getVYEntity.getValue().getVelocityY(), getSourceLocation());
			case "getradius": Expression<Entity> getRadius = (expression.getValue().read(program).searchEntity(world,ship, program) );
							  if (getRadius.getValue() == null){
								  throw new FalseProgramException("Cannot calculate on a null entity");
							  }
							  return new Expression<Double>((double)getRadius.getValue().getRadius(), getSourceLocation());
			case "getdir":    return new Expression<Double>(ship.getOrientation(),getSourceLocation());
				  
			default: 		  throw new FalseProgramException("Single expression is not correct declared");
			}
		} else {
			DoubleExpression<Expression<T>> DoubleExpression = ((DoubleExpression<Expression<T>>) (this.getValue()));
			String operator = (DoubleExpression.getOperator());
		    if (operator.equals("+")) {
			    Expression<Double> left = (DoubleExpression.getLeftValue().read(program).calculateExpression(ship, world, program) );
			    Expression<Double> right = (DoubleExpression.getRightValue().read(program).calculateExpression(ship, world, program) );
			   
		    	double sum = (left.getValue()) + (right.getValue());
		    	return new Expression<Double>(sum, this.getSourceLocation());
		    }
		    else if (operator.equals("*")) {
			    Expression<Double> left = (DoubleExpression.getLeftValue().read(program).calculateExpression(ship, world, program) );
			    Expression<Double> right = (DoubleExpression.getRightValue().read(program).calculateExpression(ship, world, program) );
			   
		    	double multiplication = (left.getValue()) * (right.getValue());
		    	return new Expression<Double>(multiplication, this.getSourceLocation());
		    }
		    else throw new FalseProgramException("Impossible operand"); 
		  	
		}
	}
	@SuppressWarnings("unchecked")
	public Expression<Entity> searchEntity(World world, Ship ship, Program program) throws FalseProgramException {
		Set<Entity> allEntities = ship.getWorld().getEntities();
		if (!((this.getValue() instanceof Entity) || (this.getValue() == null) || (this.getValue() instanceof SingleExpression))){
			throw new FalseProgramException("Search for Entity impossible");
		}
		else if (this.getValue() instanceof Entity)
			return (Expression<Entity>) this;
		
		else if (this.getValue() instanceof SingleExpression) {
			SingleExpression<Expression<T>> singleExpr = (SingleExpression<Expression<T>>) this.getValue();
			if (singleExpr.getOperator().equals("null")){
				return new Expression<Entity>(null,getSourceLocation());
			}
			else if (singleExpr.getOperator().equals("self")) {
				return new Expression<Entity>(ship,getSourceLocation());
			}
			else if (singleExpr.getOperator().equals("ship")) {
				allEntities.remove(ship);
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
			}
			else if (singleExpr.getOperator().equals("asteroid")) {
				Asteroid closest = null;
				double distance = Double.POSITIVE_INFINITY;
				for(Entity e: allEntities){
					if (e instanceof Asteroid){
						if (ship.getDistanceBetween(e) < distance){
							distance = ship.getDistanceBetween(e);
							closest = (Asteroid) e;
						}
					}
				}
				return new Expression<Entity>(closest,getSourceLocation());
			}
			else if (singleExpr.getOperator().equals("planetoid")) {
				Planetoid closest = null;
				double distance = Double.POSITIVE_INFINITY;
				for(Entity e: allEntities){
					if (e instanceof Planetoid){
						if (ship.getDistanceBetween(e) < distance){
							distance = ship.getDistanceBetween(e);
							closest = (Planetoid) e;
						}
					}
				}
				return new Expression<Entity>(closest,getSourceLocation());
			}
			else if (singleExpr.getOperator().equals("planet")) {
				MinorPlanet closest = null;
				double distance = Double.POSITIVE_INFINITY;
				for(Entity e: allEntities){
					if (e instanceof MinorPlanet){
						if (ship.getDistanceBetween(e) < distance){
							distance = ship.getDistanceBetween(e);
							closest = (MinorPlanet) e;
						}
					}
				}
				return new Expression<Entity>(closest,getSourceLocation());
			}
			else if (singleExpr.getOperator().equals("any")) {
//				Entity randomEnt = null;
//				Random rd = new Random();
//				ArrayList<Entity> arrayEnt = new ArrayList<>();
//				arrayEnt.addAll(allEntities);
//				randomEnt = arrayEnt.get(rd.nextInt(arrayEnt.size()));				
				return new Expression<Entity>((allEntities.iterator().next()),getSourceLocation());
			}
			else if (singleExpr.getOperator().equals("bullet")) {
				Set<Bullet> wrongBullets = new HashSet<>();
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
			}
			else throw new FalseProgramException("False single expression (not an entity)");
		}
		else throw new FalseProgramException("Illegal searchEntity invocation");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Expression<Boolean> evaluateExpression(Ship ship, World world, Program program) throws FalseProgramException {
		if ( ! ((this.getValue() instanceof Boolean) || (this.getValue() instanceof DoubleExpression) || (this.getValue() instanceof SingleExpression)))
		    throw new FalseProgramException("Non evaluatable expression");
		
		else if (this.getValue() instanceof Boolean) {
			return (Expression<Boolean>) this;
		}
		else if (this.getValue() instanceof SingleExpression) {
			SingleExpression<Expression<T>> singleExpr = (SingleExpression<Expression<T>>)this.getValue();
			if (singleExpr.getOperator().equals("!")) {
				Expression<Boolean> bool = singleExpr.getValue().read(program).evaluateExpression(ship, world, program);
				return new Expression<Boolean>(!bool.getValue(),this.getSourceLocation());
			}
			else throw new FalseProgramException("Single expression is not correct declared"); 
		}
		else {
			DoubleExpression<Expression<T>> DoubleExpression = ((DoubleExpression<Expression<T>>) (this.getValue()));
		    String operator = (DoubleExpression.getOperator());
		    if (operator.equals("<")) {
		    	if (((null == (DoubleExpression.getLeftValue().read(program).getValue())) || (null == (DoubleExpression.getRightValue().read(program).getValue())))) {
		    		throw new FalseProgramException("null can not be a part of a comparison");
		    	}
		    	try {
			    	Expression<Double> left = (DoubleExpression.getLeftValue().read(program).calculateExpression(ship, world, program));
				    Expression<Double> right = (DoubleExpression.getRightValue().read(program).calculateExpression(ship, world, program) );
			    	boolean result = (left.getValue()) < (right.getValue());
			    	return new Expression<Boolean>(result, this.getSourceLocation());
		    	} catch (FalseProgramException e) {
		    		throw e;
		    	}
//		    	Expression<Double> left = (DoubleExpression.getLeftValue().read(program).calculateExpression(ship, world, program));
//			    Expression<Double> right = (DoubleExpression.getRightValue().read(program).calculateExpression(ship, world, program) );
//		    	boolean result = (left.getValue()) < (right.getValue());
//		    	return new Expression<Boolean>(result, this.getSourceLocation());
		    }
		    
		    else if (operator.equals("==")) {
		    	if (((null == (DoubleExpression.getLeftValue().read(program).getValue())) || (null == (DoubleExpression.getRightValue().read(program).getValue())))) {
		    		if ((null == (DoubleExpression.getLeftValue().read(program).getValue())) && (null == (DoubleExpression.getRightValue().read(program).getValue()))) {
		    			return new Expression<Boolean>(true, this.getSourceLocation());
		    		}
		    		else
		    			return new Expression<Boolean>(false, this.getSourceLocation());
		    	}
		    	try {
		    		Expression<Double> left = (DoubleExpression.getLeftValue().read(program).calculateExpression(ship, world, program));
		    		Expression<Double> right = (DoubleExpression.getRightValue().read(program).calculateExpression(ship, world, program));
		    		boolean result = (left.getValue().doubleValue()) == (right.getValue().doubleValue());
			    	return new Expression<Boolean>(result, this.getSourceLocation());
		    	} catch (FalseProgramException e) {
		    		
		    	}
		    	try {
		    		boolean result2 = DoubleExpression.getLeftValue().read(program).searchEntity(world, ship, program).getValue() == DoubleExpression.getRightValue().read(program).searchEntity(world, ship, program).getValue();
		    		return new Expression<Boolean>(result2, this.getSourceLocation());
		    	} catch (FalseProgramException e3) {
		    		
		    	}
		    	boolean result = DoubleExpression.getLeftValue().read(program).getValue() == DoubleExpression.getRightValue().read(program).getValue();
	    		return new Expression<Boolean>(result, this.getSourceLocation());	
		    }
		    else throw new FalseProgramException("Double expression is not correct declared"); 
		}
	}
}
