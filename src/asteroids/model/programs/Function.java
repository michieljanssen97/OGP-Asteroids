package asteroids.model.programs;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part3.programs.SourceLocation;

public class Function extends Statement {
	
	String name;
	Statement body;
	SourceLocation sourceLocation;

	public Function(String functionName, Statement body, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.name = functionName;
		this.body = body;
		this.sourceLocation = sourceLocation;

	}
	
	public void execute(Ship ship,World world, Program program, double deltaT) throws NoMoreTimeException {
		doStuff(ship, world, program, deltaT);
	}

}
