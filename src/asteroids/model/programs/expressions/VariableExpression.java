package asteroids.model.programs.expressions;

import java.util.Arrays;

import asteroids.model.*;
import asteroids.model.programs.FalseProgramException;
import asteroids.part3.programs.SourceLocation;

public class VariableExpression extends Expression {
	
	String varname;
	
	public VariableExpression(String varname, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.varname = varname;
	}

	public Object read(Ship ship, World world, Program program) throws FalseProgramException {
		if (program.getVariables().containsKey(this.varname)) {
			 return program.getVariables().get(this.varname);
		 } else {
			 throw new FalseProgramException("Variable doesn't exist");
		 }
	}

	
}