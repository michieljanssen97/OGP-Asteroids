package asteroids.model.programs;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part3.programs.SourceLocation;

public class DoubleExpression<T> extends Expression<T> {
	public DoubleExpression(Expression<T> left, Expression<T> right, String operator, SourceLocation sourceLocation) {
		super(null, sourceLocation);
		this.left = left;
		this.right = right;
		this.operator = operator;
	}
	
	private Expression<T> left;
	private Expression<T> right;
	private String operator;
	public String getOperator() {return this.operator;}
	
	public Expression<T> getLeftValue(){
		return this.left;
	}
	public Expression<T> getRightValue(){
		return this.right;
	}
	
	public Object read(Ship ship, World world, Program<?,?> program) throws FalseProgramException{
		switch (getOperator()) {
			case "+":		return (Double) getLeftValue().read(ship, world, program) + (Double) getRightValue().read(ship, world, program);	
			case "*":		return (Double) getLeftValue().read(ship, world, program) * (Double) getRightValue().read(ship, world, program);	    	
			case "<":		return (Double) getLeftValue().read(ship, world, program) < (Double) getRightValue().read(ship, world, program);	
			case "==":		return getLeftValue().read(ship, world, program) == getRightValue().read(ship, world, program);
			default:		throw new FalseProgramException("Double expression is not correct declared");
		}
	}
}
