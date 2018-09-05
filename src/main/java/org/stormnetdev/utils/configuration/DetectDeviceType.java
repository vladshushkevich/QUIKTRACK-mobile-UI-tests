package org.stormnetdev.utils.configuration;

import org.openqa.selenium.Dimension;
import org.stormnetdev.reporter.Reporter;
import org.stormnetdev.webdriver.browsers.AppiumEmulatorDriver;

import java.util.Arrays;

public abstract class DetectDeviceType {
	
	protected static String[] tabletAndroidResolutionList = { 
			"1024x600", "600x1024",
			"1024x768", "768x1024",
			"1280x800", "800x1280",
			"1366x768", "768x1366",
			"1440x900", "900x1440",
			"1600x1200", "1200x1600",
			"1824x1200", "1200x1824",
//			"1920x1080", "1080x1920",// Current resolution for Tablet and Phone but more often for Phone.
			"1920x1200", "1200x1920",
			"1920x1280", "1280x1920",
			"2048x1536", "1536x2048",
			"2160x1440", "1440x2160",
			"2560x1600", "1600x2560",// Current resolution for Tablet and Phone (Samsung Galaxy Note Edge) but more often for Tablet. 		
			"3200x1800", "1800x3200"
	};

    public static String detectIOSDeviceType() {
		Dimension size = AppiumEmulatorDriver.driver.manage().window().getSize();
		String deviceType;
		int Y = size.height;
    	int X = size.width;
    	if((Y>420 && X>740) || (Y>740 && X>420)){
    		deviceType = "iPad";   		
    	}
    	else{
    		deviceType = "iPhone";   		
    		if((Y>360 && X>650) || (Y>650 && X>360)){
    			deviceType = "iPhone 6+";  
    		}
    	}
        return(deviceType);
    }
 
    public static String detectAndroidDeviceType() {
		Dimension size = AppiumEmulatorDriver.driver.manage().window().getSize();
		String deviceType;
		int Y = size.height;
    	int X = size.width;
    	String deviceResolution = Y + "x" + X;
    	if(Arrays.asList(tabletAndroidResolutionList).contains(deviceResolution)){
    		Reporter.logInfo("Device type: Tablet with resolution " + deviceResolution);
    		deviceType = "Tablet";   		
    	}  		
    	else{
    		Reporter.logInfo("Device type: Phone with resolution " + deviceResolution);
    		deviceType = "Phone";   		
    	}
        return(deviceType);
    }
}
