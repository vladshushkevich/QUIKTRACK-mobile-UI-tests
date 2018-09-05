package org.stormnetdev.tests.mobile.tests;

import org.openqa.selenium.ScreenOrientation;
import org.stormnetdev.reporter.Reporter;
import org.stormnetdev.tests.TestBase;
import org.stormnetdev.utils.configuration.Configurator;
import org.stormnetdev.webdriver.browsers.AppiumEmulatorDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static org.stormnetdev.webdriver.WebDriverWrapper.*;

public abstract class TestBaseMobile extends TestBase {

    
    @BeforeMethod
    public void setUpMobileMethod(){
    	launchWebDriverSession();
    	if(Configurator.orientation.equalsIgnoreCase("LANDSCAPE") && Configurator.platformName.equalsIgnoreCase("Android")){
    		AppiumEmulatorDriver.driver.rotate (ScreenOrientation.LANDSCAPE);
    	}
    	if(Configurator.orientation.equalsIgnoreCase("PORTRAIT") && Configurator.platformName.equalsIgnoreCase("Android")){
    		AppiumEmulatorDriver.driver.rotate (ScreenOrientation.PORTRAIT);
    	}
    	Reporter.logInfo("Set Up Method");
    }
    
    @AfterMethod
    public void tearDownMobileMethod() {
    	closeWebDriverSession();
    	getSoftAssertion().assertAll();
    	Reporter.logInfo("Tear Down Method");
    }
}
