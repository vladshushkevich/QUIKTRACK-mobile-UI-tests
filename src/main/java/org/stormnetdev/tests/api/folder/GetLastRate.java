package org.stormnetdev.tests.api.folder;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponseOptions;

public class GetLastRate extends RequestBase {

	private String currencies;

	public GetLastRate(String currencies) {
		this.currencies = currencies;
	}

	@Step("Get last updated rate for currency.")
	@Override
	public ValidatableResponseOptions getResponse(){
		super.getResponse();
		ValidatableResponseOptions result = RestAssured
				.given()
				.contentType("application/json")
				.log().all()
				.when()
				.get("/fin/rate/" + currencies)
				.then();
		reportAPIMethodData(result);
		return result;
	}
}
