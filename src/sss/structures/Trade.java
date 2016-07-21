/**
 * 
 */
package sss.structures;

import java.util.Comparator;
import java.util.Date;

/**
 * @author Robert Giegerich
 *
 */
public class Trade {
	private Date timestamp;
	private int amount;
	private TradeType tradeType;
	private int price;
	public static Comparator<Trade> DateComparator = (Trade t1, Trade t2) -> t1.getTimestamp().compareTo(t2.getTimestamp());
	
	/**
	 * @param timestamp The time when the trade takes/took place;
	 * @param amount The amount of shares traded.
	 * @param tradeType Indicates sell or buy trade.
	 * @param price The price per share. Must be greater than zero.
	 * @throws IllegalArgumentException in case the price is not greater than zero.
	 */
	public Trade(Date timestamp, int amount, TradeType tradeType, int price) {
		if (price <= 0) {
			throw new IllegalArgumentException("Trades must be conducted with a price greater than zero.");
		}
		this.timestamp = timestamp;
		this.amount = amount;
		this.tradeType = tradeType;
		this.price = price;
	}

	/**
	 * @return the time stamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @return the tradeType
	 */
	public TradeType getTradeType() {
		return tradeType;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

}
