package org.stormnetdev.tests.mobile.pages;

import org.openqa.selenium.By;
import org.stormnetdev.reporter.Reporter;

import static org.stormnetdev.webdriver.WebDriverWrapper.clickOnElement;
import static org.stormnetdev.webdriver.WebDriverWrapper.waitAndfindElement;

/**
 * Class for Settings page Cirrus app.
 * @author i.baranovski
 */

public class SelectLoginPage {
	
	/**
	 * Page locators
	 */
	
   private static String signInBtnName = "Sign In";
   private static String registerBtnName = "Register";

   
	/** Verify that SelectLoginPage is displayed**/
	
   public static void loaded() {
        Reporter.logStep("Verify that Settings form was loaded");
		waitAndfindElement(By.xpath("//*[@text=\"" + registerBtnName + "\"]"));
		Reporter.logPassedStep();
   }
	
	/** Login with already logged email **/
	
    public void loginWithLoggedEmail() {
        Reporter.logStep("Login with already logged email");
		clickOnElement(waitAndfindElement(By.xpath("//*[@text=\"" + signInBtnName + "\"]")));
		Reporter.logPassedStep();
		LoginPage.loaded();
    }
}
