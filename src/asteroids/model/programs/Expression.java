package asteroids.model.programs;

import java.util.HashSet;
import java.util.Set;

import asteroids.model.*;
import asteroids.part3.programs.SourceLocation;

public class Expression<T> {
	
	public Expression(T value, SourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
		this.value = value;
	}
	protected T value;
	public T getValue() { return this.value; }
	public void setValue(T value) { this.value = value; }
	private SourceLocation sourceLocation;
	public SourceLocation getSourceLocation() { return this.sourceLocation; }
	
	
	public Object read(Ship ship, World world, Program program) throws FalseProgramException {
		if (this.getValue() instanceof Expression) {
			return this.read(ship, world, program);
		} else {
			return this;
		}

	}

	
}
