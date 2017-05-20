package asteroids.model.programs;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part3.programs.SourceLocation;

public class WhileStatement<E,S,F> extends Statement {

	public WhileStatement(Expression<?> condition, Statement<E,F> left, Statement<E,F> right, String stating, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.condition = condition;
		this.left = left;
		this.right = right;
		this.stating = stating;
	}
	
	private Expression<?> condition;
	private Statement<E,F> left;
	private Statement<E,F> right;
	private String stating;

	
	public Expression<?> getCondition() { return this.condition;}	
	public Statement<E,F> getLeft() { return this.left ;}
	public Statement<E,F> getRight() { return this.right; }
	public String getStating() { return this.stating; }
	
	public void execute(Ship ship,World world, Program program, double deltaT) throws FalseProgramException, BreakException, NoMoreTimeException, FalseReturnException {
		doStuff(ship, world, program, deltaT);
		if (getStating().equals("if")) {

			 if ((boolean) getCondition().read(ship, world, program).getValue()) {
				 getLeft().execute(ship, world, program, deltaT);

			 } else {
				 if (getRight() != null){
					 getRight().execute(ship, world, program, deltaT); 
				 }
			 }
		 }
		 else throw new FalseProgramException("Illegal double statement");
	}
}
