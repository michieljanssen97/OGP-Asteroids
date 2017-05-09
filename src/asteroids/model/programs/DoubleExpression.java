package asteroids.model.programs;

public class DoubleExpression<E,T> {
	public DoubleExpression(E left,E right, String operator) {
		this.left = left;
		this.right = right;
		this.operator = operator;
	}
	
	private E left;
	private E right;
	private String operator;
	public String getOperator() { return this.operator; }
	
	public Expression<T> getLeftValue(){
		return ((Expression<T>) this.left);
	}
	public Expression<T> getRightValue(){
		return ((Expression<T>) this.right);
	}
	
	public void setLeftValue(E newLeftValue){
		this.left = newLeftValue;
	}
	
	public void setRightValue(E newRightValue){
		this.left = newRightValue;
	}
}
