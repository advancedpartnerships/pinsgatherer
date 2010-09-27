package pinsgatherer.exceptions;

@SuppressWarnings("serial")
public class NoEmailException extends Exception {
	
	public NoEmailException() {
		super("Email not found");
	}

}
