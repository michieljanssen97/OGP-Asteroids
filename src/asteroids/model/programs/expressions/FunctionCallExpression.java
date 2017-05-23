package asteroids.model.programs.expressions;

import java.util.List;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.BreakException;
import asteroids.model.programs.FalseProgramException;
import asteroids.model.programs.FalseReturnException;
import asteroids.model.programs.NoMoreTimeException;
import asteroids.model.programs.statements.FunctionStatement;
import asteroids.part3.programs.SourceLocation;

public class FunctionCallExpression extends Expression {

	String functionName;
	
	public FunctionCallExpression(String functionName, List<Expression>actualArgs, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.functionName = functionName;
	}
	
	public Object read(Ship ship, World world, Program program, Double deltaT) throws FalseProgramException, NoMoreTimeException, BreakException, FalseReturnException {
		FunctionStatement function = program.getFunction(functionName);
		program.enterFunction(functionName);
		try {
			function.execute(ship, world, program, deltaT);
		} finally {
			program.exitFunction();
		}
		return function.getResult();
		
		
	}

}
