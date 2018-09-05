package org.stormnetdev.tests.mobile.pages;

import org.openqa.selenium.By;
import org.stormnetdev.reporter.Reporter;

import static org.stormnetdev.webdriver.WebDriverWrapper.clickOnElement;
import static org.stormnetdev.webdriver.WebDriverWrapper.waitAndfindElement;

/**
 * Class for Login to email page Cirrus app.
 * @author i.baranovski
 */

public class MyWalletPage extends MainPage {

	/**
	 * Page locators
	 */

   private static String coinsLblName = "Coins";


    /** Go to coins page **/

    public void goToCoinsPage() {
        Reporter.logStep("Go to coins page");
        clickOnElement(waitAndfindElement(By.xpath("//*[@text=\"" + coinsLblName + "\"]")));
        Reporter.logPassedStep();
        CoinsPage.loaded();
    }
}
