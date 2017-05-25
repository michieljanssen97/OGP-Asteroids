package asteroids.model.programs.expressions;

import asteroids.model.*;
import asteroids.model.programs.BreakException;
import asteroids.model.programs.FalseProgramException;
import asteroids.model.programs.FalseReturnException;
import asteroids.model.programs.NoMoreTimeException;
import asteroids.part3.programs.SourceLocation;

public abstract class Expression<T> {
	
	protected SourceLocation sourceLocation;
	
	public Expression(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	public SourceLocation getSourceLocation() { return this.sourceLocation; }
	
	public abstract T read(Ship ship, World world, Program program, Double deltaT) throws FalseProgramException, NoMoreTimeException, BreakException, FalseReturnException;
}
