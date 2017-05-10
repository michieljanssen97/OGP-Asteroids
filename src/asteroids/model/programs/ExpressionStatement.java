package asteroids.model.programs;

public class ExpressionStatement<E> {

	public ExpressionStatement(E expression, String stating){
		this.expression = expression;
		this.stating = stating;
	}
	private E expression;
	private String stating;
	
	public String getStating() { return this.stating; }

	public Expression<?> getExpression() { return ((Expression<?>) this.expression) ;}
	
	public void setExpression(E expression) {
		this.expression = expression;
	}	
}
