package asteroids.model.programs;

public class Assignment<E> {
	
	public Assignment(E value, String operator, String variableName){
		this.value = value;
		this.operator = operator;
		this.variableName = variableName;
	}
	
	private E value;
	private String operator;
	private String variableName;

	public String getOperator() { return this.operator; }
	public String getVariableName() { return this.variableName; }
	@SuppressWarnings("unchecked")
	public Expression<?> getValue() { return (Expression<?>)this.value; }
}
