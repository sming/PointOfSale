package org.psk.intentmedia;

import org.junit.Before;
import org.junit.Test;

/**
 * Test that a missing price is detected and acted upon correctly. Has to be in its own class since
 * it cannot share the same setUp() method.
 * 
 * @author Pete
 */
public class PointOfSaleMissingPriceTest {
	static PointOfSale pos;

	@Before
	public void setUp() throws Exception {
		pos = new PointOfSale();
	}

	@Test(expected=MissingPrice.class)
	public void testNoPricesSet() {
		pos.scan("A");
		pos.scan("B");
		pos.scan("C");
	}
	
	/**
	 * if only a price has been specified for bulk quantities, it's feasible that
	 * a price not be found for purchases of smaller quantities of the product.
	 */
	@Test(expected=MissingPrice.class)
	public void testNotEnoughBought() {
		pos.setPricing("A", 4, 7);

		pos.scan("A");
		pos.total();
	}


}
