package org.stormnetdev.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.stormnetdev.reporter.Reporter;

public class WebDriverEventLogger implements WebDriverEventListener {

	private By lastFindBy;
	private String originalValue;
	private static String newValue;
	
	public static void setNewValue (String value) {
		WebDriverEventLogger.newValue = value;
	}

	@Override
	public void afterChangeValueOf(WebElement element, WebDriver arg1, CharSequence[] arg2) {
		Reporter.logSubOperation("Changed value to: '" + newValue + "'");
		Reporter.logPassedOperation();
		
	}

	@Override
	public void afterClickOn(WebElement arg0, WebDriver arg1) {
		Reporter.logPassedOperation();
		
	}

	@Override
	public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterNavigateBack(WebDriver arg0) {
		Reporter.logPassedOperation();
		
	}

	@Override
	public void afterNavigateForward(WebDriver arg0) {
		Reporter.logPassedOperation();
		
	}

	@Override
	public void beforeNavigateRefresh(WebDriver webDriver) {

	}

	@Override
	public void afterNavigateRefresh(WebDriver webDriver) {

	}

	@Override
	public void afterNavigateTo(String arg0, WebDriver arg1) {
		Reporter.logPassedOperation();
	
	}

	@Override
	public void afterScript(String arg0, WebDriver arg1) {
		Reporter.logPassedOperation();
		
	}

	@Override
	public void beforeSwitchToWindow(String s, WebDriver webDriver) {

	}

	@Override
	public void afterSwitchToWindow(String s, WebDriver webDriver) {

	}

	@Override
	public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
		Reporter.logSubOperation("Changing value of: " +  lastFindBy);
		originalValue = webElement.getAttribute("text");
		if (originalValue.isEmpty()) {
			originalValue = "NULL";
		}
		Reporter.logSubOperation("Changing value from: '" + originalValue + "'");
	}

	@Override
	public void beforeClickOn(WebElement arg0, WebDriver arg1) {
		Reporter.logOperation("Click on element " +  lastFindBy);
		
	}

	@Override
	public void beforeFindBy(By by, WebElement arg1, WebDriver arg2) {
		lastFindBy = by;
		
	}

	@Override
	public void beforeNavigateBack(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateForward(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeAlertAccept(WebDriver webDriver) {

	}

	@Override
	public void afterAlertAccept(WebDriver webDriver) {

	}

	@Override
	public void afterAlertDismiss(WebDriver webDriver) {

	}

	@Override
	public void beforeAlertDismiss(WebDriver webDriver) {

	}

	@Override
	public void beforeNavigateTo(String uRL, WebDriver arg1) {
		Reporter.logOperation("Open '" + uRL + "' URL");
		
	}

	@Override
	public void beforeScript(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onException(Throwable error, WebDriver arg1) {
		if (error.getClass().equals(InvalidSelectorException.class)){
			//TODO: Change excepcion.
//			throw new InvalidSelectorException("Argument was an invalid selector (e.g. XPath/CSS).");
			   throw new RuntimeException("Argument was an invalid selector (e.g. XPath/CSS).");
		}
		if (error.getClass().equals(NoSuchElementException.class)){
			// TODO Auto-generated method stub
        }
		else {
        	Reporter.logFailed("WebDriver error: ");
        }
	}

	@Override
	public <X> void beforeGetScreenshotAs(OutputType<X> outputType) {

	}

	@Override
	public <X> void afterGetScreenshotAs(OutputType<X> outputType, X x) {

	}

}
