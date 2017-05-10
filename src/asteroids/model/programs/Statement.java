package asteroids.model.programs;

import java.util.List;

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
			 else if (string.equals("thrust_on")){
				 ship.toggleThruster();
			 }
			 else if (string.equals("thrust_off")){
				 ship.toggleThruster();
			 }
			 else if (string.equals("fire")){
				 ship.fireBullet();
			 }
			 else if (string.equals("skip")){
				 
			 }
			 else throw new FalseProgramException("Illegal string statement");
		 }

		 else if (this.getValue() instanceof ExpressionStatement) {
			 ExpressionStatement<E> expressionStatement = (ExpressionStatement<E>)this.getValue();
			 if (expressionStatement.getStating().equals("print")){
				 if (expressionStatement.getExpression().getValue() instanceof String) {
					 program.getPrintedObjects().add(0, expressionStatement.getExpression().read(program).getValue());
					 System.out.println(expressionStatement.getExpression().read(program).getValue());
				 }
				 try {
					 Expression<Double> exp = (expressionStatement.getExpression().calculateExpression(ship, world, program));
					 program.getPrintedObjects().add(0, exp.getValue());
					 System.out.println(exp.getValue());
				 } catch (FalseProgramException e) {	
				 }
				 try {
					 Expression<Boolean> exp = (expressionStatement.getExpression().evaluateExpression(ship, world, program));
					 program.getPrintedObjects().add(0, exp.getValue());
					 System.out.println(exp.getValue());
				 } catch (FalseProgramException e) {		
				 }
				 try {
					 Expression<Entity> exp = (expressionStatement.getExpression().searchEntity(world, ship, program));
					 program.getPrintedObjects().add(0, exp.getValue());
					 System.out.println(exp.getValue());
				 } catch (FalseProgramException e) {	
				 }
			 }
			 else if(expressionStatement.getStating().equals("turn")){
				 Expression<Double> angle = expressionStatement.getExpression().read(program).calculateExpression(ship, world, program);
				 ship.turn(angle.getValue());
			 }
		 }

		 else if (this.getValue() instanceof Assignment) {
			 Assignment<E> assignment = (Assignment<E>) this.getValue();
			 if (assignment.getValue().getValue().getClass().getSimpleName().equals("Double")) {
				 program.getDoubleVariables().put(assignment.getVariableName(), assignment.getValue().read(program).calculateExpression(ship, world,program).getValue());
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
			 else throw new FalseProgramException("no such type");
		 }

		 else {
			 List<Statement<E,F>> newList = (List<Statement<E,F>>)this.getValue();
			 int correctLocation = 0;
			 if (program.getEndingSourceLocation() == null){
				 for (Statement<E,F> currentStatement : newList) {
					 currentStatement.execute(ship, world, program, deltaT);
				 }
			 }
			 else {	 
				 for (int i = 0 ; i < newList.size() ; i++) {
					 if ((newList.get(i).getSourceLocation().getLine() <= program.getEndingSourceLocation().getLine())){
						 if ((newList.get(i).getSourceLocation().getColumn() <= program.getEndingSourceLocation().getColumn())){
							 correctLocation = i;
						 }
					 }
				 }
				 for (int i = 0 ; i < newList.size() ; i++) {
					 if (i>=correctLocation) {
						 newList.get(i).execute(ship, world, program, deltaT);
					 }
				 }
			 }
		 }	 
	 }

		 
}