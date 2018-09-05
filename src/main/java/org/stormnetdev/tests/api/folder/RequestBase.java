package org.stormnetdev.tests.api.folder;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponseOptions;
import org.apache.commons.io.output.WriterOutputStream;
import org.stormnetdev.reporter.Reporter;

import java.io.PrintStream;
import java.io.StringWriter;

import static io.restassured.config.LogConfig.logConfig;
import static io.restassured.config.RestAssuredConfig.config;

/**
 * Created by baranovski on 4/3/17.
 * Base class for all requests
 */

public class RequestBase {

    private static StringWriter writer = new StringWriter();
    public static PrintStream defaultPrintStream = new PrintStream(new WriterOutputStream(writer), true);

    protected ValidatableResponseOptions getResponse(){
        RestAssured.config = config().logConfig(logConfig().defaultStream(defaultPrintStream));
        return null;
    }

    protected void reportAPIMethodData(ValidatableResponseOptions result){
        Reporter.logRequest();
        Reporter.logResponse(((Response) result.extract().response()).asString());
        Reporter.logPassedStep();
    }

    public static String getRestAssuredLog() {
        writer.flush();
        String restAssuredLog = writer.toString();
        StringBuffer buffer = writer.getBuffer();
        buffer.replace(0, buffer.length(), "");
        return restAssuredLog;
    }
}
