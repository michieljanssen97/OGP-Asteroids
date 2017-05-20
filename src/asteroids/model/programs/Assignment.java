package asteroids.model.programs;

import asteroids.model.Entity;
import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part3.programs.SourceLocation;

public class Assignment<E> extends Statement {
	
	public Assignment(String variableName, Expression<?> value, SourceLocation sourceLocation){
		super(sourceLocation);
		this.value = value;
		this.variableName = variableName;
	}
	
	private Expression<?> value;
	private String variableName;

	public String getVariableName() {return this.variableName;}
	public Expression<?> getExpression() {return this.value;}
	
	public void execute(Ship ship,World world, Program program, double deltaT) throws FalseProgramException, BreakException, NoMoreTimeException {
		doStuff(ship, world, program, deltaT); 
		if (getExpression() instanceof Expression){
			 Object assignmentExpr = getExpression().read(ship, world, program);

			 switch (assignmentExpr.getValue().getClass().getSimpleName()){
			 
			 case "Boolean": 	if ((!(program.getAllVariables().contains(getVariableName()))) || (program.getBooleanVariables().containsKey(getVariableName()))) {
				 					program.getBooleanVariables().put(getVariableName(), (Boolean) assignmentExpr.getValue());
				 					program.getAllVariables().add(getVariableName());
				 					break;
			 					}
				 
			 case "Entity":		if ((!(program.getAllVariables().contains(getVariableName()))) || (program.getEntityVariables().containsKey(getVariableName()))) {
				 					program.getEntityVariables().put(getVariableName(), (Entity) assignmentExpr.getValue());
				 					program.getAllVariables().add(getVariableName());
				 					break;
			 					}	
				 
			 case "Double":		if ((!(program.getAllVariables().contains(getVariableName()))) || (program.getDoubleVariables().containsKey(getVariableName()))) {
				 					program.getDoubleVariables().put(getVariableName(), (Double) assignmentExpr.getValue());
				 					program.getAllVariables().add(getVariableName());
				 					break;
								}
			 default: throw new FalseProgramException("Wrong type");
			 }
		 }
		 else
			 throw new FalseProgramException("Not a correct assignment");

	}	 
}
