package asteroids.model.programs;

import asteroids.model.Entity;
import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.part3.programs.SourceLocation;

public class Expression<T> {
	
	public Expression(T value,SourceLocation sourceLocation) {
		this.value = value;
		this.sourceLocation = sourceLocation;
	}
	protected T value;
	public T getValue() { return this.value; }
	public void setValue(T value) { this.value = value; }
	private SourceLocation sourceLocation;
	public SourceLocation getSourceLocation() { return this.sourceLocation; }
	
	@SuppressWarnings("unchecked")
	public Expression<?> read(Program program) throws FalseProgramException {
		if (this.getValue() instanceof Variable) {
			Variable<T> variable = (Variable<T>)this.getValue();
			if (variable.getVariableType() == Type.Boolean) {
				return new Expression<T>(variable.getVariableType(),getSourceLocation());
			}
			else if (variable.getVariableType() == Type.Entity) {
				return new Expression<T>(variable.getVariableType(),getSourceLocation());
			}
			else if (variable.getVariableType() == Type.Double) {
				return new Expression<T>(variable.getVariableType(),getSourceLocation());
			}
			else if (variable.getVariableType() == null) {
				return new Expression<T>(null, getSourceLocation());
			}
			else throw new FalseProgramException("No such type");
			
		}
		else return this;
	}
	
	@SuppressWarnings("unchecked")
	public Expression<Double> calculationExpression(Ship ship, World world,Program program) throws FalseProgramException{
		if (!((this.getValue() instanceof Double) || (this.getValue() instanceof SingleExpression) || (this.getValue() instanceof DoubleExpression)))
			throw new FalseProgramException("Didn't receive a valid expression.");
		else if (this.getValue() instanceof Double)
			return (Expression<Double>) this;
		else if (this.getValue() instanceof SingleExpression){
			SingleExpression<Expression<T>> expression =  (SingleExpression<Expression<T>>)(this.getValue());
			if (expression.getOperator().equals("sqrt")){
				Expression<Double> sqrtExpression = (expression.getValue().read(program).calculationExpression(ship, world, program) );
				return new Expression<Double>(Math.sqrt(sqrtExpression.getValue()), getSourceLocation());
			}
			if (expression.getOperator().equals("getx")) {
				Expression<Entity> getXEntity = (expression.getValue().read(program).searchEntity(world,ship, program) );
				return new Expression<Double>((double)getXEntity.getValue().getPositionX(), getSourceLocation());
			}
			if (expression.getOperator().equals("gety")) {
				Expression<Entity> getYEntity = (expression.getValue().read(program).searchEntity(world,ship, program) );
				return new Expression<Double>((double)getYEntity.getValue().getPositionY(), getSourceLocation());
			}
			if (expression.getOperator().equals("getvx")) {
				Expression<Entity> getVXEntity = (expression.getValue().read(program).searchEntity(world,ship, program) );
				return new Expression<Double>((double)getVXEntity.getValue().getVelocityX(), getSourceLocation());
			}
			if (expression.getOperator().equals("getvy")) {
				Expression<Entity> getVYEntity = (expression.getValue().read(program).searchEntity(world,ship, program) );
				return new Expression<Double>((double)getVYEntity.getValue().getVelocityY(), getSourceLocation());
			}
			if (expression.getOperator().equals("getradius")) {
				Expression<Entity> getRadius = (expression.getValue().read(program).searchEntity(world,ship, program) );
				return new Expression<Double>((double)getRadius.getValue().getRadius(), getSourceLocation());
			}
			if (expression.getOperator().equals("getdir")) {
				Expression<Entity> getDirection = (expression.getValue().read(program).searchEntity(world,ship, program) );
				return new Expression<Double>((double)getDirection.getValue().getOrientation(), getSourceLocation());
			}
			
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public Expression<Entity> searchEntity(World world, Entity entity, Program program) throws FalseProgramException {
		if ( ! ((this.getValue() instanceof SingleExpression) || (this.getValue() instanceof Entity) || (this.getValue() instanceof DoubleExpression) || (this.getValue() == null))){
			throw new FalseProgramException("search for Creature impossible");
		}
		else if	(this.getValue() instanceof Entity) {
			
		}
		else{
			
		}
		
	}
}
