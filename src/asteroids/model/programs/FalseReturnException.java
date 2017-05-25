package asteroids.model.programs;

public class FalseReturnException extends Exception {
	
	private String message;
	private static final long serialVersionUID = 2003001L;  
	
	public FalseReturnException(String message) {
		this.message = message;
	}
	
	public String getMessage(){
		return this.message;
	}
}