package asteroids.model.programs;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part3.programs.SourceLocation;

public class DoubleExpression<E> extends Expression {
	public DoubleExpression(Expression<?> left, Expression<?> right, String operator, SourceLocation sourceLocation) {
		super(null, sourceLocation);
		this.left = left;
		this.right = right;
		this.operator = operator;
	}
	
	private Expression<?> left;
	private Expression<?> right;
	private String operator;
	public String getOperator() {return this.operator;}
	
	public Expression<?> getLeftValue(){
		return (this.left);
	}
	public Expression<?> getRightValue(){
		return (this.right);
	}
	
	public void setLeftValue(Expression<?> newLeftValue){
		this.left = newLeftValue;
	}
	
	public void setRightValue(Expression<?> newRightValue){
		this.left = newRightValue;
	}
	
	public <T> Expression<?> execute(Ship ship, World world,Program program) throws FalseProgramException{
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
