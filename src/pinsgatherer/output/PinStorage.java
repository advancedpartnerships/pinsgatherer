package pinsgatherer.output;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pinsgatherer.helper.XMLHelper;

public class PinStorage {
	
	private static Document document = null;
	
	public static void addPin(String mail, String pin) {
		// Get the pins document
		Document document = getDocument();
		
		// Get root element
		Element root = document.getDocumentElement();
		
		// Create new pin element with the pin info
		Element newElement = document.createElement("pin");
		newElement.setAttribute("email", mail);
		newElement.setTextContent(pin);
		
		// Append
		root.appendChild(newElement);
		
		XMLHelper.writeXmlFile(document, "pins.xml");
	}
	
	// Kind of Singleton for the document, create on demand
	public static Document getDocument() {
		if (document == null) {
			document = XMLHelper.createNewDocument();
		}
		return document;
	}

	public static void setDocument(Document document) {
		PinStorage.document = document;
	}

}
