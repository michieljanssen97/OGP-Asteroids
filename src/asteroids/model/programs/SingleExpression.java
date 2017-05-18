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
	
	public SingleExpression(Expression<?> value, String operator, SourceLocation sourceLocation) {
		super(value, sourceLocation);
		this.value = value;
		this.operator = operator;
	}
	
	private Expression<?> value;
	private String operator;
	public String getOperator() {return this.operator;}
	
	public Expression<?> getValue(){
		return this.value;
	}
	
	public void setValue(Expression<?> newValue){
		this.value = newValue;
	}
	
	public <T> Expression<?> execute(Ship ship, World world,Program program) throws FalseProgramException {
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
	
	
}
