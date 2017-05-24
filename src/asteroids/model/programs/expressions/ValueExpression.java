package asteroids.model.programs.expressions;

import asteroids.model.*;
import asteroids.model.programs.FalseProgramException;
import asteroids.part3.programs.SourceLocation;

public class ValueExpression<T> extends Expression<T> {
	
	private T value;
	
	public ValueExpression(T value, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.value = value;
	}

	public T read(Ship ship, World world, Program program, Double deltaT) throws FalseProgramException {
		return this.value;
	}
}