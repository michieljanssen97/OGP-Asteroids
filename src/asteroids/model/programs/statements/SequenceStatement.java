package asteroids.model.programs.statements;

import java.util.List;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.BreakException;
import asteroids.model.programs.FalseProgramException;
import asteroids.model.programs.FalseReturnException;
import asteroids.model.programs.NoMoreTimeException;
import asteroids.part3.programs.SourceLocation;

public class SequenceStatement extends Statement {

	private List<Statement> statements;
	
	public SequenceStatement(List<Statement> statements, SourceLocation sourceLocation) { 
		super(sourceLocation);
		this.statements = statements;
	}
	
	public List<Statement> getStatements() {
		return this.statements;
	}
	
	public void execute(Ship ship,World world, Program program, double deltaT) throws BreakException, NoMoreTimeException, FalseProgramException, FalseReturnException {
		checkTimeLeft(ship, world, program, deltaT);
		
		int correctLocation = 0;
		 List<Statement> newList = getStatements();
		 if (program.getEndingSourceLocation() == null){
			 for (Statement currentStatement : newList) {
				 currentStatement.execute(ship, world, program, deltaT);
			 }
		 } else {	 
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
