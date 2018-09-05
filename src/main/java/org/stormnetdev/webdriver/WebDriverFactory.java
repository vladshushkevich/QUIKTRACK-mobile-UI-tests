package org.stormnetdev.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.stormnetdev.utils.configuration.Configurator;
import org.stormnetdev.reporter.Reporter;
import org.stormnetdev.webdriver.browsers.AppiumEmulatorDriver;

public class WebDriverFactory {
    
    public static WebDriver driver;
   
    public static WebDriverEventListener webDriverEventListener = new WebDriverEventLogger();
    
    public static WebDriver getDriver() {
        return driver;
    }

	public static void setDriver(String browserType) {
		Reporter.logOperation("Instantiating driver");

		EventFiringWebDriver driver = new EventFiringWebDriver(AppiumEmulatorDriver.getDriver());
		driver.register(webDriverEventListener);

		WebDriverFactory.driver = driver;

		if (driver != null) {
			Reporter.logPassedOperation();
		} 
		else {
			throw new NullPointerException("Driver has not been instantiated");
		}
	}

    public static void configureDriver() {
    	Reporter.logOperation("Configuring driver");
//        getDriver().manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
//        getDriver().manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
        Reporter.logPassedOperation();
    }
    
    public static void instantiateBrowser() {
    	Reporter.logInfo("Webdriver: " + Configurator.browserType.toString());
        WebDriverFactory.setDriver(Configurator.browserType);
    }
    
}
