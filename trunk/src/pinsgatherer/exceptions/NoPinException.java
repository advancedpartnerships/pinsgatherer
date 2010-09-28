package pinsgatherer.exceptions;

public class NoPinException extends Exception {
	public NoPinException() {
		super("Opened page didn't contain a pin, apparently.");
	}
}