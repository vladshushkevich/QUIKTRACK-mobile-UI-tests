package org.stormnetdev.tests.mobile.pages;

import org.openqa.selenium.By;
import org.stormnetdev.reporter.Reporter;

import static org.stormnetdev.webdriver.WebDriverWrapper.*;

/**
 * Class for Folder page Cirrus app.
 * @author i.baranovski
 */

public class MainPage {

	/**
	 * Page locators
	 */
	
	private static String searchFieldId = "com.alibaba.aliexpresshd:id/floating_search_view";
	private static String hamburgerButtonId = "com.alibaba.aliexpresshd:id/left_action";
	private static String profileImageId = "com.alibaba.aliexpresshd:id/profile_image";

  	/** Verify that Folder form is displayed**/
	
   public static void loaded() {
       Reporter.logStep("Verify that Main form was loaded");
//	   setWait(10);
	   waitInvisible(By.id("com.alibaba.aliexpresshd:id/iv_logo"));
	   waitForElementLong(By.id(searchFieldId));
	   Reporter.logPassedStep();
   }

	/** Verify that selected in menu page is displayed**/

	public void loadedPage(String name) {
		Reporter.logStep("Verify that selected in menu page is displayed");
		waitForElementLong(By.xpath("//*[@text=\"" + name + "\"]"));
		Reporter.logPassedStep();
	}

 	/** Go to Menu page **/
	
	protected void goToMenuForm() {
		Reporter.logStep("Go to Menu Form");
		swipeXAxis(1, 99);
//		WebElement hamburgerButton = waitAndfindElement(By.id(MainPage.hamburgerButtonId));
//		clickOnElement(hamburgerButton);
		Reporter.logPassedStep();
	}

	/** Go to selected in menu page **/

	public void goToPage(String pageName) {
		goToMenuForm();
		Reporter.logStep("Go to page " + pageName);
//		scroll_to(pageName);
		clickOnElement(waitAndfindElement(By.xpath("//*[@text=\"" + pageName + "\"]")));
		Reporter.logPassedStep();
		loadedPage(pageName);
	}


	/** Go to folder Login page **/

	public void goToLoginPage() {
		goToMenuForm();
		Reporter.logStep("Go to page Login page");
		clickOnElement(waitAndfindElement(By.id(profileImageId)));
		Reporter.logPassedStep();
		SelectLoginPage.loaded();
	}

}
