package org.psk.intentmedia;

import org.junit.Before;
import org.junit.Test;

/**
 * exercise the specified tests plus some edge cases. Care is taken to use JUnit's
 * assertEquals since it allows you to easily specify the number of digits accuracy
 * required.
 * @author Pete
 */
public class PointOfSaleTest {
	static PointOfSale pos;
	
	@Before
	public void setUp() throws Exception {
		pos = new PointOfSale();

		pos.setPricing("A", 2);
		pos.setPricing("A", 4, 7);
		pos.setPricing("B", 12);
		pos.setPricing("C", 1.25F);
		pos.setPricing("C", 6, 6);
		pos.setPricing("D", 0.15F);
	}

	@Test
	public void testABCDABAA() {

		pos.scan("A");
		pos.scan("B");
		pos.scan("C");
		pos.scan("D");
		pos.scan("A");
		pos.scan("B");
		pos.scan("A");
		pos.scan("A");

		float total = pos.total();
		org.junit.Assert.assertEquals(total, 32.40F, 0.001);
	}

	@Test
	public void testCCCCCCC() {

		for (int i = 0; i < 7; ++i)
			pos.scan("C");
		
		float total = pos.total();
		org.junit.Assert.assertEquals(total, 7.25F, 0.001);
	}

	@Test
	public void testABCD() {
		
		pos.scan("A");
		pos.scan("B");
		pos.scan("C");
		pos.scan("D");
		
		float total = pos.total();
		org.junit.Assert.assertEquals(total, 15.40F, 0.001);
	}
	
	@Test
	public void testAs() {
		
		for (int i = 0; i < 8; ++i)
			pos.scan("A");
		
		float total = pos.total();
		org.junit.Assert.assertEquals(total, 14.00F, 0.001);
		
		pos.scan("A");
		total = pos.total();
		org.junit.Assert.assertEquals(total, 16.00F, 0.001);
	}
	
	@Test
	public void testZeroItems() {
		float total = pos.total();
		org.junit.Assert.assertEquals(total, 0.0F, 0.001);
	}
	
}
