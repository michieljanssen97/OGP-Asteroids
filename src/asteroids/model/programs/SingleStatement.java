package asteroids.model.programs;

public class SingleStatement<E,S,F> {

	public SingleStatement(E expression, S statement,String stating){
		this.expression = expression;
		this.stating = stating;
		this.statement = statement;
	}

	private E expression;
	private String stating;
	private S statement;
	
	@SuppressWarnings("unchecked")
	public Statement<E,F> getStatement() { return (Statement<E,F>)this.statement; }
	public String getStating() { return this.stating; }
	public Expression<?> getExpression() { return ((Expression<?>) this.expression) ;}	
}
