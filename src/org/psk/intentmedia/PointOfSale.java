/**
 * 
 */
package org.psk.intentmedia;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * This is the API to the POS system. It allows product prices to be set, products to be 
 * scanned and the final total calculated. 
 * Implementation Note: rounding is not performed until the end, which generally is the 
 * preferred behaviour in financial systems.
 * 
 * @author Pete
 */
public class PointOfSale {

	// I'd like to use typedefs here to avoid all this boilerplate but the
	// extension
	// workaround in Java is apparently an anti-pattern:
	// http://www.ibm.com/developerworks/java/library/j-jtp02216/index.html

	// Product --> <MinNumProducts, Calculators>
	// e.g. A --> < [4, CalcFourForSevenDollars], [1, CalcOneForTwoDollars] >
	// Note that Calculators is sorted biggest bulk order first so this system supports
	// multi-level pricing like 6 for $6, 3 for $3.50, 1 for $1.50
	private Map<String, Map<Integer, ProductCalculator>> pricingSystem = new HashMap<String, Map<Integer, ProductCalculator>>();

	private ScannedProducts scanned = new ScannedProducts();

	/**
	 * Set the bulk price for a given number of products e.g. $5 for 5 Bimbams, $3 for 1 Foopy.
	 * Creates a new ProductCalculator to encapsulate this information.
	 * 
	 * @param prodCode product identifier
	 * @param num number of products for this bulk price. Note that this can be 1.
	 * @param total cost of num products
	 */
	public void setPricing(final String prodCode, int num, float price) {
		ProductCalculator calc = new ProductCalculator(prodCode, num, price);

		// Add this calculator to the set of calculators for this this product. Note that the
		// Calculators are ordered such that the price for the largest bulk size is used first,
		// which is typically how supermarkets price products.
		Map<Integer, ProductCalculator> calcs = pricingSystem.get(prodCode);
		if (calcs == null) {
			calcs = new TreeMap<Integer, ProductCalculator>(Collections.reverseOrder()); // TreeMap sorts biggest bulk first
			calcs.put(num, calc);
			pricingSystem.put(prodCode, calcs);
		} else {
			calcs.put(num, calc);			
		}
	}

	/**
	 * Convenience wrapper for single-product pricing
	 * @param prodCode product to be priced
	 * @param price cost of a single product
	 */
	public void setPricing(final String prodCode, float price) {
		setPricing(prodCode, 1, price);
	}
	
	/**
	 * Represents the act of scanning an item at checkout. Simply adds the product to the list
	 * of already scanned products.
	 * 
	 * @throws NoSuchProduct If there's no price for this product.
	 * @param prodCode product identifier
	 */
	public void scan(final String prodCode) {
		Map<Integer, ProductCalculator> calcs = pricingSystem.get(prodCode);
		if (calcs == null)
			throw new MissingPrice(prodCode);

		scanned.add(prodCode);
	}

	/**
	 * Return to 2 d.p. the current total. Analogous to the display on the POS in a supermarket.
	 * Gets the number of each product that were scanned e.g. 6 oranges. Then tries each calculator
	 * registered for that product, in descending order of bulk size, deducting the appropriate 
	 * number of products as it goes.
	 *   
	 * @return the total cost of all scanned products.
	 * @throws MissingPrice if no price was registered for a scanned product
	 */
	public float total() {
		float total = 0.0F;
		
		// for all the products bought
		for (Map.Entry<String, Integer> prod : scanned.getProducts()) {

			final String prodCode = prod.getKey();			// product code
			final Integer numScanned = prod.getValue();		// number scanned
			
			// get the registered calculators for this product
			Map<Integer, ProductCalculator> calcs = pricingSystem.get(prodCode);
			if (calcs == null) 
				throw new MissingPrice(prodCode);
			
			int remaining = numScanned;	// int is mutable, Integer is not
			float prodTotal = 0.0F;
			
			for (Map.Entry<Integer, ProductCalculator> entry : calcs.entrySet()) {
				ProductCalculator calc = entry.getValue();
				
				// Apply this calculator to our product totals. Has no effect if remaining 
				// is l.t. the calculator's bulk size. 
				prodTotal += calc.getProdTotal(remaining);
				remaining -= calc.numApplicable(remaining);
			}
			
			// There shouldn't be any remaining unpriced products
			if (remaining > 0)
				throw new MissingPrice(prodCode);

			total += prodTotal;
		}

		return Math.round(total * 100.0F) / 100.0F;	// round to nearest penny
	}
}
