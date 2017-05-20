package asteroids.model.programs;

import java.util.List;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part3.programs.SourceLocation;

public class SequenceStatement<F, E> extends Statement<E,F> {

	private List<Statement<E,F>> statements;
	
	public List<Statement<E,F>> getStatements() {
		return this.statements;
	}
	
	public SequenceStatement(List<Statement<E,F>> statements, SourceLocation sourceLocation) { 
		super(sourceLocation);
		this.statements = statements;
	}
	
	public void execute(Ship ship,World world, Program<F,?> program, double deltaT) throws BreakException, NoMoreTimeException, FalseProgramException, FalseReturnException {
		doStuff(ship, world, program, deltaT); 
		int correctLocation = 0;
		 List<Statement<E,F>> newList = getStatements();
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
