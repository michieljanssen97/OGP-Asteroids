package asteroids.model.programs;

public class Variable<T> {
	
	private String variableName;
	private T variableType;

	public Variable(String variableName){
		this.variableName = variableName;
	}
	
	public String getVariableName() { return this.variableName; }
	
	public T getVariableType() { return this.variableType; }
}
