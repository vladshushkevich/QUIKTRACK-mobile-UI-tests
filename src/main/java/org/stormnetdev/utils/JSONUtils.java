package org.stormnetdev.utils;

import io.restassured.path.json.JsonPath;
import org.json.simple.JSONArray;
import org.stormnetdev.reporter.Reporter;

import java.util.*;

/**
 * Created by baranovski on 2/8/18.
 */
public class JSONUtils {


    public static ArrayList<String> getParameterArrayFromJsonArray(Object jsonArray, String parameter){
        List array = (List) jsonArray;
        ArrayList<String> parameters = new ArrayList<>();
        HashMap transaction;
        for (int i = 0; i < array.size(); i++) {
            transaction = (HashMap) array.get(i);
            parameters.add(transaction.get(parameter).toString());
        }
        return parameters;
    }

    /**
     * Sorting of transactions by timestamp
     */

    public static String sortTransactionsByTimestamps (String response) {

        JsonPath jsonPath = new JsonPath(response);
        ArrayList<String> timestamps = JSONUtils.getParameterArrayFromJsonArray(jsonPath.getJsonObject("result"), "timestamp");
        Collections.sort(timestamps, Collections.reverseOrder());

        JSONArray transactions = new JSONArray();
        org.json.simple.JSONObject transaction = new org.json.simple.JSONObject();
        Map currentTransaction;

        for (String timestamp : timestamps) {
            currentTransaction = jsonPath.getMap("result.find { transaction -> transaction.timestamp == " + timestamp + " }");
            for ( Object key : currentTransaction.keySet() ) {
                transaction.put(key, currentTransaction.get(key));
            }
            transactions.add(transaction);
            transaction = new org.json.simple.JSONObject();
        }
        org.json.simple.JSONObject result = new org.json.simple.JSONObject();
        result.put("result", transactions);
        Reporter.logInfo("New JSON file is: " + result.toJSONString());
        return result.toJSONString();
    }
}
