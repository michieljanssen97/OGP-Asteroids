package asteroids.model.programs.statements;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.BreakException;
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

	public void execute(Ship ship,World world, Program program, double deltaT) throws FalseProgramException, FalseReturnException, NoMoreTimeException, BreakException {
		checkTimeLeft(ship, world, program, deltaT);
		switch(getStating()) {
			 case "print":  if (!program.isInFunction()) {
					 			Object expressionResult = getExpression().read(ship, world, program, deltaT);
								program.getPrintedObjects().add(expressionResult);
								System.out.println(expressionResult);
								break;
			 				} else {
			 					throw new FalseReturnException("Cannot print in function body");
			 				}
			 case "turn":	if (!program.isInFunction()) {
					 			program.setConsumedTime(program.getConsumedTime()+0.2);
								Double angle = (Double) getExpression().read(ship, world, program, deltaT);
								try {
									ship.turn(angle);
								} catch (AssertionError e){
									throw new IllegalArgumentException();
								}
								break;
			 				} else {
			 					throw new FalseReturnException("Cannot turn in function body");
							}
			 case "return": if (!program.isInFunction()){
								 throw new FalseReturnException("Return outside of function body");
							} else {
								Object returnValue = expression.read(ship, world, program, deltaT);
								
								String functionName = program.getCurrentFunctionName();
								if (functionName.charAt(functionName.length()-2) == '*') {
									functionName = functionName.substring(0, functionName.length()-2);
								}

								program.getFunction(functionName).setReturnValue(returnValue);
								break;
							}
		 }
	}
}
