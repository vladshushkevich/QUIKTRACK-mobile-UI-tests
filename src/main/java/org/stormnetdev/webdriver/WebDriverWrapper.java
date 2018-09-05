package org.stormnetdev.webdriver;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.stormnetdev.reporter.Reporter;
import org.stormnetdev.utils.configuration.DetectDeviceType;
import org.stormnetdev.webdriver.browsers.AppiumEmulatorDriver;
import org.testng.Assert;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static org.stormnetdev.reporter.Reporter.logFailed;
import static org.stormnetdev.reporter.Reporter.logPassedOperation;

public abstract class WebDriverWrapper {
	
    public static Assertion hardAssert = new Assertion();
    public static SoftAssert softAssert = new SoftAssert();
    
    public static Assertion getHardAssertion() {
    	return hardAssert;
    }
   
    public static SoftAssert getSoftAssertion() {
    	return softAssert;
    }
    
    public static void launchWebDriverSession() {
    	Reporter.logInfo("Launching WebDriver Session");
        WebDriverFactory.instantiateBrowser();
        WebDriverFactory.configureDriver();
	}
    
    public static void closeWebDriverSession() {
    	Reporter.logInfo("Closing WebDriver Session");
    	WebDriverFactory.getDriver().quit();
    }
    
    public static void clickOnElement(WebElement webElement) {
    	webElement.click();
    }
    
    public static void tapOnElement(WebElement webElement, String name) {
        Reporter.logOperation("Tap on center of element: " + name);
		try {
			TouchActions action = new TouchActions(AppiumEmulatorDriver.driver);
			action.singleTap(webElement);
			action.perform();
	    	logPassedOperation();
		   } catch (Exception e){
			   logFailed("Tapping was not performed");
			   throw(e);
	           } 
    }
    
    public static void fillTextField(WebElement fieldWebElement, String text) {
        Reporter.logOperation("Fill field with text: " + text);
        WebDriverEventLogger.setNewValue(text);
        fieldWebElement.sendKeys(text);
    }
    
    /**
     * Clear text field
     */
    
    public static void clearField(WebElement fieldWebElement) {
        Reporter.logOperation("Clear field");
		try {
	        fieldWebElement.clear();
		   } catch (Exception e){
			   logFailed("Field was not cleared");
			   throw(e);
	           } 
    }
    
    /**
     * Press the key event
     */
    public static void sendKeyEvent(int key) {
        Reporter.logOperation("Press the key event: " + key);
		try {
			if (AppiumEmulatorDriver.driver instanceof AndroidDriver){
				((AndroidDriver) AppiumEmulatorDriver.driver).pressKeyCode(key);
			}
	    	logPassedOperation();
		   } catch (Exception e){
			   logFailed("Pressing key was not performed");
			   throw(e);
	           } 
    }
    
    public static WebElement findElement(By locator) {
        try {
            WebElement element = WebDriverFactory.getDriver().findElement(locator);
                return element;
        } catch (NoSuchElementException e) {
        	Reporter.logWarning("Element '" + locator + "' not found.");
    		return null;
         }
    } 
    
    public static WebElement waitAndfindElement(By locator){
    	waitForElement(locator);
    	WebElement element = findElement(locator);
    	return element;
    }
       
    public static List<WebElement> findElements(By locator) {
        try {
            List<WebElement> elements = WebDriverFactory.getDriver().findElements(locator);
            return elements;  
        } catch (NoSuchElementException e) {
        	Reporter.logWarning("Elements '" + locator + "' not found.");
    		return null;
         }     
    }
    
    public static List<WebElement> waitAndfindElements(By locator){
    	waitAll(locator);
    	List<WebElement> elements = findElements(locator);
        return elements;  
    }
   
    public static void assertElementContainsText(WebElement element, String text) {
    	Reporter.logOperation("Asserting element: " + element.getAttribute("name") + " contains text: " + text);
    	try {
    		getHardAssertion().assertTrue(element.getAttribute("name").contains(text));
	    	logPassedOperation();
		} catch (AssertionError e) {
			Reporter.logFailed("Expected text: '" + text + "' is not found inside of element: " + element.getAttribute("name"));
			Assert.fail();
		}
	}
    
    /**
     * Wait 30 seconds for locator to find an element 
     */
    public static void waitForElement(By locator){
    	try{
			Reporter.logOperation("Wait for element: " + locator);
			WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), 30);
			Reporter.logOperation("Waiting for element: " + locator);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			Reporter.logPassedOperation();
    	}catch(TimeoutException e){
    		Reporter.logFailed("Timed out after 30 seconds waiting.");
            throw new RuntimeException(e);
    	}
    }
        
    /**
     * Wait 60 seconds for locator to find an element 
     */
    public static void waitForElementLong(By locator){
    	try{
    	WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), 60);
    	Reporter.logOperation("Waiting for element: " + locator);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		Reporter.logPassedOperation();
    	}catch(TimeoutException e){
    		Reporter.logFailed("Timed out after 60 seconds waiting.");
            throw new RuntimeException(e);
    	}
    }
    
    /**
     * Wait 30 seconds for locator to find all elements *
     */
    public static void waitAll(By locator) {
    	try{
    		WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), 30);
    		Reporter.logOperation("Waiting for elements: " + locator);
    		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    		Reporter.logPassedOperation();
    	}catch(TimeoutException e){
    		Reporter.logFailed("Timed out after 30 seconds waiting.");
            throw new RuntimeException(e);
    	}
    }
    
    /**
     * Wait 30 seconds for locator to find all visibility elements *
     */
    public static void waitAllVisibility(By locator) {
    	try{
    		WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), 30);
    		Reporter.logOperation("Waiting for elements: " + locator);
    		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    		Reporter.logPassedOperation();
    	}catch(TimeoutException e){
    		Reporter.logFailed("Timed out after 30 seconds waiting.");
            throw new RuntimeException(e);
    	}
    }

    /**
     * Wait 30 seconds for locator to not find a visible element *
     */
    public static boolean waitInvisible(By locator) {
    	try{
    		WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), 5);
    		Reporter.logOperation("Waiting for invisible element: " + locator);
			boolean result = wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			Reporter.logPassedOperation();
			return result;
		}catch(TimeoutException e){
    		Reporter.logFailed("Element is visible. Timed out after 30 seconds waiting.");
            throw new RuntimeException(e);
    	}
    } 
    
    /**
     * Sleep method. 
     * @throws InterruptedException *
     */
    public static void sleep(int time){
		try {
    		Reporter.logOperation("Sleeping for: " + time + " seconds.");
			Thread.sleep(time * 1000);
    		Reporter.logPassedOperation();
		} catch (InterruptedException e) {
    		Reporter.logFailed("Error during sleeping " + time + " seconds.");
			e.printStackTrace();
		}
    }
    
    /**
     * Press the back button *
     */
    public static void back() {
    	Reporter.logOperation("Press the back button");
    	WebDriverFactory.getDriver().navigate().back();
    }
     
    /**
     * Hide keyboard *
     */
    public static void hideKeyboard() {
    	Reporter.logOperation("Hide keyboard");
		try {
			AppiumEmulatorDriver.driver.hideKeyboard();
// For IOS Driver:
//			AppiumEmulatorDriver.driver.hideKeyboard(HideKeyboardStrategy.PRESS_KEY, strategy);
			logPassedOperation();
		   } catch (Exception e){
			   logFailed(" The keyboard was not hided.");
			   throw(e);
	           } 
    }
    
    
   /**
    * Hide keyboard IOS*
    */
   public static void hideKeyboardIOS() {
   	Reporter.logOperation("Hide keyboard");
		try {
			clickOnElement(waitAndfindElement(By.name("Done")));
			logPassedOperation();
		   } catch (Exception e){
			   logFailed(" The keyboard was not hided.");
			   throw(e);
	           } 
   }
   
   /**
    * Hide keyboard IOS - IPhone 6+ - Landscape*
    */
   public static void hideKeyboardIOSL() {
   	Reporter.logOperation("Hide keyboard");
		try {
			clickOnElement(waitAndfindElement(By.name("Hide keyboard")));
			logPassedOperation();
		   } catch (Exception e){
			   logFailed(" The keyboard was not hided.");
			   throw(e);
	           } 
   }

    /**
     * Return a static text locator that contains text *
     */
    public static By for_text(String text) {
      return By.xpath("//android.widget.TextView[contains(@text, '" + text + "')]");
    }

    public static By for_find(String value) {
    	 Reporter.logOperation("Find element: " + value);
         
         try {
        	 return By.xpath("//*[@content-desc=\"" + value + "\" or @resource-id=\"" + value +
        	          "\" or @text=\"" + value + "\"] | //*[contains(translate(@content-desc,\"" + value +
        	          "\",\"" + value + "\"), \"" + value + "\") or contains(translate(@text,\"" + value +
        	          "\",\"" + value + "\"), \"" + value + "\") or @resource-id=\"" + value + "\"]"); 
         } catch (Exception e) {
        	 Reporter.logFailed("Element " + value + " was not found");
            //Reporter.logFailed(e.getMessage());
            throw new RuntimeException(e);
         }    	
    }

    /**
     * Return an element that contains name or text *
     * TODO: Add exception NoSuchElementException
     */

	public WebElement scrollDownTo(By element) {

		int pressX = AppiumEmulatorDriver.driver.manage().window().getSize().width / 2;
		int bottomY = AppiumEmulatorDriver.driver.manage().window().getSize().height * 4/5;
		int topY = AppiumEmulatorDriver.driver.manage().window().getSize().height / 8;
		int i = 0;
		Boolean isPresent;
		WebElement webElement = null;
		do{
			isPresent = AppiumEmulatorDriver.driver.findElements(element).size()>0;
			if(isPresent){
				webElement = AppiumEmulatorDriver.driver.findElement(element);
				webElement.click();
				break;
			}
			else{
				swipe(pressX, bottomY, pressX, topY);}
			i++;

		} while(i <= 4);
		return webElement;
	}

	private static void swipe(int fromX, int fromY, int toX, int toY) {
		TouchAction touchAction = new TouchAction(AppiumEmulatorDriver.driver);
		try {
			touchAction.longPress(PointOption.point(fromX, fromY)).moveTo(PointOption.point(toX, toY)).release().perform();
		} catch(WebDriverException wd){
			Reporter.logFailed(wd.getMessage());
		}
	}
    
    /**
     * Swipe down from startY % of screen to endY % of screen  
     */
    public static void swipeYAxis(int startYPercent, int endYPercent, double widthOfScrren) {
			Reporter.logSubOperation("Swipe down from " + startYPercent + "% of screen to " + endYPercent + "% of screen");
		try {
			Dimension size = AppiumEmulatorDriver.driver.manage().window().getSize();
			int startY = (int)(size.height * startYPercent/100);
	    	int endY = (int)(size.height * endYPercent/100);
	    	int startX = (int) (size.width*widthOfScrren);
	    	swipe(startX, startY, startX, endY);
	    	logPassedOperation();
	   } catch (Exception e){
		   logFailed("Swipe was not performed");
		   throw(e);
         }  
    }
    
    /**
     * Swipe left or right from startX % of screen to endX % of screen  
     */
    public static void swipeXAxis(int startXPercent, int endXPercent) {
			Reporter.logSubOperation("Swipe left or right from " + startXPercent + "% of screen to " + endXPercent + "% of screen");
		try {
			Dimension size = AppiumEmulatorDriver.driver.manage().window().getSize();
			int startX = (int)(size.width * startXPercent/100);
	    	int endX = (int)(size.width * endXPercent/100);
	    	int startY = size.height/2;
	    	swipe(startX, startY, endX, startY);
	    	logPassedOperation();
	   } catch (Exception e){
		   logFailed("Swipe was not performed");
		   throw(e);
         }  
    }
    /**
     * Return an element name 
     * @return name
     */
	public static String getName(WebElement element) {
		Reporter.logSubOperation("Getting name of element");
		String name;
		if(DetectDeviceType.detectIOSDeviceType().contains("iPhone")){
			name = element.getAttribute("name");	

		}
		else{
			name = element.getAttribute("text");	
		}
		Reporter.logSubOperation("Element name: '" + name + "'");
		return name;
	}
	
    /**
     * Return value for attribute 
     * @return attributeValue
     */
	public static String getElemenAttribute(WebElement element, String attribute) {
		Reporter.logSubOperation("Getting value for attribute: " + attribute);
		String attributeValue = element.getAttribute(attribute);	
		Reporter.logSubOperation("Element has attribute '" +  attribute + "' and value '" + attributeValue + "'");
		return attributeValue;
	}

}
