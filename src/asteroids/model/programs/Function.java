package asteroids.model.programs;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part3.programs.SourceLocation;

public class Function<E, F> extends Statement<E,F> {
	
	String name;
	Statement<E,F> body;
	SourceLocation sourceLocation;

	public Function(String functionName, Statement<E,F> body, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.name = functionName;
		this.body = body;
		this.sourceLocation = sourceLocation;

	}
	
	public void execute(Ship ship,World world, Program<F,?> program, double deltaT) throws NoMoreTimeException {
		doStuff(ship, world, program, deltaT);
	}

}
