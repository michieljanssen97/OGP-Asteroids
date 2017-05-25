package asteroids.model.programs.statements;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import asteroids.model.Asteroid;
import asteroids.model.Entity;
import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.BreakException;
import asteroids.model.programs.FalseProgramException;
import asteroids.model.programs.FalseReturnException;
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
	
	public void execute(Ship ship, World world, Program program, double deltaT) throws FalseProgramException, BreakException, NoMoreTimeException, FalseReturnException {
		checkTimeLeft(ship, world, program, deltaT);
		
		if (getExpression() instanceof Expression){
			 Object assignedValue = getExpression().read(ship, world, program, deltaT);
			 
			 if (assignedValue == null || Arrays.asList(Boolean.class, Double.class).contains(assignedValue.getClass()) 
					 || Entity.class.isAssignableFrom(assignedValue.getClass())) {
		
				 if (assignedValue == null){
					 //
				 } else if (!program.isInFunction() && (program.functionExists(getVariableName()))) {
					 throw new FalseProgramException("Name is already taken by a function");
				 } else if (program.variableExists(getVariableName())
						 && !(program.getVariable(getVariableName()).getClass() == assignedValue.getClass())) {
					 throw new FalseProgramException("Typing error");
				 } 
				 
				 program.addOrUpdateVariable(getVariableName(), assignedValue);
			 } else {
				 System.out.println(assignedValue.getClass());
				 throw new FalseProgramException("Cannot assign this particular type");
			 }
		 } else {
			 throw new FalseProgramException("Incorrect expression during assignment");
		 }
	}	 
}
