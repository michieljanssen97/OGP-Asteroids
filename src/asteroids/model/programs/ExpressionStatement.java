package asteroids.model.programs;

public class ExpressionStatement<E> {

	public ExpressionStatement(Expression expression, String stating){
		this.expression = expression;
		this.stating = stating;
	}
	private Expression expression;
	private String stating;
	
	public String getStating() {return this.stating;}

	public Expression<?> getExpression() {return this.expression;}
	
	public void setExpression(Expression expression) {
		this.expression = expression;
	}	
}
