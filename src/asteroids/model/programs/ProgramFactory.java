package asteroids.model.programs;

import java.util.List;

import asteroids.model.Program;
import asteroids.model.programs.*;
import asteroids.part3.programs.IProgramFactory;
import asteroids.part3.programs.SourceLocation;


public class ProgramFactory<E, S, F, P, T> implements IProgramFactory<E, S, F, P>{

	@Override
	public P createProgram(List<F> functions, S main) {
		return (P) new Program<F,S>(functions,main);
	}

	@Override
	public F createFunctionDefinition(String functionName, S body, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createAssignmentStatement(String variableName, E value, SourceLocation sourceLocation) {
		Assignment<E> assignStat = new Assignment<>(value,"assignment",variableName);
		return (S) new Statement<Assignment<E>,F>(assignStat,sourceLocation);
	}

	@Override
	public S createWhileStatement(E condition, S body, SourceLocation sourceLocation) {
		SingleStatement<E,S,F> whileStat = new SingleStatement<E,S,F>(condition,body,"while");
		return (S) new Statement<SingleStatement<E,S,F>,F>(whileStat,sourceLocation) ;
	}

	@Override
	public S createBreakStatement(SourceLocation sourceLocation) {
		return (S) new Statement<String,F>("break",sourceLocation);
	}

	@Override
	public S createReturnStatement(E value, SourceLocation sourceLocation) {
		ExpressionStatement<E> returnStat = new ExpressionStatement<>(value, "return");
		return (S) new Statement<ExpressionStatement<E>,F>(returnStat,sourceLocation);
	}

	@Override
	public S createIfStatement(E condition, S ifBody, S elseBody, SourceLocation sourceLocation) {
		DoubleStatement<E,S,F> ifStat = new DoubleStatement<>(condition,ifBody, elseBody,"if");
		return (S) new Statement<DoubleStatement<E,S,F>,F>(ifStat,sourceLocation) ;
	}

	@Override
	public S createPrintStatement(E value, SourceLocation sourceLocation) {
		ExpressionStatement<E> printStat = new ExpressionStatement<>(value, "print");
		return (S) new Statement<ExpressionStatement<E>,F>(printStat,sourceLocation);
	}

	@Override
	public S createSequenceStatement(List<S> statements, SourceLocation sourceLocation) {
		return (S) new Statement<List<S>,F>(statements,sourceLocation);
	}

	@Override
	public E createReadVariableExpression(String variableName, SourceLocation sourceLocation) {
		Variable<T> readVariableExpr = new Variable<T>(variableName);
		return (E) new Expression<Variable<T>>(readVariableExpr, sourceLocation);
	}

	@Override
	public E createReadParameterExpression(String parameterName, SourceLocation sourceLocation) {
		Variable<T> readParameterExpr = new Variable<T>(parameterName);
		return (E) new Expression<Variable<T>>(readParameterExpr, sourceLocation);
	}

	@Override
	public E createFunctionCallExpression(String functionName, List<E> actualArgs, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E createChangeSignExpression(E expression, SourceLocation sourceLocation) {
		SingleExpression<E> changeSignExpr = new SingleExpression<E>(expression,"-");
		return (E) new Expression<SingleExpression<E>>(changeSignExpr,sourceLocation);
	}

	@Override
	public E createNotExpression(E expression, SourceLocation sourceLocation) {
		SingleExpression<E> notExpr = new SingleExpression<E>(expression,"!");
		return (E) new Expression<SingleExpression<E>>(notExpr,sourceLocation);
	}

	@Override
	public E createDoubleLiteralExpression(double value, SourceLocation location) {
		return (E) new Expression<Double>(value,location);
	}

	@Override
	public E createNullExpression(SourceLocation location) {
		SingleExpression<E> nullExpr = new SingleExpression<E>(null, "null");
		return (E) new Expression<SingleExpression<E>>(nullExpr, location);
	}

	@Override
	public E createSelfExpression(SourceLocation location) {
		SingleExpression<E> selfExpr = new SingleExpression<E>(null, "self");
		return (E) new Expression<SingleExpression<E>>(selfExpr, location);
	}

	@Override
	public E createShipExpression(SourceLocation location) {
		SingleExpression<E> shipExpr = new SingleExpression<E>(null, "ship");
		return (E) new Expression<SingleExpression<E>>(shipExpr, location);
	}

	@Override
	public E createAsteroidExpression(SourceLocation location) {
		SingleExpression<E> asteroidExpr = new SingleExpression<E>(null, "asteroid");
		return (E) new Expression<SingleExpression<E>>(asteroidExpr, location);
	}

	@Override
	public E createPlanetoidExpression(SourceLocation location) {
		SingleExpression<E> planetoidExpr = new SingleExpression<E>(null, "planetoid");
		return (E) new Expression<SingleExpression<E>>(planetoidExpr, location);
	}

	@Override
	public E createBulletExpression(SourceLocation location) {
		SingleExpression<E> bulletExpr = new SingleExpression<E>(null, "bullet");
		return (E) new Expression<SingleExpression<E>>(bulletExpr, location);
	}

	@Override
	public E createPlanetExpression(SourceLocation location) {
		SingleExpression<E> planetExpr = new SingleExpression<E>(null, "planet");
		return (E) new Expression<SingleExpression<E>>(planetExpr, location);
	}

	@Override
	public E createAnyExpression(SourceLocation location) {
		SingleExpression<E> anyExpr = new SingleExpression<E>(null, "any");
		return (E) new Expression<SingleExpression<E>>(anyExpr, location);
	}

	@Override
	public E createGetXExpression(E e, SourceLocation location) {
		SingleExpression<E> getXExpr = new SingleExpression<E>(e, "getx");
		return (E) new Expression<SingleExpression<E>>(getXExpr, location);
	}

	@Override
	public E createGetYExpression(E e, SourceLocation location) {
		SingleExpression<E> getYExpr = new SingleExpression<E>(e, "gety");
		return (E) new Expression<SingleExpression<E>>(getYExpr, location);
	}

	@Override
	public E createGetVXExpression(E e, SourceLocation location) {
		SingleExpression<E> getVXExpr = new SingleExpression<E>(e, "getvx");
		return (E) new Expression<SingleExpression<E>>(getVXExpr, location);
	}

	@Override
	public E createGetVYExpression(E e, SourceLocation location) {
		SingleExpression<E> getVYExpr = new SingleExpression<E>(e, "getvy");
		return (E) new Expression<SingleExpression<E>>(getVYExpr, location);
	}

	@Override
	public E createGetRadiusExpression(E e, SourceLocation location) {
		SingleExpression<E> getRadiusExpr = new SingleExpression<E>(e, "getradius");
		return (E) new Expression<SingleExpression<E>>(getRadiusExpr, location);
	}

	@Override
	public E createLessThanExpression(E e1, E e2, SourceLocation location) {
		DoubleExpression<E,T> lessExpr = new DoubleExpression<E,T>(e1,e2,"<");
		return (E) new Expression<DoubleExpression<E,T>>(lessExpr,location);
	}

	@Override
	public E createEqualityExpression(E e1, E e2, SourceLocation location) {
		DoubleExpression<E,T> equalExpr = new DoubleExpression<E,T>(e1,e2,"==");
		return (E) new Expression<DoubleExpression<E,T>>(equalExpr,location);
	}

	@Override
	public E createAdditionExpression(E e1, E e2, SourceLocation location) {
		DoubleExpression<E,T> addExpr = new DoubleExpression<E,T>(e1,e2,"+");
		return (E) new Expression<DoubleExpression<E,T>>(addExpr,location);
	}

	@Override
	public E createMultiplicationExpression(E e1, E e2, SourceLocation location) {
		DoubleExpression<E,T> mulExpr = new DoubleExpression<E,T>(e1,e2,"*");
		return (E) new Expression<DoubleExpression<E,T>>(mulExpr,location);
	}

	@Override
	public E createSqrtExpression(E e, SourceLocation location) {
		SingleExpression<E> sqrtExpr = new SingleExpression<E>(e, "sqrt");
		return (E) new Expression<SingleExpression<E>>(sqrtExpr, location);
	}

	@Override
	public E createGetDirectionExpression(SourceLocation location) {
		SingleExpression<E> dirExpr = new SingleExpression<E>(null, "getdir");
		return (E) new Expression<SingleExpression<E>>(dirExpr, location);
	}

	@Override
	public S createThrustOnStatement(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createThrustOffStatement(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createFireStatement(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createTurnStatement(E angle, SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public S createSkipStatement(SourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}
	
}