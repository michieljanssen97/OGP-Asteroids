package asteroids.model.programs;

public class SingleExpression<E> {
	
	public SingleExpression(Expression<?> value, String operator){
		this.value = value;
		this.operator = operator;
	}
	
	private Expression<?> value;
	private String operator;
	public String getOperator() {return this.operator;}
	
	public Expression<?> getValue(){
		return this.value;
	}
	
	public void setValue(Expression<?> newValue){
		this.value = newValue;
	}
}
