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
		return this.left;
	}
	public Expression<?> getRightValue(){
		return this.right;
	}
	
	public void setLeftValue(Expression<?> newLeftValue){
		this.left = newLeftValue;
	}
	
	public void setRightValue(Expression<?> newRightValue){
		this.left = newRightValue;
	}
	
	public Object execute(Ship ship, World world,Program program) throws FalseProgramException{
		switch (getOperator()) {
			case "+":		return (Double) getLeftValue().read(ship, world, program) + (Double) getRightValue().read(ship, world, program);	
			case "*":		return (Double) getLeftValue().read(ship, world, program) * (Double) getRightValue().read(ship, world, program);	    	
			case "<":		return (Double) getLeftValue().read(ship, world, program) < (Double) getRightValue().read(ship, world, program);	
			case "==":		return (Double) getLeftValue().read(ship, world, program) == (Double) getRightValue().read(ship, world, program);
			default:		throw new FalseProgramException("Double expression is not correct declared");
		}
	}
}
