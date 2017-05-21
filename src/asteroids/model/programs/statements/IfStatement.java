package asteroids.model.programs.statements;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.BreakException;
import asteroids.model.programs.FalseProgramException;
import asteroids.model.programs.FalseReturnException;
import asteroids.model.programs.NoMoreTimeException;
import asteroids.model.programs.expressions.Expression;
import asteroids.part3.programs.SourceLocation;

public class IfStatement extends Statement {

	public IfStatement(Expression<?> condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;	}
	
	private Expression<?> condition;
	private Statement ifBody;
	private Statement elseBody;

	
	public Expression<?> getCondition() { return this.condition;}	
	public Statement getIfBody() { return this.ifBody ;}
	public Statement getElseBody() { return this.elseBody; }
	
	public void execute(Ship ship,World world, Program program, double deltaT) throws FalseProgramException, BreakException, NoMoreTimeException, FalseReturnException {
		 checkTimeLeft(ship, world, program, deltaT);

		 if ((boolean) getCondition().read(ship, world, program)) {
			 getIfBody().execute(ship, world, program, deltaT);
		 } else {
			 if (getElseBody() != null) {
				 getElseBody().execute(ship, world, program, deltaT); 
			 }
		 }
	}
}
