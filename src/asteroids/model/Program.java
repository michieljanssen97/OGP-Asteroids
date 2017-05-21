package asteroids.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import asteroids.model.programs.BreakException;
import asteroids.model.programs.FalseProgramException;
import asteroids.model.programs.FalseReturnException;
import asteroids.model.programs.NoMoreTimeException;
import asteroids.model.programs.statements.Statement;
import asteroids.part3.programs.SourceLocation;

public class Program<F,S> {
	
	private final Statement main;
	private final List<F> functions;
	private SourceLocation endingSourceLocation;
	private double extraTime = 0;
	private double consumedTime = 0;
	private boolean isInWhileLoop;
	private boolean isInFunction = false;
	private HashMap<String,Object> variables = new HashMap<>();
	private List<Object> printedObjects = new ArrayList<>();

	
	public Program(List<F> functions, Statement main){
		this.main = main;
		this.functions = functions;
	}
	
	public Statement getMain(){
		return this.main;	
	}
	public  List<F> getFunctions() { 
		return this.functions;
	}
	public HashMap<String, Object> getVariables() { return this.variables; }
	public List<Object> getPrintedObjects() {return this.printedObjects;}
	
	public boolean getIsInFunction() {
		return isInFunction;
	}
	public void setIsInFunctionLoop(boolean isInFunction) {
		this.isInFunction = isInFunction;
	}

	public boolean getIsInWhileLoop() {
		return isInWhileLoop;
	}
	public void setIsInWhileLoop(boolean isInWhileLoop) {
		this.isInWhileLoop = isInWhileLoop;
	}
	
	public double getExtraTime() {
		return this.extraTime;
	}
	public void setExtraTime(double time) {
		this.extraTime = time;
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
	
	public void execute(double deltaT, Ship ship, World world) throws FalseProgramException, FalseReturnException {
		try {
			getMain().execute(ship, world, this, deltaT);
		} catch (NoMoreTimeException e) {
		} catch (BreakException ex) { 
			throw new FalseProgramException("Break is not in a while");
		}
	}
}


