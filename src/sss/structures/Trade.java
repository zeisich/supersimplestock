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
	
	public Trade(Date timestamp, int amount, TradeType tradeType, int price) {
		this.timestamp = timestamp;
		this.amount = amount;
		this.tradeType = tradeType;
		this.price = price;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
