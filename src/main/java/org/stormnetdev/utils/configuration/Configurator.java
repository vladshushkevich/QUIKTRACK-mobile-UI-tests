package org.stormnetdev.utils.configuration;

import java.nio.file.Paths;

public class Configurator {

	private static String appHost;
	private static ConfigurationParser configurationParser = new ConfigurationParser("currentConfig.properties");
	private static ConfigurationParser authorizationParser = new ConfigurationParser("authorization.properties");
	private static ConfigurationParser usersParser = new ConfigurationParser("user.properties");
	private static ConfigurationParser mobileParser = new ConfigurationParser("mobile.properties");

	//Configuration properties file
	public static String port;
	public static String protocol;
    public static String serverHost;
	public static String basePath;
	public static String basePathV2;

	public static boolean stagefs = false;
	private static String demoServerHost;

	//Authorization properties file
	public static String databaseServer;
	public static String databaseUserName;
	public static String databasePassword;

	public static String urlJira;
	public static String emailJira;
	public static String passwordJira;

	//Mobile properties file
	public static String defaultBrowserType;
	public static String browserType;
	public static String platformName;
	public static String AndroidVersion;
	public static String iOSVersion;
	public static String appPath;
	public static String appiumURL;
	public static String uDID;
	public static String bundleID;
	public static String deviceName;
	public static String orientation;
	public static String scheme;
	public static String runOnDevice;

	public static void readConfiguration() {

		//Configuration properties file
		appHost = null;
		appHost = System.getProperty("net.intellectsoft.test.host");
		if (appHost == null){
			serverHost = configurationParser.getParameter("serverHost");
		}else {
			serverHost = appHost;
		}
		port = configurationParser.getParameter("port");
		protocol = configurationParser.getParameter("protocol");
		basePath = configurationParser.getParameter("basePath");
		basePathV2 = configurationParser.getParameter("basePathV2");
		demoServerHost = configurationParser.getParameter("demoServerHost");

		//Authorization properties file
		databaseServer = authorizationParser.getParameter("databaseServer");
		databaseUserName = authorizationParser.getParameter("databaseUserName");
		databasePassword = authorizationParser.getParameter("databasePassword");

		urlJira = authorizationParser.getParameter("urlJira");
		emailJira = authorizationParser.getParameter("emailJira");
		passwordJira = authorizationParser.getParameter("passwordJira");

		//Mobile properties file
		browserType = mobileParser.getParameter("BrowserType");
		platformName = mobileParser.getParameter("PlatformName");
		AndroidVersion = mobileParser.getParameter("AndroidVersion");
		iOSVersion = mobileParser.getParameter("iOSVersion");
		String userDir = System.getProperty("user.dir");
		String localApp = mobileParser.getParameter ("AppName");
		appPath = Paths.get(userDir, localApp).toAbsolutePath().toString();
		appiumURL = mobileParser.getParameter("AppiumURL");
		uDID = mobileParser.getParameter("UDID");
		deviceName = mobileParser.getParameter("DeviceName");
		orientation = mobileParser.getParameter("Orientation");
		scheme = mobileParser.getParameter("Scheme");
		runOnDevice = mobileParser.getParameter("RunOnDevice");

		if (serverHost.equals(demoServerHost)){
			stagefs = true;
			protocol = "https";
			serverHost = demoServerHost;
		}
	}
}
