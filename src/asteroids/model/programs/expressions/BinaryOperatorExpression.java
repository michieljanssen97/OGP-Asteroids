package asteroids.model.programs.expressions;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.BreakException;
import asteroids.model.programs.FalseProgramException;
import asteroids.model.programs.FalseReturnException;
import asteroids.model.programs.NoMoreTimeException;
import asteroids.part3.programs.SourceLocation;

public class BinaryOperatorExpression<T> extends Expression<T> {
	
	private Expression<T> left;
	private Expression<T> right;
	private String operator;
	
	public BinaryOperatorExpression(Expression<T> left, Expression<T> right, String operator, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.left = left;
		this.right = right;
		this.operator = operator;
	}
	
	public String getOperator() {
		return this.operator;
	}
	
	public Expression<T> getLeftValue(){
		return this.left;
	}
	public Expression<T> getRightValue(){
		return this.right;
	}
	
	public T read(Ship ship, World world, Program program, Double deltaT) throws FalseProgramException, NoMoreTimeException, BreakException, FalseReturnException{
		Object result;
		T x = getLeftValue().read(ship, world, program, deltaT);
		T y = getRightValue().read(ship, world, program, deltaT);
		
		switch (getOperator()) {
			case "+":		result = (Double) x + (Double) y;
							break;
			case "*":		result = (Double) x * (Double) y; 	
							break;
			case "<":		result = (Double) x < (Double) y;
							break;
			case "==":		result = (x == y);
							break;
			default:		throw new FalseProgramException("Double expression is not correctly declared");
		}
		return (T) result;
	}
}
