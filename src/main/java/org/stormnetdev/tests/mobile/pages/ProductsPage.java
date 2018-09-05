package org.stormnetdev.tests.mobile.pages;

import org.openqa.selenium.By;
import org.stormnetdev.reporter.Reporter;

import java.util.Calendar;

import static org.stormnetdev.webdriver.WebDriverWrapper.*;

/**
 * Class for Login to email page Cirrus app.
 * @author i.baranovski
 */

public class ProductsPage {
	
	/**
	 * Page locators
	 */

   private static String productPriceLblName = "US $16.95";
   private static String tomorrowPageLblName = "picks with US $0.01";

  	/** Verify that CoinsPage is displayed**/
	
   public static void loaded() {
       Reporter.logStep("Verify that ProductsPage was loaded");
       waitForElement(By.xpath("//android.widget.TextView[contains(@text,\"" + tomorrowPageLblName + "\")]"));
       Reporter.logPassedStep();
   }

    /** Open 0.01$ tomorrow page**/

    public void buySelectedProduct() {
        Reporter.logStep("Buy product");
        waitForTime(10);
        clickOnElement(waitAndfindElement(By.xpath("//*[@text=\"" + productPriceLblName + "\"]")));
        clickOnElement(waitAndfindElement(By.id("com.alibaba.aliexpresshd:id/rl_coins_exchange_btn")));

        //Select parameters
        clickOnElement(waitAndfindElement(By.xpath("//android.widget.RadioGroup[1]/android.widget.CompoundButton[3]")));
//        clickOnElement(waitAndfindElement(By.xpath("//android.widget.RadioGroup[2]/android.widget.CompoundButton[2]")));


        clickOnElement(waitAndfindElement(By.id("com.alibaba.aliexpresshd:id/tv_apply_options")));
        clickOnElement(waitAndfindElement(By.id("com.alibaba.aliexpresshd:id/bt_place_order")));
        sleep(15);
        Reporter.logPassedStep();
    }

    private void waitForTime(int hours) {
        int counter = 0;
        int currentHours = Calendar.getInstance().getTime().getHours();
//        int currentHours = Calendar.getInstance().getTime().getMinutes();
        while (currentHours != hours || counter == 10000000) {
//            currentHours = Calendar.getInstance().getTime().getMinutes();
            currentHours = Calendar.getInstance().getTime().getHours();
            counter++;
        }
    }
}
