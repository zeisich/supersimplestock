package sss.test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sss.structures.Stock;
import sss.structures.StockType;

/**
 * @author Robert Giegerich
 *
 */
public class StockTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testStockLastDividend() {
		// test that exception is thrown when last dividend is negative
		thrown.expect(IllegalArgumentException.class);
		new Stock("TEA", StockType.COMMON, -1, 0, 100);
	}
	
	@Test
	public void testStockPreferred() {
		// test that exception is thrown when stock type is preferred and fixed dividend is negative
		new Stock("TEA", StockType.PREFERRED, 0, 2, 100);
		thrown.expect(IllegalArgumentException.class);
		new Stock("TEA", StockType.PREFERRED, -1, 0, 100);
	}
	
	@Test
	public void testStockParValue() {
		// test that exception is thrown when par value is negative
		thrown.expect(IllegalArgumentException.class);
		new Stock("TEA", StockType.COMMON, 1, 1, -1);
	}
}
