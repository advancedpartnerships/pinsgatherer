package pinsgatherer.exceptions;

@SuppressWarnings("serial")
public class ElementNotFoundException extends Exception {
	
	public ElementNotFoundException(String element) {
		super(element + " not found.");
	}

}
