package pinsgatherer.robot;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pinsgatherer.data.Form;
import pinsgatherer.data.FormGenerator;
import pinsgatherer.server.ServerManager;
import pinsgatherer.sikuli.SikuliScripts;

import java.util.List;

/**
 * This class simulates a person opening http://www.elsantoregalapines.com/,
 * completing a form and submitting it.
 */

public class PinsGatherer extends SeleneseTestCase {
	
	// Available pins.
	private static final int MAX_PINS = 150000;

    /**
     * This method runs before the class is executed. It starts the selenium server
     * and creates a new browser instance with a base url.
     */

    @BeforeClass
    public void setUp() {
        // Create a browser instance
        try {
            ServerManager.start();
            selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://www.elsantoregalapines.com");
            selenium.start();
            selenium.windowFocus();
            selenium.windowMaximize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method runs after the class is executed. It stops the selenium server
     * and closes the browser.
     */

    @AfterClass
    public void terminate() {
        // Close window
        selenium.stop();
        ServerManager.stop();
    }

    /**
     * Robot code for creating fake users and submitting fake data.
     */

    @Test
    public void testPinsGatherer() {
        // Create random forms
        final List<Form> forms = FormGenerator.generateForms(MAX_PINS);
        
        /*for (Form form : forms) {
        	// Open http://www.elsantoregalapines.com/
            selenium.open("/");

            // Wait for page to load
            selenium.waitForPageToLoad("30000");
            
            completeForm(form);
        }*/
        selenium.open("/");
        selenium.waitForPageToLoad("30000");
        completeForm(forms.get(0));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {}
    }

    /**
     * Completes and submits flash form.
     */

    /**
     * Call after http://www.elsantoregalapines.com/ is opened.
     *
     * @param form A form with valid and unique dni, mail and cellphone fields.
     * @return true if feasible to success, false otherwise.
     */
    private boolean completeForm(Form form) {
        return SikuliScripts.getScripts().completeForm(computeParams(form));
	}

    private String computeParams(Form form) {
        return form.getSurname() + " " + form.getName() + " " + form.getDni() + " " + form.getMail() + " " + form.getBirthdateDay() + " " + form.getBirthdateMonth() + " " + form.getBirthdateYear() + " " + form.getCellPhone() + " " + form.getSport() + " " + form.getHobbieSport();
    }

    /**
     * Call after the emailed url is opened
     * @return A pin, or null if operation failed.
     */
    private String recoverPin() {
        return SikuliScripts.getScripts().recoverPin();
	}
    
    private void collectPinFromEmail(Form form) {
    	
    	// Get form's email
    	String email = form.getMail();
    	
    	// Build email url
    	String url = "http://" + email + ".mailinator.com";
    	
    	
    	
    }
}
