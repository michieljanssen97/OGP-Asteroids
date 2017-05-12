package asteroids.model.programs;

public class DoubleExpression<E> {
	public DoubleExpression(Expression<?> left, Expression<?> right, String operator) {
		this.left = left;
		this.right = right;
		this.operator = operator;
	}
	
	private Expression<?> left;
	private Expression<?> right;
	private String operator;
	public String getOperator() {return this.operator;}
	
	public Expression<?> getLeftValue(){
		return (this.left);
	}
	public Expression<?> getRightValue(){
		return (this.right);
	}
	
	public void setLeftValue(Expression<?> newLeftValue){
		this.left = newLeftValue;
	}
	
	public void setRightValue(Expression<?> newRightValue){
		this.left = newRightValue;
	}
}
