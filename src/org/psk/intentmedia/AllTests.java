package org.psk.intentmedia;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Simple facility to execute all tests.
 * @author Pete
 */
@RunWith(Suite.class)
@SuiteClasses({ PointOfSaleTest.class, PointOfSaleMissingPriceTest.class })
public class AllTests {

}
