package org.stormnetdev.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.stormnetdev.reporter.Reporter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GlobalVariablesParser {
	
	public static final String dynamicVariablesFileName = "./test-input/globalDynamicVariables.json";
	public static final String staticVariablesFileName = "./test-input/globalStaticVariables.json";

	private static GlobalVariablesParser instance;
	
	private FileWriter fileWriter;
	private FileReader fileReader;
	
	public static GlobalVariablesParser getInstanse(){
		if(instance == null){
			instance = new GlobalVariablesParser();
		}
		return instance;
	}
	
	public void updateGlobalVariable(String parameter, String value){
		Reporter.logOperation("Set global parameter '" + parameter);
		Reporter.logInfo("Value: '" + value + "'");
		JSONObject obj = getJsonObject();
		obj.put(parameter, value);
		updateJsonFile(obj);
		Reporter.logPassedOperation();
	}
	
	private JSONObject getJsonObject() {
		Reporter.logOperation("Getting json file with global variables.");
		try {
			fileReader = new FileReader(dynamicVariablesFileName);
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(fileReader);
			JSONObject jsonObject = (JSONObject) obj;
			fileReader.close();
			Reporter.logPassedOperation();
			return jsonObject;
		} catch (IOException e) {
			Reporter.logFailed("Json was not received.");
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			Reporter.logFailed("Json was not received or file is empty.");
			e.printStackTrace();
			return null;
		}
	}
	
	private void updateJsonFile(JSONObject obj) {
		Reporter.logOperation("Update JSON file.");
		try {
			fileWriter = new FileWriter(dynamicVariablesFileName);
			fileWriter.write(obj.toString());
			fileWriter.flush();
			fileWriter.close();
			Reporter.logPassedOperation();
		} catch (IOException e) {
			Reporter.logFailed("Json was not updated.");
			e.printStackTrace();
		}
	}
	
	private String getGlobalValueFromFile(String value, String currentFileName) {
		try {
			Reporter.logOperation("Getting value '" + value + "'");
			fileReader = new FileReader(currentFileName);
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(fileReader);
			JSONObject jsonObject = (JSONObject) obj;
			Reporter.logInfo("Value is: " + (String) jsonObject.get(value));
			fileReader.close();
			Reporter.logPassedOperation();
			return (String) jsonObject.get(value);
		} catch (IOException e) {
			Reporter.logFailed("Json was not received.");
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			Reporter.logFailed("Json was not received or file is empty.");
			e.printStackTrace();
			return null;
		}
	}

	public String getStaticGlobalValue(String value) {
		return getGlobalValueFromFile(value, staticVariablesFileName);
	}

	public String getDynamicGlobalValue(String value) {
		return getGlobalValueFromFile(value, dynamicVariablesFileName);
	}
}
