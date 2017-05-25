package asteroids.model.programs.expressions;

import asteroids.model.*;
import asteroids.model.programs.FalseProgramException;
import asteroids.part3.programs.SourceLocation;

public class VariableExpression extends Expression<Object> {
	
	private String varName;
	
	public VariableExpression(String varname, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.varName = varname;
	}

	public Object read(Ship ship, World world, Program program, Double DeltaT) throws FalseProgramException {
		return program.getVariable(this.varName);
	}

	
}