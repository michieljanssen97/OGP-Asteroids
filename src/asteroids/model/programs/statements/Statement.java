package asteroids.model.programs.statements;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.programs.BreakException;
import asteroids.model.programs.FalseProgramException;
import asteroids.model.programs.FalseReturnException;
import asteroids.model.programs.NoMoreTimeException;
import asteroids.part3.programs.SourceLocation;

public abstract class Statement {
	
	 public Statement(SourceLocation sourceLocation) {
	  this.sourceLocation = sourceLocation;
	 }
	 private SourceLocation sourceLocation;
	
	 
	 public SourceLocation getSourceLocation() {
		 return this.sourceLocation;
	 }

	 public void doStuff(Ship ship,World world, Program program, double deltaT) throws NoMoreTimeException {
		 double toRound = (deltaT + program.getExtraTime())*100000.0;
		 toRound = Math.round(toRound);
		 deltaT = (toRound)/100000.0;
		 if (program.getEndingSourceLocation() != null){
			 if ((this.getSourceLocation().getLine() == (program.getEndingSourceLocation().getLine())) && (this.getSourceLocation().getColumn() == program.getEndingSourceLocation().getColumn())) {	 
				 program.setEndingSourceLocation(null);
			 }
		 }
		 
		 if ((deltaT-program.getConsumedTime())<0.2) {
			 if ( (program.getEndingSourceLocation() == null) || (program.getEndingSourceLocation().getLine() <= this.getSourceLocation().getLine()) && (program.getEndingSourceLocation().getColumn() <= this.getSourceLocation().getColumn()))
				 program.setEndingSourceLocation(this.getSourceLocation());
			 program.setExtraTime(deltaT-program.getConsumedTime());
			 program.setConsumedTime(0.0);
			 throw new NoMoreTimeException();
		 }
	 }

	public abstract void execute(Ship ship, World world, Program program, double deltaT)
			throws FalseProgramException, BreakException, NoMoreTimeException, FalseReturnException;
}