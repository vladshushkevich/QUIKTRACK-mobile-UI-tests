package org.stormnetdev.tests.mobile.pages;

import org.openqa.selenium.By;
import org.stormnetdev.reporter.Reporter;

import static org.stormnetdev.webdriver.WebDriverWrapper.*;

/**
 * Class for Login to email page Cirrus app.
 * @author i.baranovski
 */

public class CoinsPage {
	
	/**
	 * Page locators
	 */

   private static String coinsCountLblId = "com.alibaba.aliexpresshd:id/tv_coins_count";
   private static String oneCentLblName = "FOR US $0.01";
   private static String tomorrowPageLblName = "picks with US $0.01";
   private static String viewMoreLblName = "View More";

  	/** Verify that CoinsPage is displayed**/
	
   public static void loaded() {
       Reporter.logStep("Verify that CoinsPage was loaded");
       waitForElement(By.id(coinsCountLblId));
       Reporter.logPassedStep();
   }

    /** Open 0.01$ tomorrow page**/

    public void openDiscountPage() {
        Reporter.logStep("Open 0.01$ tomorrow page");
        swipeYAxis(90, 20, 0.5);
        swipeYAxis(90, 20, 0.5);
        swipeYAxis(90, 20, 0.5);
        sleep(2);
//        tapOnElement(waitAndfindElement(By.xpath("//*[@text=\"" + oneCentLblName + "\"]")), "FOR US $0.01");
//        clickOnElement(waitAndfindElement(By.xpath("//*[@text=\"" + oneCentLblName + "\"]")));
        clickOnElement(waitAndfindElement(By.xpath("//*[@text='FOR US $0.01']")));
//        clickOnElement(waitAndfindElement(By.xpath("//android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.FrameLayout[2]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.HorizontalScrollView[1]/android.widget.LinearLayout[1]/android.support.v7.app.ActionBar.Tab[2]/android.widget.TextView[1]")));
        swipeYAxis(30, 60, 0.5);
        sleep(1);
        waitForElement(By.xpath("//android.widget.TextView[contains(@text,'" + tomorrowPageLblName + "')]"));
        clickOnElement(waitAndfindElement(By.xpath("//*[@text=\"" + viewMoreLblName + "\"]")));
        ProductsPage.loaded();
        Reporter.logPassedStep();
    }
}
