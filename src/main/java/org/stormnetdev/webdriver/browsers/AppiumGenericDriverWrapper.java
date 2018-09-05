package org.stormnetdev.webdriver.browsers;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.stormnetdev.reporter.Reporter;
import org.stormnetdev.webdriver.WebDriverFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.stormnetdev.webdriver.WebDriverWrapper.fillTextField;
import static org.stormnetdev.webdriver.WebDriverWrapper.waitAndfindElement;


public class AppiumGenericDriverWrapper {
	static AppiumDriver driver = (AppiumDriver) WebDriverFactory.getDriver();

	public static void launchApp(){
    	Reporter.logInfo("Launching Application...");
    	driver.launchApp();
    }
    
    public static void closeApp(){
    	Reporter.logInfo("Closing and Reset Application...");
    	driver.closeApp();
    	driver.resetApp();
    }
    
    public static void resetApp(){
    	Reporter.logInfo("Resetting Application...");
    	driver.closeApp();
    	driver.resetApp();
    	driver.launchApp();
    }
    
    public static void quitDriver() {
    	try {
    		Reporter.logOperation("Closing Driver...");
    		driver.quit();
        	Reporter.logPassedOperation();
    	} catch (Exception e)
    	{
    		Reporter.logFailed("Error while closing driver");
			e.printStackTrace();
    	}
	}
	
	public static void movePictureToDevice(String destDirPath, String srcFilePath) {
		try {
			Reporter.logInfo("Copying File (" + srcFilePath + ") to Simulator");
			File destDir = new File(destDirPath);
			FileUtils.forceMkdir(destDir);
			File srcFile = new File(srcFilePath);
            FileUtils.copyFileToDirectory(srcFile, destDir);
		} catch (Exception e) {
			Reporter.logFailed("Can't copy picture into emulator");
			e.printStackTrace();
		}
	}
	
	public static void deletePhotoDataFolderFromEmulator(String destDirPath) {
		try {
			Reporter.logInfo("Deleting " + destDirPath + " folder");
			File destDir = new File(destDirPath);
			FileUtils.deleteDirectory(destDir);
		} catch (Exception e) {
			Reporter.logFailed("Can't delete directory");
			e.printStackTrace();
		}
	}
	
	public static long getDateDiff(DateTime date1, DateTime date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getMillis() - date1.getMillis();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	
	public static void setDateTime(DateTime dateTimeToSet) {
		DateTime dateTime = DateTime.now();
		Reporter.logInfo("Current Time: " + dateTime);
		Reporter.logOperation("Setting up datetime for: " + dateTimeToSet);
		fillTextField(waitAndfindElement(By.xpath("//UIAPicker")), Long.toString(getDateDiff(dateTime, dateTimeToSet, TimeUnit.DAYS)));
		fillTextField(waitAndfindElement(By.xpath("//UIAPicker//UIAPickerWheel[2]")), Integer.toString(dateTimeToSet.getHourOfDay()%12));
		fillTextField(waitAndfindElement(By.xpath("//UIAPicker//UIAPickerWheel[3]")), Integer.toString(dateTimeToSet.getMinuteOfHour()));
		if(dateTimeToSet.getHourOfDay()<=12){
			fillTextField(waitAndfindElement(By.xpath("//UIAPicker//UIAPickerWheel[4]")), "AM");
		}
		else{
			fillTextField(waitAndfindElement(By.xpath("//UIAPicker//UIAPickerWheel[4]")), "PM");
		}
		Reporter.logPassedOperation();
	}
	
	public static void setTime(DateTime dateTimeToSet) {
		Reporter.logOperation("Setting up time for: " + dateTimeToSet.getHourOfDay() + ":" + dateTimeToSet.getMinuteOfHour());
		fillTextField(waitAndfindElement(By.xpath("//UIAPicker//UIAPickerWheel[2]")), Integer.toString(dateTimeToSet.getHourOfDay()%12));
		fillTextField(waitAndfindElement(By.xpath("//UIAPicker//UIAPickerWheel[3]")), Integer.toString(dateTimeToSet.getMinuteOfHour()));
		if(dateTimeToSet.getHourOfDay()<=12){
			fillTextField(waitAndfindElement(By.xpath("//UIAPicker//UIAPickerWheel[4]")), "AM");
		}
		else{
			fillTextField(waitAndfindElement(By.xpath("//UIAPicker//UIAPickerWheel[4]")), "PM");
		}
		Reporter.logPassedOperation();
	}   
}
