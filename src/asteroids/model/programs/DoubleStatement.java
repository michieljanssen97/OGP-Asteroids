package asteroids.model.programs;

public class DoubleStatement<E,S,F> {

	public DoubleStatement(Expression<?> condition, Statement<E,F> left, Statement<E,F> right, String stating) {
		this.condition = condition;
		this.left = left;
		this.right = right;
		this.stating = stating;
	}
	
	private Expression<?> condition;
	private Statement<E,F> left;
	private Statement<E,F> right;
	private String stating;

	
	public Expression<?> getCondition() { return this.condition;}	
	public Statement<E,F> getLeft() { return this.left ;}
	public Statement<E,F> getRight() { return this.right; }
	public String getStating() { return this.stating; }
}
