package org.stormnetdev.tests.mobile.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.stormnetdev.reporter.Reporter;

import static org.stormnetdev.webdriver.WebDriverWrapper.*;

/**
 * Class for Login to email page Cirrus app.
 * @author i.baranovski
 */

public class LoginPage {
	
	/**
	 * Page locators
	 */
	
   private static String headingName = "Sign In";
   private static String headingLblXpath = "//*[@text=\"%1$s\"]";
   private static String headingXpath = String.format(headingLblXpath, headingName);

   private static String loginFieldId = "com.alibaba.aliexpresshd:id/et_email";
   private static String passwordFieldId = "com.alibaba.aliexpresshd:id/et_password";
   private static String loginBtnId = "com.alibaba.aliexpresshd:id/tv_signin_btn_label";
	private static String profileInfoLblId = "com.alibaba.aliexpresshd:id/profile_text_info";


	/** Verify that LoginToEmailForm is displayed**/
	
   public static void loaded() {
       Reporter.logStep("Verify that LoginToEmailForm was loaded");
       waitForElement(By.xpath(headingXpath));
       Reporter.logPassedStep();
   }
    
	/** Authorization 
	 * @throws InterruptedException **/
	
	public static void authorizeEmail(String login, String password) {
		if (findElement(By.id(profileInfoLblId)) == null) {
			Reporter.logStep("Authorize with new login and password");
			WebElement loginBox = waitAndfindElement(By.id(loginFieldId));
			clickOnElement(loginBox);
			clearField(loginBox);
			fillTextField(loginBox, login);
			hideKeyboard();
			WebElement passwordBox = waitAndfindElement(By.id(passwordFieldId));
			clickOnElement(passwordBox);
			fillTextField(passwordBox, password);
			hideKeyboard();
			WebElement loginBtn = waitAndfindElement(By.id(loginBtnId));
			clickOnElement(loginBtn);
			Reporter.logPassedStep();
			ProfilePage.loaded();
		}
	}
    
}
