package asteroids.model.programs;

public class Variable<T> {
	
	private String variableName;
	private T variableType;

	public Variable(String variableName, T variableType){
		this.variableName = variableName;
		this.variableType = variableType;
	}
	
	public String getVariableName() { return this.variableName; }
	
	public T getVariableType() { return this.variableType; }
}
