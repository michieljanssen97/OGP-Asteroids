package asteroids.model.programs.statements;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.NoMoreTimeException;
import asteroids.part3.programs.SourceLocation;

public class FunctionStatement extends Statement {
	
	String name;
	Statement body;
	SourceLocation sourceLocation;

	public FunctionStatement(String functionName, Statement body, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.name = functionName;
		this.body = body;
		this.sourceLocation = sourceLocation;
	}
	
	public void execute(Ship ship,World world, Program program, double deltaT) throws NoMoreTimeException {
		checkTimeLeft(ship, world, program, deltaT);
	}

}
