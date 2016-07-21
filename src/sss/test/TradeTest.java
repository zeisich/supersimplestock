/**
 * 
 */
package sss.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sss.structures.Trade;
import sss.structures.TradeType;

/**
 * @author Robert Giegerich
 *
 */
public class TradeTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	

	/**
	 * Test method for {@link sss.structures.Trade#Trade(java.util.Date, int, sss.structures.TradeType, int)}.
	 */
	@Test
	public void testTrade() {
		new Trade(new Date(System.currentTimeMillis()), 100, TradeType.BUY, 1);
		thrown.expect(IllegalArgumentException.class);
		new Trade(new Date(System.currentTimeMillis()), 100, TradeType.SELL, 0);
	}

}
