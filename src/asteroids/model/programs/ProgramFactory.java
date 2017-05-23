package asteroids.model.programs;

import java.util.List;

import asteroids.model.*;
import asteroids.model.programs.expressions.ContainerExpression;
import asteroids.model.programs.expressions.BinaryOperatorExpression;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.FunctionCallExpression;
import asteroids.model.programs.expressions.EntityExpression;
import asteroids.model.programs.expressions.UnaryOperatorExpression;
import asteroids.model.programs.expressions.VariableExpression;
import asteroids.model.programs.statements.AssignmentStatement;
import asteroids.model.programs.statements.ExpressionStatement;
import asteroids.model.programs.statements.FunctionStatement;
import asteroids.model.programs.statements.IfStatement;
import asteroids.model.programs.statements.SequenceStatement;
import asteroids.model.programs.statements.Statement;
import asteroids.model.programs.statements.ActionStatement;
import asteroids.model.programs.statements.WhileStatement;
import asteroids.part3.programs.IProgramFactory;
import asteroids.part3.programs.SourceLocation;


public class ProgramFactory<E, S, F, P> implements IProgramFactory<Expression, Statement, FunctionStatement, Program>{

	@Override
	public Program createProgram(List<FunctionStatement> functions, Statement main) {
		return new Program<FunctionStatement, Statement>(functions,main);
	}

	@Override
	public FunctionStatement createFunctionDefinition(String functionName, Statement body, SourceLocation sourceLocation) {
		return new FunctionStatement(functionName, body, sourceLocation);
	}

	@Override
	public Statement createAssignmentStatement(String variableName, Expression value, SourceLocation sourceLocation) {
		return new AssignmentStatement(variableName, value, sourceLocation);
	}

	@Override
	public Statement createWhileStatement(Expression condition, Statement body, SourceLocation sourceLocation) {
		return new WhileStatement(condition, body, sourceLocation);
	}

	@Override
	public Statement createBreakStatement(SourceLocation sourceLocation) {
		return new ActionStatement("break",sourceLocation);
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
		return new VariableExpression(variableName, sourceLocation);
	}

	@Override
	public Expression createReadParameterExpression(String parameterName, SourceLocation sourceLocation) {
		return new VariableExpression(parameterName, sourceLocation);
	}

	@Override
	public Expression createFunctionCallExpression(String functionName, List<Expression> actualArgs, SourceLocation sourceLocation) {
		return new FunctionCallExpression(functionName, actualArgs, sourceLocation);
	}

	@Override
	public Expression createChangeSignExpression(Expression expression, SourceLocation sourceLocation) {
		return new UnaryOperatorExpression<Double>(expression,"-", sourceLocation);
	}

	@Override
	public Expression createNotExpression(Expression expression, SourceLocation sourceLocation) {
		return new UnaryOperatorExpression<Boolean>(expression,"!", sourceLocation);
	}

	@Override
	public Expression createDoubleLiteralExpression(double value, SourceLocation location) {
		return new ContainerExpression<Double>(value,location);
	}

	@Override
	public Expression createNullExpression(SourceLocation location) {
		return new EntityExpression<>("null", location);
	}

	@Override
	public Expression createSelfExpression(SourceLocation location) {
		return new EntityExpression<Ship>("self", location);
	}

	@Override
	public Expression createShipExpression(SourceLocation location) {
		return new EntityExpression<Ship>("ship", location);
	}

	@Override
	public Expression createAsteroidExpression(SourceLocation location) {
		return new EntityExpression<Asteroid>("asteroid", location);
	}

	@Override
	public Expression createPlanetoidExpression(SourceLocation location) {
		return new EntityExpression<Planetoid>("planetoid", location);
	}

	@Override
	public Expression createBulletExpression(SourceLocation location) {
		return new EntityExpression<Bullet>("bullet", location);
	}

	@Override
	public Expression createPlanetExpression(SourceLocation location) {
		return new EntityExpression<MinorPlanet>("planet", location);
	}

	@Override
	public Expression createAnyExpression(SourceLocation location) {
		return new EntityExpression<Entity>("any", location);
	}

	@Override
	public Expression createGetXExpression(Expression e, SourceLocation location) {
		return new UnaryOperatorExpression<Double>(e, "getx", location);
	}

	@Override
	public Expression createGetYExpression(Expression e, SourceLocation location) {
		return new UnaryOperatorExpression<Double>(e, "gety", location);
	}

	@Override
	public Expression createGetVXExpression(Expression e, SourceLocation location) {
		return new UnaryOperatorExpression<Double>(e, "getvx", location);
	}

	@Override
	public Expression createGetVYExpression(Expression e, SourceLocation location) {
		return new UnaryOperatorExpression<Double>(e, "getvy", location);
	}

	@Override
	public Expression createGetRadiusExpression(Expression e, SourceLocation location) {
		return new UnaryOperatorExpression<Double>(e, "getradius", location);
	}

	@Override
	public Expression createLessThanExpression(Expression e1, Expression e2, SourceLocation location) {
		return new BinaryOperatorExpression<Boolean>(e1,e2,"<", location);
	}

	@Override
	public Expression createEqualityExpression(Expression e1, Expression e2, SourceLocation location) {
		return new BinaryOperatorExpression<Boolean>(e1,e2,"==", location);
	}

	@Override
	public Expression createAdditionExpression(Expression e1, Expression e2, SourceLocation location) {
		return new BinaryOperatorExpression<Double>(e1,e2,"+", location);
	}

	@Override
	public Expression createMultiplicationExpression(Expression e1, Expression e2, SourceLocation location) {
		return new BinaryOperatorExpression<Double>(e1,e2,"*", location);
	}

	@Override
	public Expression createSqrtExpression(Expression e, SourceLocation location) {
		return new UnaryOperatorExpression<Double>(e, "sqrt", location);
	}

	@Override
	public Expression createGetDirectionExpression(SourceLocation location) {
		return new UnaryOperatorExpression<Double>(null, "getdir", location);
	}

	@Override
	public Statement createThrustOnStatement(SourceLocation location) {
		return new ActionStatement("thrust_on",location);
	}

	@Override
	public Statement createThrustOffStatement(SourceLocation location) {
		return new ActionStatement("thrust_off",location);
	}

	@Override
	public Statement createFireStatement(SourceLocation location) {
		return new ActionStatement("fire",location);
	}

	@Override
	public Statement createTurnStatement(Expression angle, SourceLocation location) {
		return new ExpressionStatement(angle, "turn", location);
	}

	@Override
	public Statement createSkipStatement(SourceLocation location) {
		return new ActionStatement("skip",location);
	}
	
}