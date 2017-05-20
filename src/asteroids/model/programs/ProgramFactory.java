package asteroids.model.programs;

import java.util.List;

import asteroids.model.Program;
import asteroids.part3.programs.IProgramFactory;
import asteroids.part3.programs.SourceLocation;


public class ProgramFactory<E, S, F, P> implements IProgramFactory<Expression, Statement, Function, Program>{

	@Override
	public Program<Function, Statement<E,F>> createProgram(List<Function> functions, Statement main) {
		return new Program<Function, Statement<E,F>>(functions,main);
	}

	@Override
	public Function createFunctionDefinition(String functionName, Statement body, SourceLocation sourceLocation) {
		return new Function(functionName, body, sourceLocation);
	}

	@Override
	public Statement createAssignmentStatement(String variableName, Expression value, SourceLocation sourceLocation) {
		return new Assignment(variableName, value, sourceLocation);
	}

	@Override
	public Statement createWhileStatement(Expression condition, Statement body, SourceLocation sourceLocation) {
		return new WhileStatement(condition, body, sourceLocation);
	}

	@Override
	public Statement createBreakStatement(SourceLocation sourceLocation) {
		return new StringStatement("break",sourceLocation);
	}

	@Override
	public Statement createReturnStatement(Expression value, SourceLocation sourceLocation) {
		return new ExpressionStatement(value, "return", sourceLocation);
	}

	@Override
	public Statement createIfStatement(Expression condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation) {
		return new IfStatement(condition, ifBody, elseBody, sourceLocation);
	}

	@Override
	public Statement createPrintStatement(Expression value, SourceLocation sourceLocation) {
		return new ExpressionStatement(value, "print", sourceLocation);
	}

	@Override
	public Statement createSequenceStatement(List<Statement> statements, SourceLocation sourceLocation) {
		return new SequenceStatement(statements,sourceLocation);
	}

	@Override
	public Expression createReadVariableExpression(String variableName, SourceLocation sourceLocation) {
		return new Expression<String>(variableName, sourceLocation);
	}

	@Override
	public Expression createReadParameterExpression(String parameterName, SourceLocation sourceLocation) {
		return new Expression<String>(parameterName, sourceLocation);
	}

	@Override
	public Expression createFunctionCallExpression(String functionName, List<Expression> actualArgs, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createChangeSignExpression(Expression expression, SourceLocation sourceLocation) {
		return new SingleExpression(expression,"-", sourceLocation);
	}

	@Override
	public Expression createNotExpression(Expression expression, SourceLocation sourceLocation) {
		return new SingleExpression<Expression>(expression,"!", sourceLocation);
	}

	@Override
	public Expression createDoubleLiteralExpression(double value, SourceLocation location) {
		return new Expression<Double>(value,location);
	}

	@Override
	public Expression createNullExpression(SourceLocation location) {
		return new SingleExpression<Expression>(null, "null", location);
	}

	@Override
	public Expression createSelfExpression(SourceLocation location) {
		return new SingleExpression<Expression>(null, "self", location);
	}

	@Override
	public Expression createShipExpression(SourceLocation location) {
		return new SingleExpression<Expression>(null, "ship", location);
	}

	@Override
	public Expression createAsteroidExpression(SourceLocation location) {
		return new SingleExpression<Expression>(null, "asteroid", location);
	}

	@Override
	public Expression createPlanetoidExpression(SourceLocation location) {
		return new SingleExpression<Expression>(null, "planetoid", location);
	}

	@Override
	public Expression createBulletExpression(SourceLocation location) {
		return new SingleExpression<Expression>(null, "bullet", location);
	}

	@Override
	public Expression createPlanetExpression(SourceLocation location) {
		return new SingleExpression<Expression>(null, "planet", location);
	}

	@Override
	public Expression createAnyExpression(SourceLocation location) {
		return new SingleExpression<Expression>(null, "any", location);
	}

	@Override
	public Expression createGetXExpression(Expression e, SourceLocation location) {
		return new SingleExpression<Expression>(e, "getx", location);
	}

	@Override
	public Expression createGetYExpression(Expression e, SourceLocation location) {
		return new SingleExpression<Expression>(e, "gety", location);
	}

	@Override
	public Expression createGetVXExpression(Expression e, SourceLocation location) {
		return new SingleExpression<Expression>(e, "getvx", location);
	}

	@Override
	public Expression createGetVYExpression(Expression e, SourceLocation location) {
		return new SingleExpression<Expression>(e, "getvy", location);
	}

	@Override
	public Expression createGetRadiusExpression(Expression e, SourceLocation location) {
		return new SingleExpression<Expression>(e, "getradius", location);
	}

	@Override
	public Expression createLessThanExpression(Expression e1, Expression e2, SourceLocation location) {
		return new DoubleExpression(e1,e2,"<", location);
	}

	@Override
	public Expression createEqualityExpression(Expression e1, Expression e2, SourceLocation location) {
		return new DoubleExpression<Expression>(e1,e2,"==", location);
	}

	@Override
	public Expression createAdditionExpression(Expression e1, Expression e2, SourceLocation location) {
		return new DoubleExpression<Expression>(e1,e2,"+", location);
	}

	@Override
	public Expression createMultiplicationExpression(Expression e1, Expression e2, SourceLocation location) {
		return new DoubleExpression<Expression>(e1,e2,"*", location);
	}

	@Override
	public Expression createSqrtExpression(Expression e, SourceLocation location) {
		return new SingleExpression<Expression>(e, "sqrt", location);
	}

	@Override
	public Expression createGetDirectionExpression(SourceLocation location) {
		return new SingleExpression<Expression>(null, "getdir", location);
	}

	@Override
	public Statement createThrustOnStatement(SourceLocation location) {
		return new StringStatement("thrust_on",location);
	}

	@Override
	public Statement createThrustOffStatement(SourceLocation location) {
		return new StringStatement("thrust_off",location);
	}

	@Override
	public Statement createFireStatement(SourceLocation location) {
		return new StringStatement("fire",location);
	}

	@Override
	public Statement createTurnStatement(Expression angle, SourceLocation location) {
		return new ExpressionStatement(angle, "turn", location);
	}

	@Override
	public Statement createSkipStatement(SourceLocation location) {
		return new StringStatement("skip",location);
	}
	
}