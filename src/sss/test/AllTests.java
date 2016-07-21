package sss.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ StockTest.class, SuperSimpleStockTest.class, TradeTest.class })
public class AllTests {

}
