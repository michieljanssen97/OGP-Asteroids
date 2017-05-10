package asteroids.model.programs;

import asteroids.model.Entity;
import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
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
	
	@SuppressWarnings("unchecked")
	public Expression<?> read(Program program) throws FalseProgramException {
		if (this.getValue() instanceof Variable) {
			Variable<T> variable = (Variable<T>)this.getValue();
			if (variable.getVariableType() == Type.Boolean) {
				return new Expression<T>(variable.getVariableType(),getSourceLocation());
			}
			else if (variable.getVariableType() == Type.Entity) {
				return new Expression<T>(variable.getVariableType(),getSourceLocation());
			}
			else if (variable.getVariableType() == Type.Double) {
				return new Expression<T>(variable.getVariableType(),getSourceLocation());
			}
			else if (variable.getVariableType() == null) {
				return new Expression<T>(null, getSourceLocation());
			}
			else throw new FalseProgramException("No such type");
			
		}
		else return this;
	}
	
	@SuppressWarnings("unchecked")
	public Expression<Double> calculationExpression(Entity entity, World world,Program program) throws FalseProgramException{
		if (!((this.getValue() instanceof Double) || (this.getValue() instanceof SingleExpression) || (this.getValue() instanceof DoubleExpression)))
			throw new FalseProgramException("Didn't receive a valid expression.");
		else if (this.getValue() instanceof Double)
			return (Expression<Double>) this;
		else if (this.getValue() instanceof SingleExpression){
			SingleExpression<Expression<T>> expression =  (SingleExpression<Expression<T>>)(this.getValue());
			if (expression.getOperator().equals("-")){
				Expression<Double> minExpression = expression.getValue().read(program).calculationExpression(entity, world, program);
				return new Expression<Double>((-1)*minExpression.getValue(),getSourceLocation());
			}
			if (expression.getOperator().equals("sqrt")){
				Expression<Double> sqrtExpression = (expression.getValue().read(program).calculationExpression(entity, world, program) );
				return new Expression<Double>(Math.sqrt(sqrtExpression.getValue()), getSourceLocation());
			}
			if (expression.getOperator().equals("getx")) {
				Expression<Entity> getXEntity = (expression.getValue().read(program).searchEntity(world,entity, program) );
				return new Expression<Double>((double)getXEntity.getValue().getPositionX(), getSourceLocation());
			}
			if (expression.getOperator().equals("gety")) {
				Expression<Entity> getYEntity = (expression.getValue().read(program).searchEntity(world,entity, program) );
				return new Expression<Double>((double)getYEntity.getValue().getPositionY(), getSourceLocation());
			}
			if (expression.getOperator().equals("getvx")) {
				Expression<Entity> getVXEntity = (expression.getValue().read(program).searchEntity(world,entity, program) );
				return new Expression<Double>((double)getVXEntity.getValue().getVelocityX(), getSourceLocation());
			}
			if (expression.getOperator().equals("getvy")) {
				Expression<Entity> getVYEntity = (expression.getValue().read(program).searchEntity(world,entity, program) );
				return new Expression<Double>((double)getVYEntity.getValue().getVelocityY(), getSourceLocation());
			}
			if (expression.getOperator().equals("getradius")) {
				Expression<Entity> getRadius = (expression.getValue().read(program).searchEntity(world,entity, program) );
				return new Expression<Double>((double)getRadius.getValue().getRadius(), getSourceLocation());
			}
			else throw new FalseProgramException("Single expression is not correct declared");

		}
		else {
			DoubleExpression<Expression<T>,T> DoubleExpression = ((DoubleExpression<Expression<T>, T>) (this.getValue()));
		    Expression<Double> left = (DoubleExpression.getLeftValue().read(program).calculationExpression(entity, world, program) );
		    Expression<Double> right = (DoubleExpression.getRightValue().read(program).calculationExpression(entity, world, program) );
		    String operator = (DoubleExpression.getOperator());
		    if (operator.equals("+")) {
		    	double sum = (left.getValue()) + (right.getValue());
		    	return new Expression<Double>(sum, this.getSourceLocation());
		    }
		    else if (operator.equals("*")) {
		    	double multiplication = (left.getValue()) * (right.getValue());
		    	return new Expression<Double>(multiplication, this.getSourceLocation());
		    }
		    else throw new FalseProgramException("Impossible operand"); 
		  	
		}
	}
	@SuppressWarnings("unchecked")
	public Expression<Entity> searchEntity(World world, Entity entity, Program program) throws FalseProgramException {
		if (!((this.getValue() instanceof Entity) || (this.getValue() == null))){
			throw new FalseProgramException("search for Creature impossible");
		}
		else {
			return new Expression<Entity>((Entity)this.getValue(),this.getSourceLocation());
		}		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Expression<Boolean> evaluateExpression(Entity entity, World world, Program program) throws FalseProgramException {
		if ( ! ((this.getValue() instanceof Boolean) || (this.getValue() instanceof DoubleExpression) || (this.getValue() instanceof SingleExpression)))
		    throw new FalseProgramException("Non evaluatable expression");
		
		else if (this.getValue() instanceof Boolean) {
			return (Expression<Boolean>) this;
		}
		else if (this.getValue() instanceof SingleExpression) {
			SingleExpression<Expression<T>> singleExpr = (SingleExpression<Expression<T>>)this.getValue();
			if (singleExpr.getOperator().equals("!")) {
				Expression<Boolean> bool = singleExpr.getValue().read(program).evaluateExpression(entity, world, program);
				return new Expression<Boolean>(!bool.getValue(),this.getSourceLocation());
			}
			else throw new FalseProgramException("Single expression is not correct declared"); 
		}
		else {
			DoubleExpression<Expression<T>,T> DoubleExpression = ((DoubleExpression<Expression<T>, T>) (this.getValue()));
		    String operator = (DoubleExpression.getOperator());
		    if (operator.equals("<")) {
		    	Expression<Double> left = (DoubleExpression.getLeftValue().read(program).calculationExpression(entity, world, program));
			    Expression<Double> right = (DoubleExpression.getRightValue().read(program).calculationExpression(entity, world, program) );
		    	boolean result = (left.getValue()) < (right.getValue());
		    	return new Expression<Boolean>(result, this.getSourceLocation());
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
		    		Expression<Double> left = (DoubleExpression.getLeftValue().read(program).calculationExpression(entity, world, program));
		    		Expression<Double> right = (DoubleExpression.getRightValue().read(program).calculationExpression(entity, world, program));
		    		boolean result = (left.getValue().doubleValue()) == (right.getValue().doubleValue());
			    	return new Expression<Boolean>(result, this.getSourceLocation());
		    	} catch (FalseProgramException e) {
		    		
		    	}
		    	try {
		    		boolean result2 = DoubleExpression.getLeftValue().read(program).searchEntity(world, entity, program).getValue() == DoubleExpression.getRightValue().read(program).searchEntity(world, entity, program).getValue();
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
