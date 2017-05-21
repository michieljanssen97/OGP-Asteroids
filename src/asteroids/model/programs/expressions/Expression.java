package asteroids.model.programs.expressions;

import asteroids.model.*;
import asteroids.model.programs.FalseProgramException;
import asteroids.part3.programs.SourceLocation;

public abstract class Expression<T> {
	
	public Expression(SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	
	protected SourceLocation sourceLocation;
	public SourceLocation getSourceLocation() { return this.sourceLocation; }
	
	public abstract T read(Ship ship, World world, Program<?,?> program) throws FalseProgramException;
}
