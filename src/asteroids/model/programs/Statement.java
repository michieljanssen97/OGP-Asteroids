package asteroids.model.programs;

import java.util.List;
import java.util.concurrent.ExecutionException;

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
	 
	 public void doStuff(Ship ship,World world, Program program, double deltaT) throws NoMoreTimeException {
		 deltaT = deltaT + program.getExtraTime();
		 if (program.getEndingSourceLocation() != null){
			 if ((this.getSourceLocation().getLine() == (program.getEndingSourceLocation().getLine())) && (this.getSourceLocation().getColumn() == program.getEndingSourceLocation().getColumn())) {	 
				 program.setEndingSourceLocation(null);
			 }
		 }
		 
		 if ((deltaT-program.getConsumedTime())<0.2) {
			 program.setEndingSourceLocation(this.getSourceLocation());
			 program.setConsumedTime(0.0);
			 program.setExtraTime(deltaT-program.getConsumedTime());
			 throw new NoMoreTimeException();
		 }
	}
	 
	public void execute(Ship ship,World world, Program<Statement,F> program, double deltaT) throws FalseProgramException, BreakException, NoMoreTimeException {
		doStuff(ship, world, program, deltaT);
		if (this.getValue() instanceof SingleStatement) {
			executeSingleStatement(ship, world, program, deltaT);
		} else if (this.getValue() instanceof DoubleStatement)  {
			executeDoubleStatement(ship, world, program, deltaT);
		} else if (this.getValue() instanceof String) {
			executeString(ship, world, program, deltaT);
		} else if (this.getValue() instanceof ExpressionStatement) {
			executeExpressionStatement(ship, world, program, deltaT);
		} else if (this.getValue() instanceof Assignment) {
			executeAssignment(ship, world, program, deltaT);
		} else {
			List<Statement<E,F>> newList = (List<Statement<E,F>>)this.getValue();
			 int correctLocation = 0;
			 if (program.getEndingSourceLocation() == null){
				 for (Statement<E,F> currentStatement : newList) {
					 currentStatement.execute(ship, world, program, deltaT);
				 }
			 } else {	 
				 for (int i = 0 ; i < newList.size() ; i++) {
					 if ((newList.get(i).getSourceLocation().getLine() <= program.getEndingSourceLocation().getLine())){
						 if ((newList.get(i).getSourceLocation().getColumn() <= program.getEndingSourceLocation().getColumn())){
							 correctLocation = i;
						 }
					 }
				 }for (int i = 0 ; i < newList.size() ; i++) {
					 if (i>=correctLocation) {
						 newList.get(i).execute(ship, world, program, deltaT);
					 }
				 }
			 }
		}
		
	}
	
	public void executeSingleStatement(Ship ship,World world, Program<Statement,F> program, double deltaT) throws FalseProgramException, BreakException, NoMoreTimeException {
		SingleStatement<E,Statement<E,F>,F> singleStatement = ((SingleStatement<E,Statement<E,F>,F>) (this.getValue()));

		 if (singleStatement.getStating().equals("while")){
			 while (true){
				 Expression<?> condition = singleStatement.getExpression();
				 Statement<String,F> breakWhileStat = new Statement<String,F>("break",getSourceLocation());
				 Statement<E,F> whileBody = singleStatement.getStatement();
				 DoubleStatement<Expression<?>,Statement<E,F>,F> newWhileStat = new DoubleStatement(condition,whileBody, breakWhileStat, "if");
				 Statement<DoubleStatement<Expression<?>,Statement<E,F>,F>,F> ifStatement = new Statement<DoubleStatement<Expression<?>,Statement<E,F>,F>,F>(newWhileStat,getSourceLocation()) ;
				 try {
					 ifStatement.execute(ship, world, program, deltaT);
				 } catch (BreakException e) {
					 break;
				 }
			 }	
		 } else throw new FalseProgramException("Illegal single statement");
	}
	
	public void executeDoubleStatement(Ship ship,World world, Program<Statement,F> program, double deltaT) throws FalseProgramException, BreakException, NoMoreTimeException {
		if (this.getValue() instanceof DoubleStatement) {
			 DoubleStatement<E,Statement<E,F>,F> doubleStatement = (DoubleStatement<E,Statement<E,F>,F>) this.getValue();
			 if (doubleStatement.getStating().equals("if")) {

				 if (doubleStatement.getCondition().read(program).evaluateExpression(ship, world, program).getValue()) {
					 doubleStatement.getLeft().execute(ship, world, program, deltaT);

				 } else{
					 if (doubleStatement.getRight() != null){
						 doubleStatement.getRight().execute(ship, world, program, deltaT); 
					 }
				 }
			 }
			 else throw new FalseProgramException("Illegal double statement");
		}
	}
		
	public void executeString(Ship ship,World world, Program<Statement,F> program, double deltaT) throws BreakException, FalseProgramException {
		String string = (String) this.getValue();
		 switch (string) {
           case "break":  		throw new BreakException();
           case "thrust_on": 	program.setConsumedTime(program.getConsumedTime()+0.2);
			 					ship.toggleThruster();
			 					break;
           case "thrust_off":  program.setConsumedTime(program.getConsumedTime()+0.2);
			 					ship.toggleThruster();
			 					break;
           case "fire":        program.setConsumedTime(program.getConsumedTime()+0.2);
			 					ship.fireBullet();
			 					break;
           case "skip":		program.setConsumedTime(program.getConsumedTime()+0.2);
           					break;
           default: 			throw new FalseProgramException("Illegal string statement");
		 }
	}
	
	public void executeExpressionStatement(Ship ship,World world, Program<Statement,F> program, double deltaT) throws FalseProgramException {
		ExpressionStatement<E> expressionStatement = (ExpressionStatement<E>)this.getValue();
		 if (expressionStatement.getStating().equals("print")){
			 if (expressionStatement.getExpression().getValue() instanceof String) {
				 program.getPrintedObjects().add(expressionStatement.getExpression().read(program).getValue());
				 System.out.println(expressionStatement.getExpression().read(program).getValue());
			 }
			 try {
				 Expression<Double> exp = (expressionStatement.getExpression().calculateExpression(ship, world, program));
				 program.getPrintedObjects().add(exp.getValue());
				 System.out.println(exp.getValue());
			 } catch (FalseProgramException e) {	
			 }
			 try {
				 Expression<Boolean> exp = (expressionStatement.getExpression().evaluateExpression(ship, world, program));
				 program.getPrintedObjects().add(exp.getValue());
				 System.out.println(exp.getValue());
			 } catch (FalseProgramException e) {		
			 }
			 try {
				 Expression<Entity> exp = (expressionStatement.getExpression().searchEntity(world, ship, program));
				 program.getPrintedObjects().add(exp.getValue());
				 System.out.println(exp.getValue());
			 } catch (FalseProgramException e) {	
			 }
		 }
		 else if(expressionStatement.getStating().equals("turn")){
			 program.setConsumedTime(program.getConsumedTime()+0.2);
			 Expression<Double> angle = expressionStatement.getExpression().read(program).calculateExpression(ship, world, program);
			 ship.turn(angle.getValue());
		 }
	}
	
	public void executeAssignment(Ship ship,World world, Program<Statement,F> program, double deltaT) throws FalseProgramException, BreakException, NoMoreTimeException {
		Assignment<E> assignment = (Assignment<E>) this.getValue();
		System.out.println(assignment.getVariableName());
		System.out.println(assignment.getValue().getValue().getClass().getSimpleName().equals("Double"));
		 if (assignment.getValue().getValue().getClass().getSimpleName().equals("Double")) {
			 if (program.getAllVariables().contains(assignment.getVariableName())){
				 if(program.getDoubleVariables().containsKey(assignment.getVariableName())){
					 program.getDoubleVariables().put(assignment.getVariableName(), assignment.getValue().read(program).calculateExpression(ship, world, program).getValue());
				 } else {
					 throw new FalseProgramException("Type change of variable is not allowed.");
				 }
			 } else {
				 program.getDoubleVariables().put(assignment.getVariableName(), assignment.getValue().read(program).calculateExpression(ship, world, program).getValue());
				 program.getAllVariables().add(assignment.getVariableName());
			 }
			 
		 }
		 else if (assignment.getValue().getValue().getClass().getSimpleName().equals("Entity")){
			 if (assignment.getValue().getValue() == null)
				 program.getEntityVariables().put(assignment.getVariableName(), null);
			 else
				 program.getEntityVariables().put(assignment.getVariableName(), assignment.getValue().read(program).searchEntity(world, ship, program).getValue());
		 }
		 else if (assignment.getValue().getValue().getClass().getSimpleName().equals("Boolean")){
			 program.getBooleanVariables().put(assignment.getVariableName(), assignment.getValue().read(program).evaluateExpression(ship, world,program).getValue());
		 }
		 else if (assignment.getValue() instanceof Expression){
			 Expression<?> assignmentExpr = (Expression<?>)assignment.getValue();
			 try {
				 Expression<Double> exp = (assignmentExpr.read(program).calculateExpression(ship, world, program));
				 program.getDoubleVariables().put(assignment.getVariableName(), exp.getValue());
			 } catch (FalseProgramException e) {	
			 }
			 try {
				 Expression<Boolean> exp = (assignmentExpr.read(program).evaluateExpression(ship, world, program));
				 program.getBooleanVariables().put(assignment.getVariableName(), exp.getValue());
			 } catch (FalseProgramException e) {		
			 }
			 try {
				 Expression<Entity> exp = (assignmentExpr.read(program).searchEntity(world, ship, program));
				 if (program.getAllVariables().contains(assignment.getVariableName())){
					 if(program.getEntityVariables().containsKey(assignment.getVariableName())){
						 program.getEntityVariables().put(assignment.getVariableName(), exp.getValue());
					 }
					 else {
						 throw new IllegalArgumentException("Type change of variable is not allowed.");
					 }
				 }
				 else {
					 program.getEntityVariables().put(assignment.getVariableName(), exp.getValue());
					 program.getAllVariables().add(assignment.getVariableName());
				 }
			 } catch (FalseProgramException e) {

			 }
		 
		 }
		 else throw new FalseProgramException("no such type");
	}
		 
}