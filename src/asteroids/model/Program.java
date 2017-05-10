package asteroids.model;

import java.util.List;

import asteroids.model.programs.BreakException;
import asteroids.model.programs.FalseProgramException;
import asteroids.model.programs.NoMoreTimeException;
import asteroids.model.programs.Statement;
import asteroids.part3.programs.SourceLocation;

public class Program<F,S> {
	
	private final S main;
	private final List<F> functions;
	private SourceLocation endingSourceLocation;
	private double consumedTime;
	private boolean isInWhileLoop;
	
	
	public Program(List<F> functions, S main){
		this.main = main;
		this.functions = functions;
	}
	
	public Statement getMain(){
		return (Statement) this.main;	
	}
	public  List<F> getFunctions() { 
		return this.functions;
	}
	
	public boolean getIsInWhileLoop() {
		return isInWhileLoop;
	}
	public void setIsInWhileLoop(boolean isInWhileLoop) {
		this.isInWhileLoop = isInWhileLoop;
	}
	
	public double getConsumedTime() {
		return this.consumedTime; 
	}
	public void setConsumedTime(double time) {
		this.consumedTime = time;
	}
	
	public SourceLocation getEndingSourceLocation() { 
		return this.endingSourceLocation; 
	}
	public void setEndingSourceLocation(SourceLocation sourceLocation) {
		this.endingSourceLocation = sourceLocation;
	}
	
	public void execute(double deltaT, Ship ship, World world) throws FalseProgramException {
		while (true) {try {
		getMain().execute(ship, world, this, deltaT);
		}
		catch (NoMoreTimeException e) {
			break;}
		catch (BreakException ex) { throw new FalseProgramException("Break is not in a while");};
	}
	
	}
}


