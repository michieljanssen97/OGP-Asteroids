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
		Assignment<Expression> assignStat = new Assignment<>(value,"assignment",variableName);
		return new Statement<Assignment<Expression>,Function>(assignStat,sourceLocation);
	}

	@Override
	public Statement createWhileStatement(Expression condition, Statement body, SourceLocation sourceLocation) {
		SingleStatement whileStat = new SingleStatement(condition,body,"while");
		return new Statement(whileStat,sourceLocation) ;
	}

	@Override
	public Statement createBreakStatement(SourceLocation sourceLocation) {
		return new Statement<String,Function>("break",sourceLocation);
	}

	@Override
	public Statement createReturnStatement(Expression value, SourceLocation sourceLocation) {
		ExpressionStatement<Expression> returnStat = new ExpressionStatement<>(value, "return");
		return new Statement<ExpressionStatement<Expression>,Function>(returnStat,sourceLocation);
	}

	@Override
	public Statement createIfStatement(Expression condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation) {
		DoubleStatement<Expression,Statement,Function> ifStat = new DoubleStatement<>(condition,ifBody, elseBody,"if");
		return new Statement<DoubleStatement<Expression,Statement,Function>,Function>(ifStat,sourceLocation) ;
	}

	@Override
	public Statement createPrintStatement(Expression value, SourceLocation sourceLocation) {
		ExpressionStatement<Expression> printStat = new ExpressionStatement<>(value, "print");
		return new Statement<ExpressionStatement<Expression>,Function>(printStat,sourceLocation);
	}

	@Override
	public Statement createSequenceStatement(List<Statement> statements, SourceLocation sourceLocation) {
		return new Statement(statements,sourceLocation);
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
		SingleExpression<Expression> changeSignExpr = new SingleExpression(expression,"-");
		return new Expression(changeSignExpr,sourceLocation);
	}

	@Override
	public Expression createNotExpression(Expression expression, SourceLocation sourceLocation) {
		SingleExpression<Expression> notExpr = new SingleExpression<Expression>(expression,"!");
		return new Expression<SingleExpression<Expression>>(notExpr,sourceLocation);
	}

	@Override
	public Expression createDoubleLiteralExpression(double value, SourceLocation location) {
		return new Expression<Double>(value,location);
	}

	@Override
	public Expression createNullExpression(SourceLocation location) {
		SingleExpression<Expression> nullExpr = new SingleExpression<Expression>(null, "null");
		return  new Expression<SingleExpression<Expression>>(nullExpr, location);
	}

	@Override
	public Expression createSelfExpression(SourceLocation location) {
		SingleExpression<Expression> selfExpr = new SingleExpression<Expression>(null, "self");
		return  new Expression<SingleExpression<Expression>>(selfExpr, location);
	}

	@Override
	public Expression createShipExpression(SourceLocation location) {
		SingleExpression<Expression> shipExpr = new SingleExpression<Expression>(null, "ship");
		return  new Expression<SingleExpression<Expression>>(shipExpr, location);
	}

	@Override
	public Expression createAsteroidExpression(SourceLocation location) {
		SingleExpression<Expression> asteroidExpr = new SingleExpression<Expression>(null, "asteroid");
		return  new Expression<SingleExpression<Expression>>(asteroidExpr, location);
	}

	@Override
	public Expression createPlanetoidExpression(SourceLocation location) {
		SingleExpression<Expression> planetoidExpr = new SingleExpression<Expression>(null, "planetoid");
		return  new Expression<SingleExpression<Expression>>(planetoidExpr, location);
	}

	@Override
	public Expression createBulletExpression(SourceLocation location) {
		SingleExpression<Expression> bulletExpr = new SingleExpression<Expression>(null, "bullet");
		return  new Expression<SingleExpression<Expression>>(bulletExpr, location);
	}

	@Override
	public Expression createPlanetExpression(SourceLocation location) {
		SingleExpression<Expression> planetExpr = new SingleExpression<Expression>(null, "planet");
		return  new Expression<SingleExpression<Expression>>(planetExpr, location);
	}

	@Override
	public Expression createAnyExpression(SourceLocation location) {
		SingleExpression<Expression> anyExpr = new SingleExpression<Expression>(null, "any");
		return  new Expression<SingleExpression<Expression>>(anyExpr, location);
	}

	@Override
	public Expression createGetXExpression(Expression e, SourceLocation location) {
		SingleExpression<Expression> getXExpr = new SingleExpression<Expression>(e, "getx");
		return  new Expression<SingleExpression<Expression>>(getXExpr, location);
	}

	@Override
	public Expression createGetYExpression(Expression e, SourceLocation location) {
		SingleExpression<Expression> getYExpr = new SingleExpression<Expression>(e, "gety");
		return new Expression<SingleExpression<Expression>>(getYExpr, location);
	}

	@Override
	public Expression createGetVXExpression(Expression e, SourceLocation location) {
		SingleExpression<Expression> getVXExpr = new SingleExpression<Expression>(e, "getvx");
		return new Expression<SingleExpression<Expression>>(getVXExpr, location);
	}

	@Override
	public Expression createGetVYExpression(Expression e, SourceLocation location) {
		SingleExpression<Expression> getVYExpr = new SingleExpression<Expression>(e, "getvy");
		return  new Expression<SingleExpression<Expression>>(getVYExpr, location);
	}

	@Override
	public Expression createGetRadiusExpression(Expression e, SourceLocation location) {
		SingleExpression<Expression> getRadiusExpr = new SingleExpression<Expression>(e, "getradius");
		return  new Expression<SingleExpression<Expression>>(getRadiusExpr, location);
	}

	@Override
	public Expression createLessThanExpression(Expression e1, Expression e2, SourceLocation location) {
		DoubleExpression<Expression> lessExpr = new DoubleExpression<Expression>(e1,e2,"<");
		return  new Expression<DoubleExpression<Expression>>(lessExpr,location);
	}

	@Override
	public Expression createEqualityExpression(Expression e1, Expression e2, SourceLocation location) {
		DoubleExpression<Expression> equalExpr = new DoubleExpression<Expression>(e1,e2,"==");
		return new Expression<DoubleExpression<Expression>>(equalExpr,location);
	}

	@Override
	public Expression createAdditionExpression(Expression e1, Expression e2, SourceLocation location) {
		DoubleExpression<Expression> addExpr = new DoubleExpression<Expression>(e1,e2,"+");
		return new Expression<DoubleExpression<Expression>>(addExpr,location);
	}

	@Override
	public Expression createMultiplicationExpression(Expression e1, Expression e2, SourceLocation location) {
		DoubleExpression<Expression> mulExpr = new DoubleExpression<Expression>(e1,e2,"*");
		return new Expression<DoubleExpression<Expression>>(mulExpr,location);
	}

	@Override
	public Expression createSqrtExpression(Expression e, SourceLocation location) {
		SingleExpression<Expression> sqrtExpr = new SingleExpression<Expression>(e, "sqrt");
		return new Expression<SingleExpression<Expression>>(sqrtExpr, location);
	}

	@Override
	public Expression createGetDirectionExpression(SourceLocation location) {
		SingleExpression<Expression> dirExpr = new SingleExpression<Expression>(null, "getdir");
		return new Expression<SingleExpression<Expression>>(dirExpr, location);
	}

	@Override
	public Statement createThrustOnStatement(SourceLocation location) {
		return new Statement<String,Function>("thrust_on",location);
	}

	@Override
	public Statement createThrustOffStatement(SourceLocation location) {
		return new Statement<String,Function>("thrust_off",location);
	}

	@Override
	public Statement createFireStatement(SourceLocation location) {
		return new Statement<String,Function>("fire",location);
	}

	@Override
	public Statement createTurnStatement(Expression angle, SourceLocation location) {
		ExpressionStatement<Expression> turnStat = new ExpressionStatement<>(angle, "turn");
		return new Statement<ExpressionStatement<Expression>,Function>(turnStat,location);
	}

	@Override
	public Statement createSkipStatement(SourceLocation location) {
		return new Statement<String,Function>("skip",location);
	}
	
}