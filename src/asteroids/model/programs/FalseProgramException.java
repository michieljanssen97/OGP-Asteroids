package asteroids.model.programs;

public class FalseProgramException extends Exception {
	
	private String message;
	private static final long serialVersionUID = 2003001L;    
	
	public FalseProgramException(String message) {
		this.message = message;
	}
	
	public String getMessage(){
		return this.message;
	}
}
