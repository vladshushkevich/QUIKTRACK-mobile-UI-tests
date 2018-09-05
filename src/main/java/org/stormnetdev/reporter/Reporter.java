package org.stormnetdev.reporter;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.stormnetdev.tests.api.folder.RequestBase;

public class Reporter {
    
	//TODO: To move all styles to report stylesheet
	public static int testNumber = 1;
	public static int totalTestsNumber = 0;
	public static String outputDir;
	public static String suiteName;
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";//#00529B
	public static final String ANSI_BLUE_BOLD = "\u001B[34;1m";//#00529B
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_CYAN_BOLD = "\u001B[36;1m";//#00ffff
	public static final String ANSI_WHITE = "\u001B[37m";
	
    private static String currentStep;
    public static String description;

//    @Step(">>>Info: {0}")
	public static void logInfo(String message) {
        System.out.println(ANSI_CYAN + ">>>Info: " + message + ANSI_RESET);
        description += "<h3 style='color: #999999;'><b>Info:</b> " + message + "</h3>";
    }

    @Attachment("Request")
    public static String logRequest() {
	    String request = RequestBase.getRestAssuredLog();
        System.out.println(ANSI_BLACK + "Request: " + ANSI_RESET + ANSI_BLACK + request + ANSI_RESET);
        description += "<h2 style='color: #000000;'><b>Request:</b></h2>" + "<h3 style='color: #999999;'> " + request + "</h3>";
        System.out.println();
        return request;
	}

    @Attachment("Response")
    public static String logResponse(final String message) {
        System.out.println(ANSI_BLACK + "Response: " + ANSI_RESET + ANSI_BLACK + message + ANSI_RESET);
        description += "<h2 style='color: #000000;'><b>Response:</b></h2>" + "<h3 style='color: #999999;'> " + message + "</h3>";
        System.out.println();
        return message;
	}

//    @Step(">>>Operation: {0}")
    public static void logOperation(String message) {
        System.out.print("    Operation: " + message);
        description += "<p style='margin-left: 2em;'><b>Operation:</b> " + message + "</p>";
    }

    public static void logSubOperation(String message) {
        System.out.print("    > " + message);
        org.testng.Reporter.log("<p style='margin-left: 4em;'><b>></b> " + message + "</p>");
    }

    @Step("{0}")
    public static void logStep(String message) {
        System.out.println(ANSI_BLUE_BOLD + "Step: " + message + ANSI_RESET);
        if(message.length()>50){
        	currentStep = message.substring(0, 50);
        } //TODO: Refactor. Long name Screenshot reporter issue.
        else{
        	currentStep = message;
        }
        description += "<h2 style='color: #00529B;'><b>Step:</b> " + message + "</h2>";
    }
    
    public static void logPassedOperation() {
        System.out.println(ANSI_GREEN + "    |    Passed" + ANSI_RESET);
        description += "<p style='color: #339966; margin-left: 2em;'><b>> </b>Operation Passed</p>";
    }
    
    public static void logPassedStep() {
        System.out.println(ANSI_GREEN + "Step Passed" + ANSI_RESET);
        description += "<h2 style='color: #339966; padding-bottom: 1em;'><b>Step Passed</b></h2>";
    }

    @Step("{0}")
    public static void logWarning(String message) {
        System.out.println(ANSI_YELLOW + "Warning: " + message + ANSI_RESET);
        description += "<h3 style='color: #9F6000;'>" + message + "</h3>";
    }

    @Step("{0}")
    public static void logFailed(String message) {
    	System.out.println(ANSI_RED + "    |    Failed" + ANSI_RESET);
        System.out.println(ANSI_RED + "Error: " + message + ANSI_RESET);
        description += "<h3 style='color: #FF0033;'>" + message + "</h3>";
    }

}
