package asteroids.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import asteroids.model.programs.BreakException;
import asteroids.model.programs.FalseProgramException;
import asteroids.model.programs.NoMoreTimeException;
import asteroids.model.programs.Statement;
import asteroids.part3.programs.SourceLocation;

public class Program<F,S> {
	
	private final S main;
	private final List<F> functions;
	private SourceLocation endingSourceLocation;
	private double extraTime = 0;
	private double consumedTime = 0;
	private boolean isInWhileLoop;
	private HashMap<String,Boolean> booleanVariables = new HashMap<>();
	private HashMap<String,Double> doubleVariables = new HashMap<>();
	private HashMap<String,Entity> entityVariables = new HashMap<>();
	private HashSet<String> allVariables = new HashSet<>();
	private List<Object> printedObjects = new ArrayList<>();

	
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
	public HashMap<String,Boolean> getBooleanVariables() { return this.booleanVariables; }
	public HashMap<String,Double> getDoubleVariables() { return this.doubleVariables;}
	public HashMap<String,Entity> getEntityVariables() { return this.entityVariables; }
	public HashSet<String> getAllVariables() { return this.allVariables; }
	public List<Object> getPrintedObjects() {return this.printedObjects;}
	


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
	
	public void execute(double deltaT, Ship ship, World world) throws FalseProgramException {
		while (true) {try {
		getMain().execute(ship, world, this, deltaT);
		}
		catch (NoMoreTimeException e) {
			break;}
		catch (BreakException ex) { throw new FalseProgramException("Break is not in a while");};
		break;
	}
	
	}
}


