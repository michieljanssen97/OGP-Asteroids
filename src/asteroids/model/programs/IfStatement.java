package asteroids.model.programs;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part3.programs.SourceLocation;

public class IfStatement<E, F> extends Statement {

	public IfStatement(Expression<?> expression, Statement statement,String stating, SourceLocation sourceLocation) {
		super(sourceLocation);
		this.expression = expression;
		this.stating = stating;
		this.statement = statement;
	}

	private Expression expression;
	private String stating;
	private Statement statement;
	
	public Statement getStatement() {return this.statement;}
	public String getStating() {return this.stating;}
	public Expression<?> getExpression() {return this.expression;}	
	
	public void execute(Ship ship,World world, Program program, double deltaT) throws FalseProgramException, BreakException, NoMoreTimeException, FalseReturnException {

		 if (getStating().equals("while")){
			 while (true){
				 Expression<?> condition = getExpression();
				 Statement<E,F> breakWhileStat = new StringStatement("break",getSourceLocation());
				 Statement<E,F> whileBody = getStatement();
				 WhileStatement newWhileStat = new WhileStatement(condition, whileBody, breakWhileStat, "if", getSourceLocation());
				 try {
					 newWhileStat.execute(ship, world, program, deltaT);
				 } catch (BreakException e) {
					 break;
				 }
			 }	
		 } else throw new FalseProgramException("Illegal single statement");
	}
}
