package asteroids.model.programs.expressions;

import asteroids.model.*;
import asteroids.model.programs.FalseProgramException;
import asteroids.part3.programs.SourceLocation;

public class ContainerExpression<T> extends Expression<T> {
	
	T value;
	
	public ContainerExpression(T value, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.value = value;
	}

	public T read(Ship ship, World world, Program program) throws FalseProgramException {
		return this.value;
	}

	
}