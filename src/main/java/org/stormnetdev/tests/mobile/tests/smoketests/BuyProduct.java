package org.stormnetdev.tests.mobile.tests.smoketests;

import org.stormnetdev.utils.configuration.ConfigurationParser;
import org.stormnetdev.tests.mobile.pages.*;
import org.stormnetdev.tests.mobile.tests.TestBaseMobile;
import org.testng.annotations.Test;

/*
 * @author i.baranovski
 * Send email test.
 * Steps to reproduce:
 * 1. Tap login without Sales Force Button;
 * 2. Authorize Email;
 * 3. Go To My Inbox;
 * 4. Open Msg By Name;
 * 5. Archive message
 * 6. From Inbox To All Mail;
 * 7. Find Msg By Name
*/

public class BuyProduct extends TestBaseMobile {

	static ConfigurationParser authorization = new ConfigurationParser("authorization.properties");

    private static String emailWithoutSF = authorization.getParameter("emailWithoutSF");
    private static String passwordWithoutSF = authorization.getParameter("passwordWithoutSF");
	
    @Test
	public void buyProduct() throws InterruptedException {
		MainPage mainPage = new MainPage();
		mainPage.loaded();
//		wait(5);
//		mainPage.goToLoginPage();
//		SelectLoginPage selectLoginPage = new SelectLoginPage();
//		selectLoginPage.loginWithLoggedEmail();
//		LoginPage loginPage = new LoginPage();
//		loginPage.authorizeEmail(emailWithoutSF, passwordWithoutSF);
		mainPage.goToPage("My Wallet");
		MyWalletPage myWalletPage = new MyWalletPage();
		myWalletPage.goToCoinsPage();
		CoinsPage coinsPage = new CoinsPage();
		coinsPage.openDiscountPage();
		ProductsPage productsPage = new ProductsPage();
		productsPage.buySelectedProduct();
	}
}