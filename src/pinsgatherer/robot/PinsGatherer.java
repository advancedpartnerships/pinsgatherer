package pinsgatherer.robot;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pinsgatherer.data.Form;
import pinsgatherer.data.FormGenerator;
import pinsgatherer.output.PinStorage;
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
        
        for (Form form : forms) {
        	// Open http://www.elsantoregalapines.com/
            selenium.open("/");

            // Wait for page to load
            selenium.waitForPageToLoad("30000");
            
            completeForm(form);
            
            storePin(form);
        }
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
    
    private void storePin(Form form) {
    	
    	// Get form's email
    	String email = form.getMail();
    	email = email.split("@mailinator.com")[0];
    	
    	// Build email url
    	String url = "http://" + email + ".mailinator.com";
    	
    	// Open url in new window
    	selenium.openWindow(url, "mailinator");
    	selenium.waitForPopUp("mailinator", "30000");
    	selenium.selectWindow("mailinator");
    	
    	// Open new email
    	String mailLinkLocator = "link=Tu pin de regalo";
    	waitForElement(mailLinkLocator, "30000");
    	selenium.click(mailLinkLocator);

    	// Get email content
    	String emailContentLoctor = "//div[@id='message']";
    	waitForElement(emailContentLoctor, "30000");
    	
    	String emailContent = selenium.getText(emailContentLoctor);
    	
    	String pinUrlCode = getPinUrlCode(emailContent);
    	
    	// Close mailinator and return to previous window
    	selenium.close();
    	selenium.selectWindow("null");

    	// Open http://www.elsantoregalapines.com/
        selenium.open("/" + pinUrlCode);

        // Wait for page to load
        selenium.waitForPageToLoad("30000");
            	
        // Store pin
    	String pin = recoverPin();
    	PinStorage.addPin(email, pin);
    }
    
    private String getPinUrlCode(String emailContent) {
    	String[] contents = emailContent.split("ingresa a ");
    	
    	String[] pinUrl = contents[1].split(".com/");
    	// Get the pin's url
    	String code = pinUrl[1];
    	// Remove "." (last character)
    	code = code.substring(0, code.length() - 1);
    	return code;
    }
    
    private void waitForElement(String locator, String timeout) {
    	int seconds = Integer.valueOf(timeout) / 1000;
    	for (int second = 0;; second++) {
    		if (second >= seconds) {
    			fail("Email not found");
    		}
    		if (selenium.isElementPresent(locator)) {
    			break;
    		}
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
}
