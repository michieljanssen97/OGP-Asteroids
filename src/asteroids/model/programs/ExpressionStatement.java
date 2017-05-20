package asteroids.model.programs;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part3.programs.SourceLocation;

public class ExpressionStatement<E> extends Statement {

	public ExpressionStatement(Expression<?> expression, String stating, SourceLocation sourceLocation){
		super(sourceLocation);
		this.expression = expression;
		this.stating = stating;
	}
	private Expression<?> expression;
	private String stating;
	
	public String getStating() {return this.stating;}

	public Expression<?> getExpression() {return this.expression;}
	
	public void setExpression(Expression<?> expression) {
		this.expression = expression;
	}

	public void execute(Ship ship,World world, Program program, double deltaT) throws FalseProgramException, FalseReturnException {
		 if (getStating().equals("print")){
			program.getPrintedObjects().add(getExpression().read(ship, world, program).getValue());
			System.out.println(getExpression().read(ship, world, program).getValue());
		 }
		 else if(getStating().equals("turn")){
			 program.setConsumedTime(program.getConsumedTime()+0.2);
			 Expression<Double> angle = (Expression<Double>) getExpression().read(ship, world, program);
			 try {
				 ship.turn(angle.getValue());
			 } catch (AssertionError e){
				 throw new IllegalArgumentException();
			 }
		 }else if(getStating().equals("return")){
			 if (program.getIsInFunction()){
				 
			 }else {
				 throw new FalseReturnException("Return outside function body");
			 }
		 }
		 
	}
}
