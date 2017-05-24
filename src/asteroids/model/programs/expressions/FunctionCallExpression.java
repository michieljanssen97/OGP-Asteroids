package asteroids.model.programs.expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.BreakException;
import asteroids.model.programs.FalseProgramException;
import asteroids.model.programs.FalseReturnException;
import asteroids.model.programs.NoMoreTimeException;
import asteroids.model.programs.statements.FunctionStatement;
import asteroids.part3.programs.SourceLocation;

public class FunctionCallExpression extends Expression {

	private String functionName;
	private List<Expression> actualArgs;
	
	public FunctionCallExpression(String functionName, List<Expression>actualArgs, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.functionName = functionName;
		this.actualArgs = actualArgs;
	}
	
	public List<Expression> getArguments() {
		return this.actualArgs;
	}
	
	public Object read(Ship ship, World world, Program program, Double deltaT) throws FalseProgramException, NoMoreTimeException, BreakException, FalseReturnException {
		FunctionStatement function = program.getFunction(functionName);
		Object returnValue = null;
		
		List<Object> arguments = new ArrayList<>();
		for (Expression arg: getArguments()) {
			arguments.add(arg.read(ship, world, program, deltaT));
		}

		program.enterFunction(functionName, arguments);
		try {
			function.execute(ship, world, program, deltaT);
			returnValue = function.getReturnValue();
		} finally {
			program.exitFunction();
		}

		return returnValue;
		
		
	}

}
