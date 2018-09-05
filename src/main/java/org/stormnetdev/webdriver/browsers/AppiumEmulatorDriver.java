package org.stormnetdev.webdriver.browsers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.stormnetdev.utils.configuration.Configurator;

import java.net.MalformedURLException;
import java.net.URL;

public class AppiumEmulatorDriver extends AppiumGenericDriverWrapper {
	public static AppiumDriver driver;

	public static RemoteWebDriver getDriver() {
		if(Configurator.platformName.equalsIgnoreCase("IOS")){
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("newCommandTimeout", 60);
			capabilities.setCapability("deviceName", Configurator.deviceName);
			capabilities.setCapability("orientation", Configurator.orientation);
			capabilities.setCapability("platformName", Configurator.platformName); 
			capabilities.setCapability("platformVersion", Configurator.iOSVersion);
			capabilities.setCapability("bundleId", Configurator.bundleID);
			capabilities.setCapability("app", Configurator.appPath);
				if(Configurator.runOnDevice.equalsIgnoreCase("True")){
					capabilities.setCapability("udid", Configurator.uDID);
				}
			capabilities.setCapability("nativeInstrumentsLib", true);
				if(Configurator.scheme.equalsIgnoreCase("Release")){
					capabilities.setCapability("noReset", false);
					capabilities.setCapability("fullReset", true);
					capabilities.setCapability("keepKeyChains", false);
				}
			URL appiumURL = null;
				try {
					appiumURL = new URL(Configurator.appiumURL);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			driver = new IOSDriver(appiumURL, capabilities);
			return driver;
		}
		else{
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName", Configurator.deviceName); 
			capabilities.setCapability("orientation", Configurator.orientation); 
			capabilities.setCapability("platformName", Configurator.platformName); 
			capabilities.setCapability("platformVersion", Configurator.AndroidVersion); 
//			capabilities.setCapability("app", Configurator.appPath);
			capabilities.setCapability("appPackage", "com.alibaba.aliexpresshd");
			capabilities.setCapability("appActivity","com.aliexpress.module.home.MainActivity");
			capabilities.setCapability("newCommandTimeout", 300);

			if(Configurator.scheme.equalsIgnoreCase("Release")){
				capabilities.setCapability("noReset", true);
				capabilities.setCapability("fullReset", false);
				capabilities.setCapability("keepKeyChains", false);
			}
			URL appiumURL = null;
			try {
				appiumURL = new URL(Configurator.appiumURL);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 driver = new AndroidDriver(appiumURL, capabilities);
			return driver;
		}
	}

}
