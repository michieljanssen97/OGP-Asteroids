package asteroids.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import asteroids.model.programs.BreakException;
import asteroids.model.programs.FalseProgramException;
import asteroids.model.programs.FalseReturnException;
import asteroids.model.programs.NoMoreTimeException;
import asteroids.model.programs.statements.FunctionStatement;
import asteroids.model.programs.statements.Statement;
import asteroids.part3.programs.SourceLocation;

public class Program<F,S> {
	
	private final Statement main;
	private SourceLocation endingSourceLocation;
	private double extraTime = 0;
	private double consumedTime = 0;
	private boolean isInWhileLoop;
	
	private List<String> callStack = new ArrayList<>();
	
	private HashMap<String,Object> variables = new HashMap<>();
	private HashMap<String,HashMap<String,Object>> localVariableScopes = new HashMap<>();
	private HashMap<String,FunctionStatement> functions = new HashMap<>();
	private List<Object> printedObjects = new ArrayList<>();

	
	public Program(List<FunctionStatement> functions, Statement main){
		this.main = main;
		
		functions.stream()
				 .forEach(function -> this.functions.put(function.getName(), function));
	}
	
	public Statement getMain(){
		return this.main;	
	}
	
	public void addFunction(String functionName, FunctionStatement statement) {
		if (functions.containsKey(functionName)) {
			this.functions.put(functionName, statement);
		}
	}
	
	public FunctionStatement getFunction(String functionName) {
		return this.functions.get(functionName);
	}
	
	public HashMap<String, Object> getVariables() { return this.variables; }
	
	public Object getVariable(String variableName) throws FalseProgramException {
		if (this.variables.containsKey(variableName)) {
			return this.variables.get(variableName);
		} else {
			throw new FalseProgramException("Variable doesn't exist");
		}
	}
	
	public void addVariable(String variableName, Object assignedValue) throws FalseProgramException {
		if (this.variables.containsKey(variableName) || this.functions.containsKey(variableName)) {
			throw new FalseProgramException("Variable already exists or variable name is a function");
		} else {
			this.variables.put(variableName, assignedValue);
		}
	}
	
	public void updateVariable(String variableName, Object assignedValue) throws FalseProgramException {
		if (this.variables.containsKey(variableName)) {
			this.variables.replace(variableName, assignedValue);
		} else {
			throw new FalseProgramException("Variable doesn't exist");
		}
	}
	
	public List<Object> getPrintedObjects() {return this.printedObjects;}
	
	public boolean isInFunction() {
		return !callStack.isEmpty();
	}
	
	public String getCurrentFunction() {
		if (callStack.size() > 0) {
			return callStack.get(callStack.size()-1);
		} else {
			return null;
		}
	}
	
	public void enterFunction(String functionName) {
		callStack.add(functionName);
	}
	
	public void exitFunction() {
		if (callStack.size() > 0) {
			callStack.remove(callStack.size()-1);
		}
	}

	public boolean isInWhileLoop() {
		return isInWhileLoop;
	}
	public void toggleIsInWhileLoop() {
		isInWhileLoop = !isInWhileLoop;
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


