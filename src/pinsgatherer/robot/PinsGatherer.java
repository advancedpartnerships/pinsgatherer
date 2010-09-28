package pinsgatherer.robot;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pinsgatherer.data.Form;
import pinsgatherer.data.FormGenerator;
import pinsgatherer.exceptions.ElementNotFoundException;
import pinsgatherer.exceptions.FillingFormException;
import pinsgatherer.exceptions.NoEmailException;
import pinsgatherer.exceptions.NoPinException;
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
	private static final int REFRESH_INTERVAL = 10; // Seconds

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

        //for (Form form : forms) {
        Form form = forms.get(2);
        	try {
            	// Open http://www.elsantoregalapines.com/
                selenium.open("/");

                // Wait for page to load
                selenium.waitForPageToLoad("30000");
                
                // Complete the form with fake data
                if(completeForm(form)) {
                    // Find and store the pin
                    findAndStorePin(form);
                } else {
                    throw new FillingFormException();
                }
        	} catch (NoEmailException e) {
        		e.printStackTrace();
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        //}
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
    
    /**
     * 
     * Call after the form has been submitted. Opens mailinator, parses the mail
     * and stores the pin in an xml file.
     * 
     * @param form
     * @throws NoEmailException 
     */
    
    private void findAndStorePin(Form form) throws NoPinException, NoEmailException {
    	
    	// Get form's email
    	String email = form.getMail();
    	email = email.split("@mailinator.com")[0];
    	
    	// Open mailinator's email inbox
    	openMailinatorUrl(email);
    	
    	// Open new pin email
		openEmail("link=Tu pin de regalo");
    	
		// Get pin's code from email
    	String pinUrlCode = getPinUrlCode();
    	
    	// Close mailinator and return to previous window
    	selenium.close();
    	selenium.selectWindow("null");

    	// Open http://www.elsantoregalapines.com/
        selenium.open("/" + pinUrlCode);
        
        // Wait for page to load
        selenium.waitForPageToLoad("30000");
            	
        // Recover and store pin
    	String pin = recoverPin();
        if (pin != null) {
    	    PinStorage.addPin(email, pin);
        } else {
            throw new NoPinException();
        }
    }
    
    /**
     * 
     * Open a mailinator account in a new window.
     * 
     * @param email the account
     */

	private void openMailinatorUrl(String email) {
		// Build email url
    	String url = "http://" + email + ".mailinator.com";
    	
    	// Open url in new window
    	selenium.openWindow(url, "mailinator");
    	selenium.waitForPopUp("mailinator", "30000");
    	selenium.selectWindow("mailinator");
	}
	
	/**
	 * 
	 * Opens the pin's email
	 * 
	 * @param emailLinkLocator the email's xpath locator
	 * @throws NoEmailException if the email wasn't found
	 */
	
	private void openEmail(String emailLinkLocator) throws NoEmailException {
		// Wait for the email to appear, refreshing every REFRES_INTERVAL seconds
		try {
			waitForElement(emailLinkLocator, "60000", true, REFRESH_INTERVAL);
			// Open the email
			selenium.click(emailLinkLocator);
		} catch (ElementNotFoundException e) {
			throw new NoEmailException();
		}
	}
	
	/**
	 * 
	 * Call after the email has been open. Parses the email content and get the pin's url.
	 * 
	 * @return the pins url code
	 */
    
    private String getPinUrlCode() {
    	// Get email content
    	String emailContent = getEmailContent();
    	if (emailContent == null) {
    		return null;
    	}
    	
    	String[] contents = emailContent.split("ingresa a ");
    	
    	String[] pinUrl = contents[1].split(".com/");
    	// Get the pin's url
    	String code = pinUrl[1];
    	// Remove "." (last character)
    	code = code.substring(0, code.length() - 1);
    	return code;
    }
    
	/**
	 * 
	 * Gets the email content of an mailinator email. Call after the email has been opened.
	 * 
	 * @return the content of the email
	 */

	private String getEmailContent() {
		String emailContentLoctor = "//div[@id='message']";
    	try {
			waitForElement(emailContentLoctor, "30000");
		} catch (ElementNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return selenium.getText(emailContentLoctor);
	}
    
    private void waitForElement(String locator, String timeout) throws ElementNotFoundException {
    	waitForElement(locator, timeout, false, 0);
    }
    
    /**
     * 
     * Waits for an html element to appear in the dom
     * 
     * @param locator the element locator
     * @param timeout the time to wait the element
     * @param refresh a flag to indicate if the page should be refreshed
     * @param refreshInterval  the interval of page refresh
     * @throws ElementNotFoundException
     */
    
    private void waitForElement(String locator, String timeout, boolean refresh, int refreshInterval) throws ElementNotFoundException {
    	int seconds = Integer.valueOf(timeout) / 1000;
    	for (int second = 0;; second++) {
    		if (second >= seconds) {
    			throw new ElementNotFoundException(locator);
    		}
    		if (selenium.isElementPresent(locator)) {
    			break;
    		} else {
    			if (refresh && (second % refreshInterval == 0)) {
    				selenium.refresh();
    				selenium.waitForPageToLoad("30000");
    			}
    		}
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }

}
