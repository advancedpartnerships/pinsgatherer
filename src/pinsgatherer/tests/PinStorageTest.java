package pinsgatherer.tests;

import java.io.File;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pinsgatherer.helper.XMLHelper;
import pinsgatherer.output.PinStorage;

import junit.framework.TestCase;

public class PinStorageTest extends TestCase {
	
	@Test
	public void testAddPin() {
		// Create a pin
		final String pin = "RFJTHE4HT24";
		
		// Store the pin
		PinStorage.addPin("pepito@mailinatore", pin);
		
		// Validate pin inside document
		// Get document
		Document document = PinStorage.getDocument();
		// Validate doc was created
		assertFalse(document == null);
		
		// Get pins
		Element root = document.getDocumentElement();
		NodeList pins = root.getChildNodes();
		assertFalse(pins.getLength() == 0);
		
		// Get first pin
		Node pinNode = pins.item(0);
		// Validate content
		assertEquals(pinNode.getTextContent(), pin);
		
		// Write the document into the file
		XMLHelper.writeXmlFile(document, "pins.xml");
		// Check the file was created
		assertTrue(new File("pins.xml").exists());
	}

}
