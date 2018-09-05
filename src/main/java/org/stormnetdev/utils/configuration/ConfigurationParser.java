package org.stormnetdev.utils.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationParser {
	
	Properties prop = new Properties();
	InputStream input = null;
	
	public ConfigurationParser(String fileNme) {
		openConfigurationFile(fileNme);
	}
	
	private void openConfigurationFile(String fileNme) {
		String configurationFilePath = "./test-input/"+fileNme;
		System.out.println("Using Configuration File: " + configurationFilePath);
		try {
			input = new FileInputStream(configurationFilePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			prop.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readAppiumIOSConfigurationSheet() {
		//TODO: To implement IOS Config
	}
	
	public void readAppiumAndroidConfigurationSheet() {
		//TODO: To implement Android Config
	}
	
	public String getParameter(String parameterName) {
		System.out.print("	Setting parameter: " + parameterName + " = ");
		String parameterValue = prop.getProperty(parameterName);
		System.out.println(parameterValue);
		return parameterValue;	
	}
	
}
