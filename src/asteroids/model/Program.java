package asteroids.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import asteroids.model.programs.BreakException;
import asteroids.model.programs.FalseProgramException;
import asteroids.model.programs.FalseReturnException;
import asteroids.model.programs.NoMoreTimeException;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.statements.FunctionStatement;
import asteroids.model.programs.statements.Statement;
import asteroids.part3.programs.SourceLocation;

public class Program {
	
	private final Statement main;
	private SourceLocation endingSourceLocation;
	private double extraTime = 0;
	private double consumedTime = 0;
	
	public List<String> callStack = new ArrayList<>();
	
	private HashMap<String,Object> globalVariables = new HashMap<>();
	private HashMap<String,HashMap<String,Object>> functionScopes = new HashMap<>();
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
	
	public Boolean functionExists(String functionName) {
		return this.functions.containsKey(functionName);
	}
	
	public HashMap<String, Object> getGlobalVariables() { return this.globalVariables; }
	
	public HashMap<String, Object> getFunctionScope(String functionName) { 
		return this.functionScopes.get(functionName);
	}
	
	public HashMap<String, Object> getCurrentScope() {
		if (isInFunction()) {
			return getFunctionScope(getCurrentFunction());
		} else {
			return getGlobalVariables();
		}
	}
	
	public Boolean variableExists(String variableName) {
		return (getCurrentScope().containsKey(variableName));
	}
	
	public Object getVariable(String variableName) throws FalseProgramException {
		if (getCurrentScope().containsKey(variableName)) {
			return getCurrentScope().get(variableName);
		} else if (getGlobalVariables().containsKey(variableName)) { // Global scope
			return this.globalVariables.get(variableName);
		} else {
			throw new FalseProgramException("Variable doesn't exist");
		}
	}
	
	public void addOrUpdateVariable(String variableName, Object assignedValue) throws FalseProgramException {
		if (!isInFunction() && functionExists(variableName)) {
			throw new FalseProgramException("Variable name is already a function");
		}
		
		if (variableExists(variableName)) {
			getCurrentScope().replace(variableName, assignedValue);
		} else {
			getCurrentScope().put(variableName, assignedValue);
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
	
	public void enterFunction(String functionName, List<Object> parameters) {
		
		final String funcName = functionName;
		long recursiveDepth = callStack.stream().filter(func -> func.startsWith(funcName)).count();
		functionName += "*" + recursiveDepth;

		callStack.add(functionName);
		functionScopes.put(functionName, new HashMap<String,Object>());
		
		for (int i=0; i < parameters.size(); i++) {
			getCurrentScope().put("$"+(i+1), parameters.get(i));
		}
	}
	
	public void exitFunction() {
		if (callStack.size() > 0) {
			functionScopes.remove(getCurrentFunction());
			callStack.remove(callStack.size()-1);
		}
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
	
	public List<Object> execute(double deltaT, Ship ship, World world) throws FalseProgramException, FalseReturnException {
		try {
			getMain().execute(ship, world, this, deltaT);
			if (getEndingSourceLocation() != null){
				return null;
			} else {
				return getPrintedObjects();
			}
		} catch (NoMoreTimeException e) {
		} catch (BreakException ex) { 
			throw new FalseProgramException("Break is not in a while");
		}
		return null;
	}
}


