/**
 * 
 */
package sss.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sss.SuperSimpleStock;
import sss.structures.Stock;
import sss.structures.StockType;
import sss.structures.Trade;
import sss.structures.TradeType;

/**
 * @author Robert Giegerich
 *
 */
public class SuperSimpleStockTest {
	Trade[] history = new Trade[]{
			new Trade(new Date(System.currentTimeMillis() - 100000), 20, TradeType.BUY, 2),
			new Trade(new Date(System.currentTimeMillis() - 200000), 50, TradeType.SELL, 4),
			new Trade(new Date(System.currentTimeMillis() - 250000), 30, TradeType.BUY, 2),
			new Trade(new Date(System.currentTimeMillis() - 400000), 50, TradeType.SELL, 4),
			new Trade(new Date(System.currentTimeMillis() - 600000), 200, TradeType.BUY, 10)
			};
	Trade[] oldHistory = new Trade[]{
			new Trade(new Date(System.currentTimeMillis() - 390000), 10, TradeType.SELL, 4),
			new Trade(new Date(System.currentTimeMillis() - 400000), 10, TradeType.SELL, 4),
			new Trade(new Date(System.currentTimeMillis() - 600000), 10, TradeType.BUY, 4)
			};
	SuperSimpleStock sssUnderTest;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		sssUnderTest = new SuperSimpleStock(new HashMap<String, Stock>(), new HashMap<String, LinkedList<Trade>>());
	}


	/**
	 * Test method for {@link sss.SuperSimpleStock#listStock(sss.structures.Stock, java.util.LinkedList)}.
	 */
	@Test
	public void testListAndRetrieveStock() {
		Stock tea = new Stock("TEA", StockType.COMMON, 0, 0, 100);
		sssUnderTest.listStock(tea, new LinkedList<Trade>(Arrays.asList(oldHistory)));
		assertTrue(sssUnderTest.retrieveStock("TEA").equals(tea));
		thrown.expect(IllegalArgumentException.class);
		sssUnderTest.listStock(tea, new LinkedList<Trade>(Arrays.asList(oldHistory)));
	}

	/**
	 * Test method for {@link sss.SuperSimpleStock#recordTrade(java.lang.String, int, int, sss.structures.TradeType)}.
	 */
	@Test
	public void testRecordTrade() {
		// test recording of trade with its impact on volume weighted price
		sssUnderTest.listStock(new Stock("TEA", StockType.COMMON, 0, 0, 100), new LinkedList<Trade>(Arrays.asList(oldHistory)));
		sssUnderTest.recordTrade("TEA", 100, 2, TradeType.SELL);
		assertEquals(2, sssUnderTest.getCurrentVolumeWeightedPrice("TEA"), 0);
		// test for exception in case of unlisted stock
		thrown.expect(IllegalArgumentException.class);
		sssUnderTest.recordTrade("GIN", 100, 1, TradeType.SELL);

	}

	/**
	 * Test method for {@link sss.SuperSimpleStock#getDividendYield(java.lang.String, int)}.
	 */
	@Test
	public void testGetDividendYield() {
		// test for zero dividend
		sssUnderTest.listStock(new Stock("TEA", StockType.COMMON, 0, 0, 100), new LinkedList<Trade>(Arrays.asList(oldHistory)));
		assertEquals(0, sssUnderTest.getDividendYield("TEA", 100), 0);
		// test for positive dividend
		sssUnderTest.listStock(new Stock("POP", StockType.COMMON, 8, 0, 100), new LinkedList<Trade>(Arrays.asList(oldHistory)));
		assertEquals(8, sssUnderTest.getDividendYield("POP", 1), 0);
		// test for preferred stock type
		sssUnderTest.listStock(new Stock("GIN", StockType.PREFERRED, 8, 2, 100), new LinkedList<Trade>(Arrays.asList(oldHistory)));
		assertEquals(2, sssUnderTest.getDividendYield("GIN", 1), 0);
		// test for exception in case of invalid price argument
		thrown.expect(IllegalArgumentException.class);
		sssUnderTest.getDividendYield("GIN", 0);
	}

	/**
	 * Test method for {@link sss.SuperSimpleStock#getPERatio(java.lang.String, int)}.
	 */
	@Test
	public void testGetPERatio() {
		// test for zero dividend
		sssUnderTest.listStock(new Stock("TEA", StockType.COMMON, 0, 0, 100), new LinkedList<Trade>(Arrays.asList(oldHistory)));
		assertEquals(0, sssUnderTest.getPERatio("TEA", 1), 0);
		// test for positive dividend
		sssUnderTest.retrieveStock("TEA").setLastDividend(5);
		assertEquals(0.2, sssUnderTest.getPERatio("TEA", 1), 0);
		// test for exception in case of invalid price
		thrown.expect(IllegalArgumentException.class);
		sssUnderTest.getPERatio("TEA", 0);
	}

	/**
	 * Test method for {@link sss.SuperSimpleStock#getCurrentVolumeWeightedPrice(java.lang.String)}.
	 */
	@Test
	public void testGetCurrentVolumeWeightedPrice() {
		// test with no trades in last five minutes
		sssUnderTest.listStock(new Stock("TEA", StockType.COMMON, 0, 0, 100), new LinkedList<Trade>(Arrays.asList(oldHistory)));
		assertEquals(1, sssUnderTest.getCurrentVolumeWeightedPrice("TEA"), 0);
		// test with trades in last five minutes
		sssUnderTest.listStock(new Stock("GIN", StockType.COMMON, 0, 0, 100), new LinkedList<Trade>(Arrays.asList(history)));
		assertEquals(3.0, sssUnderTest.getCurrentVolumeWeightedPrice("GIN"), 0);
		// test for exception in case of unlisted stock
		thrown.expect(IllegalArgumentException.class);
		sssUnderTest.getCurrentVolumeWeightedPrice("POP");
	}

	/**
	 * Test method for {@link sss.SuperSimpleStock#getGBCEAllShareIndex()}.
	 */
	@Test
	public void testGetGBCEAllShareIndex() {
		// test with trades in last five minutes for all listed stocks
		sssUnderTest.listStock(new Stock("TEA", StockType.COMMON, 0, 0, 100), new LinkedList<Trade>(Arrays.asList(history)));
		sssUnderTest.listStock(new Stock("POP", StockType.COMMON, 0, 0, 100), new LinkedList<Trade>(Arrays.asList(history)));
		sssUnderTest.listStock(new Stock("GIN", StockType.COMMON, 0, 0, 100), new LinkedList<Trade>(Arrays.asList(history)));
		assertEquals(3.0, sssUnderTest.getGBCEAllShareIndex(), 0);
		sssUnderTest.recordTrade("TEA", 10, 14, TradeType.BUY);
		assertEquals(3.3, sssUnderTest.getGBCEAllShareIndex(), 0.002);
		// test with stock without trades in the last five minutes
		sssUnderTest.listStock(new Stock("JOE", StockType.COMMON, 0, 0, 100), new LinkedList<Trade>(Arrays.asList(oldHistory)));
		assertEquals(2.449, sssUnderTest.getGBCEAllShareIndex(),0.0005);

	}

	/**
	 * Test method for {@link sss.SuperSimpleStock#isStockListed(java.lang.String)}.
	 */
	@Test
	public void testIsStockListed() {
		assertFalse(sssUnderTest.isStockListed("TEA"));
		sssUnderTest.listStock(new Stock("TEA", StockType.COMMON, 0, 0, 100), new LinkedList<Trade>(Arrays.asList(oldHistory)));
		assertTrue(sssUnderTest.isStockListed("TEA"));
	}

}
