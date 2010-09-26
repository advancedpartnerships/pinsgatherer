package pinsgatherer.robot;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pinsgatherer.data.Form;
import pinsgatherer.data.FormGenerator;
import pinsgatherer.server.ServerManager;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;

/**
 *
 * This class simulates a person opening http://www.elsantoregalapines.com/,
 * completing a form and submitting it. 
 * 
 */

public class PinsGatherer extends SeleneseTestCase {

	/**
	 * 
	 * This method runs before the class is executed. It starts the selenium server
	 * and creates a new browser instance with a base url.
	 * 
	 */
	
	@BeforeClass
	public void setUp() {
		// Create a browser instance
		try {
			ServerManager.start();
			selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://www.elsantoregalapines.com");
			selenium.start();
			selenium.windowFocus();
			selenium.windowMaximize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * This method runs after the class is executed. It stops the selenium server
	 * and closes the browser.
	 * 
	 */
	
	@AfterClass
	public void terminate() {
		// Close window
		selenium.stop();
		ServerManager.stop();
	}

	/**
	 * 
	 * Robot code for creating fake users and submitting fake data.
	 * 
	 */

	@Test
	public void testPinsGatherer() {
		// Open http://www.elsantoregalapines.com/
		selenium.open("/");

		// Wait for page to load
		selenium.waitForPageToLoad("30000");

		// Flash code from here

		// Create random forms
		
		final List<Form> forms = FormGenerator.generateForms(10);
		for (Form form : forms) {
			completeForm(form);
		}

	}

	/**
	 * 
	 * Completes and submits flash form.
	 * 
	 */

	private void completeForm(Form form) {
		// Fill fields

		// Accept terms

		// Submit form
	}

}
