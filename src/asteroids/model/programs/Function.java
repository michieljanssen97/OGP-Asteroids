package asteroids.model.programs;

import asteroids.part3.programs.SourceLocation;

public class Function {
	
	String name;
	Statement body;
	SourceLocation sourceLocation;

	public Function(String functionName, Statement body, SourceLocation sourceLocation) {
		this.name = functionName;
		this.body = body;
		this.sourceLocation = sourceLocation;
	}

}
