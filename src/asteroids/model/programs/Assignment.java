package asteroids.model.programs;

public class Assignment<E> {
	
	public Assignment(Expression<?> value, String operator, String variableName){
		this.value = value;
		this.operator = operator;
		this.variableName = variableName;
	}
	
	private Expression<?> value;
	private String operator;
	private String variableName;

	public String getOperator() {return this.operator;}
	public String getVariableName() {return this.variableName;}
	public Expression<?> getValue() {return this.value;}
}
