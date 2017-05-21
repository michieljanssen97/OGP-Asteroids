package asteroids.model.programs.statements;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.FalseProgramException;
import asteroids.model.programs.FalseReturnException;
import asteroids.model.programs.NoMoreTimeException;
import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class ExpressionStatement extends Statement {

	private Expression<?> expression;
	private String stating;
	
	public ExpressionStatement(Expression<?> expression, String stating, SourceLocation sourceLocation){
		super(sourceLocation);
		this.expression = expression;
		this.stating = stating;
	}
	
	
	public String getStating() {return this.stating;}
	public Expression<?> getExpression() {return this.expression;}
	
	public void setExpression(Expression<?> expression) {
		this.expression = expression;
	}

	public void execute(Ship ship,World world, Program program, double deltaT) throws FalseProgramException, FalseReturnException, NoMoreTimeException {
		checkTimeLeft(ship, world, program, deltaT);
		switch(getStating()) {
			 case "print":  Object expressionResult = getExpression().read(ship, world, program);
							program.getPrintedObjects().add(expressionResult);
							System.out.println(expressionResult);
							break;
			 case "turn":	program.setConsumedTime(program.getConsumedTime()+0.2);
							Double angle = (Double) getExpression().read(ship, world, program);
							try {
								ship.turn(angle);
							} catch (AssertionError e){
								throw new IllegalArgumentException();
							}
							break;
			 case "return": if (!program.getIsInFunction()){
								 throw new FalseReturnException("Return outside function body");
							}
			 				break;
		 }
	}
}
