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
	
	
	public Expression<?> read(Ship ship, World world, Program program) throws FalseProgramException {
		if (this.getValue() instanceof String) {
			if (program.getDoubleVariables().get(this.getValue()) != null){
				return new Expression<Double>((Double)program.getDoubleVariables().get(this.getValue()),getSourceLocation());
			}
			else if (program.getBooleanVariables().get(this.getValue()) != null){
				return new Expression<Boolean>((Boolean)program.getDoubleVariables().get(this.getValue()),getSourceLocation());
			}
			
			else if (program.getEntityVariables().get(this.getValue()) != null){
				return new Expression<Entity>((Entity)program.getDoubleVariables().get(this.getValue()),getSourceLocation());
			}
			else throw new FalseProgramException("No such type");
			
		} else if (this.getValue() instanceof Expression) {
			return this.execute(ship, world, program);
		} else {
			return this;
		}

	}
	private Expression<?> execute(Ship ship, World world, Program program) {
		return this.getValue().execute(ship, world, program);
	}
	
}
