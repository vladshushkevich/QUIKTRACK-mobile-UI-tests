package org.stormnetdev.tests.mobile.pages;

import org.openqa.selenium.By;
import org.stormnetdev.reporter.Reporter;

import static org.stormnetdev.webdriver.WebDriverWrapper.waitForElement;

/**
 * Class for Login to email page Cirrus app.
 * @author i.baranovski
 */

public class ProfilePage extends MainPage {
	
	/**
	 * Page locators
	 */

   private static String profileInfoLblId = "com.alibaba.aliexpresshd:id/profile_text_info";

  	/** Verify that LoginToEmailForm is displayed**/
	
   public static void loaded() {
       Reporter.logStep("Verify that ProfilePage was loaded");
       waitForElement(By.id(profileInfoLblId));
       Reporter.logPassedStep();
   }
}
