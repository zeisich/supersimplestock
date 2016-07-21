/**
 * 
 */
package sss;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import sss.structures.Stock;
import sss.structures.Trade;
import sss.structures.TradeType;

/** A super simple stock market.
 * @author Robert Giegerich
 *
 */
public class SuperSimpleStock {
	private HashMap<String, Stock> stocks;
	private HashMap<String, LinkedList<Trade>> trades;
	
	public SuperSimpleStock(HashMap<String, Stock> stocks, HashMap<String, LinkedList<Trade>> trades) {
		this.stocks = stocks;
		// Trades should be ordered by descending time stamp.
		for (LinkedList<Trade> list : trades.values()) {
			list.sort(Trade.DateComparator.reversed());
		}
		this.trades = trades;
	}	
	
	/** Lists a stock to be traded with the SuperSimpleStock.
	 * @param stock The stock to be listed.
	 * @param tradeHistory The collection of Trade objects representing the trade history of the stock.
	 */
	public void listStock(Stock stock, List<Trade> tradeHistory) {
		if (stocks.containsKey(stock.getSymbol())) {
			throw new IllegalArgumentException("Stock with symbol " + stock.getSymbol() + " already listed.");
		} else {
			this.stocks.put(stock.getSymbol(), stock);
			// Sort by time stamp in reversed order to have the most current trade at the beginning of the list
			tradeHistory.sort(Trade.DateComparator.reversed());
			this.trades.put(stock.getSymbol(), new LinkedList<Trade>(tradeHistory));
		}
	}
	
	/** Records a trade on this SuperSimpleStock.
	 * @param stockSymbol The traded stock.
	 * @param amount The amount of shares traded.
	 * @param price The price the stock is traded for.
	 * @param type Indicates whether it is a sell or a buy trade.
	 * @throws IllegalArgumentException in case the stock is not listed on this SuperSimpleStock.
	 */
	public void recordTrade(String stockSymbol, int amount, int price, TradeType type) {
		if (isStockListed(stockSymbol)) {
			trades.get(stockSymbol).addFirst(new Trade(new Date(System.currentTimeMillis()), amount, type, price));
		} else {
			throw new IllegalArgumentException("Could not record trade: Stock " + stockSymbol + " is unknown.");
		}
	}
	
	/** The dividend yield of a listed stock.
	 * @param stockSymbol The stock to get the dividend yield for.
	 * @param price The price used to calculate the dividend yield.
	 * @return The return to price ratio.
	 * @throws IllegalArgumentException in case the price is lower than or equal to zero and in case the stock is not listed.
	 */
	public double getDividendYield(String stockSymbol, int price) {
		if (price <= 0) {
			throw new IllegalArgumentException("Price must be an integer greater than zero.");
		}
		Stock stock = retrieveStock(stockSymbol);
		switch (stock.getType()) {
		case COMMON:
			return (double)stock.getLastDividend() / price;
		case PREFERRED:
			return ((stock.getFixedDividend() / 100.0) * stock.getParValue()) / price;
		default:
			return -1;
		}
		
	}
	
	/** The P/E Ratio of a listed stock.
	 * @param stockSymbol The stock to get the P/E Ratio for.
	 * @param price The price used to calculate the P/E ratio for.
	 * @return 0 in case the last dividend was 0. Price divided by last dividend in all other cases.
	 * @throws IllegalArgumentException in case the price is lower than or equal to zero and in case the stock is not listed.
	 */
	public double getPERatio(String stockSymbol, int price) {
		if (price <= 0) {
			throw new IllegalArgumentException("Price must be an integer greater than zero.");
		}
		Stock stock = retrieveStock(stockSymbol);
		int lastDividend = stock.getLastDividend();
		if (lastDividend == 0) {
			return 0;
		} else {
			return (double) price / lastDividend;
		}
	}
	
	/** The volume weighted price.
	 * @param stockSymbol The stock to get the current volume weighted price for.
	 * @return The volume weighted price based on the trades of the last five minutes.
	 * @throws IllegalArgumentException in case the stock is not listed.
	 */
	public double getCurrentVolumeWeightedPrice(String stockSymbol) {
		LinkedList<Trade> relevantTrades = new LinkedList<Trade>();
		if (isStockListed(stockSymbol)) {
			for (Trade trade : trades.get(stockSymbol)) {
				// Only trades within an interval of five minutes are relevant
				if (System.currentTimeMillis() - trade.getTimestamp().getTime() <= 300000) {
					relevantTrades.add(trade);
				} else {
					// trades is sorted, so all time stamps to come are from an earlier time
					break;
				}
			}
		} else {
			throw new IllegalArgumentException("Stock " + stockSymbol + " is unknown.");
		}
		int totalAmount = 0;
		int totalTradingVolume = 0;
		for (Trade trade : relevantTrades) {
			totalAmount += trade.getAmount();
			totalTradingVolume += trade.getPrice() * trade.getAmount();
		}
		if (totalAmount == 0) {
			return 1;
		}
		return (double) totalTradingVolume / totalAmount;
	}
	
	/** The GBCE All Share Index.
	 * @return The geometric mean of the volume weighted prices of all listed stocks.
	 */
	public double getGBCEAllShareIndex() {
		double accumulator = 1;
		for (Stock stock : stocks.values()) {
			accumulator *= getCurrentVolumeWeightedPrice(stock.getSymbol());
		}
		return Math.pow(accumulator, 1.0 / stocks.size());
	}
	
	/** Checks whether a stock with a specific symbol is listed.
	 * @param stockSymbol The symbol to check for.
	 * @return true when the stock is listed.
	 */
	public boolean isStockListed(String stockSymbol) {
		return stocks.containsKey(stockSymbol);
	}
	
	/** Retrieves a stock by its symbol. 
	 * @param stockSymbol The symbol of the stock to be retrieved.
	 * @return The listed stock.
	 * @throws IllegalArgumentException in case the stock is not listed
	 */
	public Stock retrieveStock(String stockSymbol) {
		Stock stock = stocks.get(stockSymbol);
		if (stock == null) {
			throw new IllegalArgumentException("Stock " + stockSymbol + " is unknown");
		}
		return stock;
	}
}
