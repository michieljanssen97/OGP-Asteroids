package asteroids.model.programs;

public class ProgramException extends Exception {
	
	protected String message;
	protected static final long serialVersionUID = 2003001L; 
	
	public ProgramException() {}
	
	public ProgramException(String message) {
		super(message);
	}
	
	public static class NoMoreTime extends ProgramException {
		protected static final long serialVersionUID = 2003001L; 
		public NoMoreTime(){super();}
	}
	
	public static class Break extends Exception {
		protected static final long serialVersionUID = 2003001L; 
		public Break() {super();}
	}
	
	public static class FalseProgram extends ProgramException {
		protected static final long serialVersionUID = 2003001L; 
		public FalseProgram(String message) {
			super(message);
		}
	}
	
	public static class FalseReturn extends ProgramException {
		protected static final long serialVersionUID = 2003001L; 
		public FalseReturn(String message) {
			super(message);
		}
	}
}
