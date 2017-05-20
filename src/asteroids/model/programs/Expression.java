package asteroids.model.programs;

import asteroids.model.*;
import asteroids.part3.programs.SourceLocation;

public abstract class Expression<T> {
	
	public Expression(Expression<T> value, SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
		this.value = value;
	}
	protected Expression<T> value;
	public Expression<T> getValue() { return this.value; }
	public void setValue(Expression<T> value) { this.value = value; }
	private SourceLocation sourceLocation;
	public SourceLocation getSourceLocation() { return this.sourceLocation; }
	
	public abstract Object read(Ship ship, World world, Program<?,?> program) throws FalseProgramException;
}
