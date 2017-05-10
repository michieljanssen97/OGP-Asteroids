package asteroids.model.programs;

public class DoubleExpression<E> {
	public DoubleExpression(E left,E right, String operator) {
		this.left = left;
		this.right = right;
		this.operator = operator;
	}
	
	private E left;
	private E right;
	private String operator;
	public String getOperator() { return this.operator; }
	
	public Expression<?> getLeftValue(){
		return ((Expression<?>) this.left);
	}
	public Expression<?> getRightValue(){
		return ((Expression<?>) this.right);
	}
	
	public void setLeftValue(E newLeftValue){
		this.left = newLeftValue;
	}
	
	public void setRightValue(E newRightValue){
		this.left = newRightValue;
	}
}
