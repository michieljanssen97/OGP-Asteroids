package asteroids.model.programs;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part3.programs.SourceLocation;

public class IfStatement<E,S,F> extends Statement<E,F> {

	public IfStatement(Expression<?> condition, Statement<E,F> ifBody, Statement<E,F> elseBody, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;	}
	
	private Expression<?> condition;
	private Statement<E,F> ifBody;
	private Statement<E,F> elseBody;

	
	public Expression<?> getCondition() { return this.condition;}	
	public Statement<E,F> getIfBody() { return this.ifBody ;}
	public Statement<E,F> getElseBody() { return this.elseBody; }
	
	public void execute(Ship ship,World world, Program<F,?> program, double deltaT) throws FalseProgramException, BreakException, NoMoreTimeException, FalseReturnException {
		 doStuff(ship, world, program, deltaT);

		 if ((boolean) getCondition().read(ship, world, program)) {
			 getIfBody().execute(ship, world, program, deltaT);

		 } else {
			 if (getElseBody() != null){
				 getElseBody().execute(ship, world, program, deltaT); 
			 }
		 }
	}
}
