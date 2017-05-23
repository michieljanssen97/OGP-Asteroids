package asteroids.model.programs.statements;

import java.util.List;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.BreakException;
import asteroids.model.programs.FalseProgramException;
import asteroids.model.programs.FalseReturnException;
import asteroids.model.programs.NoMoreTimeException;
import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class FunctionStatement extends Statement {
	
	String name;
	Statement body;
	SourceLocation sourceLocation;
	List<Expression<?>> arguments;
	Object returnValue;
	

	public FunctionStatement(String functionName, Statement body, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.name = functionName;
		this.body = body;
		this.sourceLocation = sourceLocation;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}
	
	public Object getResult() {
		return this.returnValue;
	}
	
	public void execute(Ship ship,World world, Program program, double deltaT) throws NoMoreTimeException, FalseProgramException, BreakException, FalseReturnException {
		checkTimeLeft(ship, world, program, deltaT);
		this.body.execute(ship, world, program, deltaT);
	}

}
