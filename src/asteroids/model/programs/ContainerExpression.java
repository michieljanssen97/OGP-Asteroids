package asteroids.model.programs;

import java.util.HashSet;
import java.util.Set;

import asteroids.model.*;
import asteroids.part3.programs.SourceLocation;

public class ContainerExpression extends Expression {
	
	Object contained;
	
	public ContainerExpression(Object value, SourceLocation sourceLocation) {
		super(null, sourceLocation);
		this.contained = value;
	}
	
	private SourceLocation sourceLocation;
	public SourceLocation getSourceLocation() { return this.sourceLocation; }
	
	
	public Object read(Ship ship, World world, Program program) throws FalseProgramException {
		return this.contained;
	}

	
}