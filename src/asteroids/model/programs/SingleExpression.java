package asteroids.model.programs;

public class SingleExpression<E> {
	
	public SingleExpression(E value, String operator){
		this.value = value;
		this.operator = operator;
	}
	
	private E value;
	private String operator;
	public String getOperator() { return this.operator; }
	
	public Expression getValue(){
		return (Expression) this.value;
	}
	
	public void setValue(E newValue){
		this.value = newValue;
	}
}
