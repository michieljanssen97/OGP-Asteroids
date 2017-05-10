package asteroids.model.programs;

public class DoubleStatement<E,S,F> {

	public DoubleStatement(E condition, S left,S right,String stating) {
		this.condition = condition;
		this.left = left;
		this.right = right;
		this.stating = stating;
	}
	
	private E condition;
	private S left;
	private S right;
	private String stating;

	
	public Expression<?> getCondition() { return ((Expression<?>) this.condition) ;}	
	@SuppressWarnings("unchecked")
	public Statement<E,F> getLeft() { return ((Statement<E,F>) this.left) ;}
	@SuppressWarnings("unchecked")
	public Statement<E,F> getRight() { return ((Statement<E,F>) this.right); }
	public String getStating() { return this.stating; }
}
