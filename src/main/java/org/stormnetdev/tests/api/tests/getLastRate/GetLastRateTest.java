package org.stormnetdev.tests.api.tests.getLastRate;

import io.qameta.allure.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponseOptions;
import org.stormnetdev.reporter.Reporter;
import org.stormnetdev.tests.api.folder.GetLastRate;
import org.stormnetdev.tests.api.tests.TestBaseAPI;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/*
 * @author i.baranovski
 * GetLastRateTest test.
*/

public class GetLastRateTest extends TestBaseAPI {

	private String currencyPair = "GLP/ETH";

	@Step("Calculate Fiat currency rate and compare with received value")
	private void getResponse(){
		GetLastRate request = new GetLastRate(currencyPair);
		ValidatableResponseOptions response = request.getResponse()
				.statusCode(200);
		Reporter.logInfo("Status code is: " + response.extract().statusCode());
		JsonPath jsonPath = new JsonPath(response.extract().body().asString());
		assertThat(jsonPath.getJsonObject("result"), not(nullValue()));
	}

	@Test(description = "Return last currency rates in the system.")
	@Description("In this test we receive last currency rate that exists in the system and check status code " +
			"and that result is not null in response.")
	@Severity(SeverityLevel.BLOCKER)
	@Feature("Fetching and Storing the data from external sources into DB - FGM-528")
	@Story("GetLastRate")
	@Issues({@Issue("FGM-840")})


	private void getLastRateTest() {
		getResponse();
	}
}
