package org.stormnetdev.tests;

import io.restassured.RestAssured;
import org.stormnetdev.reporter.Reporter;
import org.stormnetdev.utils.configuration.Configurator;
import org.testng.annotations.*;

public abstract class TestBase {

    private static String baseHost;
	public static String appHost;

    @BeforeSuite
    public static void setupURL() {
        Configurator.readConfiguration();
        appHost = System.getProperty("net.intellectsoft.test.host");

        if (Configurator.port == null) {
            RestAssured.port = Integer.valueOf("-1");
        }
        else{
            RestAssured.port = Integer.valueOf(Configurator.port);
        }

        if(Configurator.basePath==null){
            RestAssured.basePath = "/api/v1";
        }
        RestAssured.basePath = Configurator.basePath;

        if(appHost==null){
            baseHost = Configurator.protocol + "://" + Configurator.serverHost;
        }else{
            baseHost = Configurator.protocol + "://" + appHost;
        }
        System.out.println("BASE HOST VALUE: " + baseHost);
        RestAssured.baseURI = baseHost;
    }

    @BeforeSuite(dependsOnMethods={"setupURL"})
    public void setUpSuite() {
    }

    @BeforeTest
    public void setUpTest() {
        Reporter.description = null;
		// TODO Auto-generated method stub
    }


    @BeforeClass
    public void setUpClass() {
		// TODO Auto-generated method stub
    }
    
    @BeforeMethod
    public void setUpMethod(){
		// TODO Auto-generated method stub
    }
    
    @AfterMethod
    public void tearDownMethod() {
        System.out.println("");
    	System.out.println("---------------------------------------------------------");
    	System.out.println("");
    }
    
    @AfterClass
    public void tearDownClass() {
		// TODO Auto-generated method stub
    }
    
    @AfterTest
    public void tearDownTest() {
		// TODO Auto-generated method stub
    }
    
    @AfterSuite
    public void tearDownSuite() {
		// TODO Auto-generated method stub
    	}
}
