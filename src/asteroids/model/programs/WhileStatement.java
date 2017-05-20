package asteroids.model.programs;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part3.programs.SourceLocation;

public class WhileStatement<E, F> extends Statement {

	public WhileStatement(Expression<?> condition, Statement body, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.condition = condition;
		this.body = body;
	}

	private Expression condition;
	private Statement body;
	
	public Statement getBody() {return this.body;}
	public Expression<?> getCondition() {return this.condition;}	
	
	public void execute(Ship ship,World world, Program program, double deltaT) throws FalseProgramException, BreakException, NoMoreTimeException, FalseReturnException {
		while ((Boolean) getCondition().read(ship, world, program).getValue()){
			 try {
				 body.execute(ship, world, program, deltaT);
			 } catch (BreakException e) {
				 break;
			 }
		 }	
	}
}
