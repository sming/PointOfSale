package org.psk.intentmedia;

/**
 * Encapsulates "a product pricing". Each product may have one or more product pricings. Typically,
 * a product will always have a price for a purchase size of one.
 * 
 * @author Pete
 */
public class ProductCalculator {
	final String prodCode;
	final int minQualifyingNum;
	final float unitPrice;

	public ProductCalculator(final String prodCode, int num, float price) {
		this.prodCode = prodCode;
		this.minQualifyingNum = num;
		this.unitPrice = price / minQualifyingNum;
	}

	boolean isApplicable(Integer numPurchased) {
		return numPurchased >= minQualifyingNum;
	}

	float getUnitPrice() {
		return unitPrice;
	}

	public float getProdTotal(int remaining) {
		if (remaining >= minQualifyingNum) {
			return unitPrice * numApplicable(remaining);
		} else {
			return 0.0F;
		}
	}

	public int numApplicable(int remaining) {
		return remaining - (remaining % minQualifyingNum); 	// gives remainder
	}
}
