package asteroids.model.programs.statements;

import java.util.Arrays;

import asteroids.model.Entity;
import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.BreakException;
import asteroids.model.programs.FalseProgramException;
import asteroids.model.programs.NoMoreTimeException;
import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class AssignmentStatement extends Statement {
	
	private Expression<?> value;
	private String variableName;
	
	public AssignmentStatement(String variableName, Expression<?> value, SourceLocation sourceLocation){
		super(sourceLocation);
		this.value = value;
		this.variableName = variableName;
	}
	
	public String getVariableName() {return this.variableName;}
	public Expression<?> getExpression() {return this.value;}
	
	public void execute(Ship ship, World world, Program program, double deltaT) throws FalseProgramException, BreakException, NoMoreTimeException {
		checkTimeLeft(ship, world, program, deltaT);
		
		if (getExpression() instanceof Expression){
			 Object assignedValue = getExpression().read(ship, world, program);
			 
			 if (Arrays.asList(Boolean.class, Double.class, Entity.class).contains(assignedValue.getClass())) {
				 if (program.getVariables().containsKey(getVariableName()) 
						 && program.getVariables().get(getVariableName()).getClass() == assignedValue.getClass()) {
					 program.getVariables().replace(getVariableName(), assignedValue);
				 } else {
					 program.getVariables().put(getVariableName(), assignedValue);
				 }
			 } else {
				 throw new FalseProgramException("Not a correct assignment");
			 }
		 }
		 else
			 throw new FalseProgramException("Not a correct assignment");

	}	 
}
