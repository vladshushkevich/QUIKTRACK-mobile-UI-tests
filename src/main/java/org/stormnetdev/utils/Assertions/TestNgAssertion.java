package org.stormnetdev.utils.Assertions;

import org.testng.Assert;

import java.math.BigInteger;

/**
 * Created by baranovski on 2/5/18.
 */
public class TestNgAssertion {


    public static void assertGreater(BigInteger number1, BigInteger number2) {
        if (number2.compareTo(number1) != -1) {
            Assert.fail("Values " + number1 + " is not greater than " + number2);
        }
    }
}
