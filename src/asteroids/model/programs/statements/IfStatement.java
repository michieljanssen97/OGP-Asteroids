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

	private Expression<Boolean> condition;
	private Statement ifBody;
	private Statement elseBody;
	
	public IfStatement(Expression<Boolean> condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;	}
	
	public Expression<Boolean> getCondition() { return this.condition;}	
	public Statement getIfBody() { return this.ifBody ;}
	public Statement getElseBody() { return this.elseBody; }
	
	public void execute(Ship ship,World world, Program program, double deltaT) throws FalseProgramException, BreakException, NoMoreTimeException, FalseReturnException {
		 checkTimeLeft(ship, world, program, deltaT);

		 if (getCondition().read(ship, world, program, deltaT)) {
			 getIfBody().execute(ship, world, program, deltaT);
		 } else {
			 if (getElseBody() != null) {
				 getElseBody().execute(ship, world, program, deltaT); 
			 }
		 }
	}
}
