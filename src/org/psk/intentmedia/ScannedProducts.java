package org.psk.intentmedia;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Simple container for products that have been scanned. We want to track how many
 * of each product was scanned.
 * @author Pete
 */
public class ScannedProducts {
	// TreeMap will sort the products alphabetically for us, should we want to print the
	// receipt.
	private Map<String, Integer> prods = new TreeMap<String, Integer>();
	
	void add(final String prodCode) {
		Integer prodCount = prods.get(prodCode);
		int newVal = 0;
		if (prodCount == null)
			newVal = 1;
		else
			newVal = prodCount + 1;
		
		prods.put(prodCode, newVal);
	}
	
	Set<Map.Entry<String, Integer>> getProducts() {
		return prods.entrySet();
	}
}
