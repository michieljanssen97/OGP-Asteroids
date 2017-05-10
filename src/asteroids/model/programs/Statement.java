package asteroids.model.programs;

import asteroids.model.Entity;
import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part3.programs.SourceLocation;

public class Statement<E,F> {
	
	 public Statement(E value, SourceLocation sourceLocation) {
	  this.value = value;
	  this.sourceLocation = sourceLocation;
	 }
	 private SourceLocation sourceLocation;
	 private E value;
	 
	 public SourceLocation getSourceLocation() {
		 return this.sourceLocation;
	 }

	 public E getValue() {
	  return (this.value);
	 }
	 
	 public void setValue(E value) {
		this.value = value;
	 }
	 public void execute(Ship ship,World world, Program<Statement,F> program, double deltaT) throws FalseProgramException, BreakException, NoMoreTimeException {
		 if (program.getEndingSourceLocation() != null){
			 if ((this.getSourceLocation().getLine() == (program.getEndingSourceLocation().getLine())) && (this.getSourceLocation().getColumn() == program.getEndingSourceLocation().getColumn())) {	 
				 program.setEndingSourceLocation(null);
			 }
		 }
		 if (program.getConsumedTime() > deltaT){
			 program.setEndingSourceLocation(this.getSourceLocation());
			 program.setConsumedTime(0.0);
			 throw new NoMoreTimeException();
		 }
		 
		 if (this.getValue() instanceof SingleStatement) {
			 SingleStatement<E,Statement<E,F>,F> singleStatement = ((SingleStatement<E,Statement<E,F>,F>) (this.getValue()));
			 
			 if (singleStatement.getStating().equals("while")){
				 while (true){
					 Expression<?> condition = singleStatement.getExpression();
					 Statement<String,F> breakWhileStat = new Statement<String,F>("break",getSourceLocation());
					 Statement<E,F> whileBody = singleStatement.getStatement();
					 DoubleStatement<Expression<?>,Statement,F> newWhileStat = new DoubleStatement<>(condition,whileBody, breakWhileStat, "if");
					 Statement<DoubleStatement<Expression<?>,Statement,F>,F> ifStatement = new Statement<DoubleStatement<Expression<?>,Statement,F>,F>(newWhileStat,getSourceLocation()) ;
					 try {
						 ifStatement.execute(ship, world, program, deltaT);
					 } catch (BreakException e) {
						 break;
					 }
				 }	
			 }
			 else throw new FalseProgramException("Illegal single statement");
		 }
		 
		 else if (this.getValue() instanceof DoubleStatement) {
			 DoubleStatement<E,Statement<E,F>,F> doubleStatment = (DoubleStatement<E,Statement<E,F>,F>) this.getValue();
			 if (doubleStatment.getStating().equals("if")) {

				 if (doubleStatment.getCondition().read(program).evaluateExpression(ship, world, program).getValue()) {
					 doubleStatment.getLeft().execute(ship, world, program, deltaT);

				 } else{
					 if (doubleStatment.getRight() != null){
						 doubleStatment.getRight().execute(ship, world, program, deltaT); 
					 }
				 }
			 }
			 else throw new FalseProgramException("Illegal double statement");
		 }
		 
		 else if (this.getValue() instanceof String) {
			 String string = (String) this.getValue();
			 if (string.equals("break")) {
				 throw new BreakException();
			 }
			 else throw new FalseProgramException("Illegal string statement");
		 }
		 
		 else if (this.getValue() instanceof ExpressionStatement) {
			 ExpressionStatement<E> expressionStatement = (ExpressionStatement<E>)this.getValue();
			 if (expressionStatement.getStating().equals("print")){
				 if (expressionStatement.getExpression().getValue() instanceof Variable) {
					 Variable<?> var = (Variable<?>)expressionStatement.getExpression().getValue();
					 Expression<Variable<?>> expresvar = new Expression<Variable<?>>(var, getSourceLocation());
					 System.out.println(expresvar.read(program).getValue());
				 }
				 try {
					 Expression<Double> exp = (expressionStatement.getExpression().calculationExpression(ship, world, program));
					 System.out.println(exp.getValue());
				 } catch (FalseProgramException e) {	
				 }
				 try {
					 Expression<Boolean> exp = (expressionStatement.getExpression().evaluateExpression(ship, world, program));
					 System.out.println(exp.getValue());
				 } catch (FalseProgramException e) {		
				 }
				 try {
					 Expression<Entity> exp = (expressionStatement.getExpression().searchEntity(world, ship, program));
					 System.out.println(exp.getValue());
				 } catch (FalseProgramException e) {	
				 }
			 }
		 }
		 
		 else if (this.getValue() instanceof Assignment) {
			 Assignment<E> assignment = (Assignment<E>) this.getValue();
			 if (assignment.getValue().getValue().toString().equals("Double") {
				 program.getDoubleVariables().put(assignment.getVariableName(), assignment.getValue().read(program).calculate(gameObject, world,program).getValue());
			 }
			 else if (assignment.getValue().getValue().equals(T)){
				 if (assignment.getValue().getValue() == null)
					 program.getGameObjectVariables().put(assignment.getVariableName(), null);
				 else
					 program.getGameObjectVariables().put(assignment.getVariableName(), assignment.getValue().read(program).search(world, gameObject, program).getValue());
			 }
			 else if (assignment.getValue().getValue().equals(Type.Double)){
				 program.getBooleanVariables().put(assignment.getVariableName(), assignment.getValue().read(program).evaluate(gameObject, world,program).getValue());
			 }
			 else throw new FalseProgramException("no such type");
		 }
			 
			 
			 
			 
			 
			 
	 }

		 
}