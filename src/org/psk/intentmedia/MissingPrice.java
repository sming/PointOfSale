package org.psk.intentmedia;

/**
 * Represents the unexpected scenario of a product being scanned for which no price
 * has been set. In a real system, this would be handled appropriately, obviously.
 * 
 * @author Pete
 */
public class MissingPrice extends RuntimeException {
	// stops Eclipse from whining about serializable warning
	private static final long serialVersionUID = 1882468348579325740L;

	/**
	 * @param prodCode product identifier.
	 */
	MissingPrice(String prodCode) {
		super("The price for product "+prodCode+" could not be found.");
	}

}
