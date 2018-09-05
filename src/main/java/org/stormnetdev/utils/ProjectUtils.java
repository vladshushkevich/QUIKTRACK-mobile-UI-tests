package org.stormnetdev.utils;

import io.restassured.response.Response;
import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.DateTime;
import org.stormnetdev.reporter.Reporter;
import org.stormnetdev.utils.transactions.Convert;
import org.testng.Assert;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

import static java.lang.Math.*;
import static org.apache.commons.lang.StringUtils.leftPad;
import static org.stormnetdev.reporter.Reporter.logPassedOperation;
import static org.testng.Assert.fail;

public class ProjectUtils {
 
    public static Assertion hardAssert = new Assertion();
    public static SoftAssert softAssert = new SoftAssert();
    
    public static Assertion getHardAssertion() {
    	return hardAssert;
    }
   
    public static SoftAssert getSoftAssertion() {
    	return softAssert;
    }
    
    /**
     * Sleep method. 
     * @throws InterruptedException *
     */
    public static void sleep(int time){
		try {
    		Reporter.logOperation("Sleeping for: " + time + " seconds.");
			Thread.sleep(time * 1000);
    		Reporter.logPassedOperation();
		} catch (InterruptedException e) {
    		Reporter.logFailed("Error during sleeping " + time + " seconds.");
			e.printStackTrace();
		}
    }
    
    /**
     * Assert that value contains X symbols. 
     */
    
    public static void assertElementLengh(Response response, String assertionElement, int assertionLengh) {
    	Reporter.logOperation("Asserting element '" + assertionElement + "' lengh is : " + assertionLengh);
    	try {
    		getHardAssertion().assertTrue(response.path(assertionElement).toString().length() == assertionLengh);
	    	logPassedOperation();
		} catch (AssertionError e) {
			Reporter.logFailed("Expected: " + assertionLengh + " symbols \n" + "Actual: " + response.path(assertionElement).toString().length() + " symbols");
			Assert.fail();
		}
	}

    /**
	 * Random Phone.
	 */

	public static String getRandomPhone() {
		Reporter.logOperation("Get random phone number.");
		Random rand = new Random();
		String phone = "";
		for(int i = 0; i < 12; i++){
			phone = phone + rand.nextInt(9);
		}
		String phoneNumber = "+" + phone;
		Reporter.logInfo("Phone is " + phoneNumber);
		logPassedOperation();
		return phoneNumber;
	}

	/**
	 * Random Passphrase.
	 */

	public static String getRandomPassphrase() {
		Reporter.logOperation("Generate random passphrase.");
		String passphrase = "test passphrase #" + DateTime.now().getMillis();
		Reporter.logInfo("Passphrase is " + passphrase);
		logPassedOperation();
		return passphrase;
	}

	/**
	 * Random public key.
	 */

	public static String randomPublicKey(int length) {
		Reporter.logOperation("Generate random public key.");
		StringBuffer sb = new StringBuffer();
		for (int i = length/2; i > 0; i -= 12) {
			int n = min(12, abs(i));
			sb.append(leftPad(Long.toString(round(random() * pow(36, n)), 36), n, '0'));
		}
		String publicKey = sb.toString();

		byte[] message = Convert.toBytes(publicKey);
		publicKey = Convert.toHexString(message);
		Reporter.logInfo("Public key is " + publicKey);
		logPassedOperation();
		return publicKey;
	}

	/**
	 * Random GEC address.
	 */

	public static String randomGECAddress() {
		Reporter.logOperation("Generate random GEC address.");
		String rs = null;
		rs = "GEC-" + RandomStringUtils.randomAlphanumeric(4).toUpperCase() + "-"
				+ RandomStringUtils.randomAlphanumeric(4).toUpperCase() + "-"
				+ RandomStringUtils.randomAlphanumeric(4).toUpperCase() + "-"
				+ RandomStringUtils.randomAlphanumeric(4).toUpperCase();
		RandomStringUtils.randomAlphanumeric(4);
		return rs;
	}
	/**
	 * Random string.
	 */

	public static String randomString() {
		Reporter.logOperation("Generate random public key.");
		String random = Long.toHexString(Double.doubleToLongBits(Math.random()));
		return random;
	}


	/**
	 * Get random int
	 */

	public static int randomInt(int min, int max) {
		Reporter.logOperation("Generate random integer number between " + min + " value and " + max);
		Random random = new Random();
		Reporter.logPassedOperation();
		return random.nextInt(max - min + 1) + min;
	}

	/**
	 * Get random long
	 */

	public static long randomLong(int min, int max) {
		Reporter.logOperation("Generate random long number between " + min + " value and " + max);
		Reporter.logPassedOperation();
		long generatedLong = min + (long) (Math.random() * (max - min));
		return generatedLong;
	}

	/**
	 * Get random digits string
	 */

	public static String randomDigitsString(int count) {
		Reporter.logOperation("Generate random digits string from " + count + " digits.");
		Random random = new Random();
		String stringNumber = "";
		int i=0;
		while(i != count){
			stringNumber += random.nextInt(9);
			i++;
		}
		Reporter.logPassedOperation();
		return stringNumber;
	}

    /**
     * Check that timestamp array is in selected days range
     */

	public static void checkThatDateInDaysRange(long afterTimestamp, long beforeTimestamp, ArrayList<String> timestamps) {
		Calendar afterDate = Calendar.getInstance();
		afterDate.setTimeZone(TimeZone.getTimeZone("UTC"));
		afterDate.setTimeInMillis(afterTimestamp * 1000);
		afterDate.set(Calendar.AM_PM, Calendar.AM);
		afterDate.set(Calendar.HOUR, 0);
		afterDate.set(Calendar.MINUTE, 0);
		afterDate.set(Calendar.SECOND, 0);
		afterDate.set(Calendar.MILLISECOND, 0);

		Calendar beforeDate = Calendar.getInstance();
		beforeDate.setTimeZone(TimeZone.getTimeZone("UTC"));
		beforeDate.setTimeInMillis(beforeTimestamp * 1000);
		beforeDate.set(Calendar.AM_PM, Calendar.PM);
		beforeDate.set(Calendar.HOUR, 11);
		beforeDate.set(Calendar.MINUTE, 59);
		beforeDate.set(Calendar.SECOND, 59);
		beforeDate.set(Calendar.MILLISECOND, 0);

		Reporter.logInfo("afterDate " + afterDate.getTime().toString());
		Reporter.logInfo("beforeDate " + beforeDate.getTime().toString());

		for (int i = 0; i < timestamps.size(); i++) {
			Calendar currentValue = Calendar.getInstance();
			currentValue.setTimeZone(TimeZone.getTimeZone("UTC"));
			currentValue.setTimeInMillis(Long.valueOf(timestamps.get(i)) * 1000);
			currentValue.set(Calendar.HOUR, 0);
			currentValue.set(Calendar.AM_PM, Calendar.PM);
			currentValue.set(Calendar.MINUTE, 0);
			currentValue.set(Calendar.SECOND, 0);
			currentValue.set(Calendar.MILLISECOND, 0);

			if (!(currentValue.after(afterDate) && currentValue.before(beforeDate))) {
				fail(currentValue.getTime().toString() + " is not in the range between " + afterDate.getTime().toString() + " and " + beforeDate.getTime().toString());
			}
		}
	}

	public static BigDecimal getBigDecimal(Object value ) {
		BigDecimal ret = null;
		if( value != null ) {
			if( value instanceof BigDecimal ) {
				ret = (BigDecimal) value;
			} else if( value instanceof String ) {
				ret = new BigDecimal( (String) value );
			} else if( value instanceof BigInteger ) {
				ret = new BigDecimal( (BigInteger) value );
			} else if( value instanceof Number ) {
				ret = new BigDecimal( ((Number)value).doubleValue() );
			} else {
				throw new ClassCastException("Not possible to coerce ["+value+"] from class "+value.getClass()+" into a BigDecimal.");
			}
		}
		return ret;
	}
}
