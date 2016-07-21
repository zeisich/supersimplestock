package sss.structures;

/**
 * @author Robert Giegerich
 *
 */
public class Stock {
	private String symbol;
	private StockType type;
	private int lastDividend;
	private int fixedDividend;
	private int parValue;
	
	
	/**
	 * @param symbol The unique identifier of the stock.
	 * @param type Indicates whether the stock is listed as common or preferred.
	 * @param lastDividend The most recent dividend. Must be greater than or equal to zero.
	 * @param fixedDividend The fixed rate that a preferred stock returns as dividend. Must be greater than zero. Will be ignored for common stocks. 
	 * @param parValue The par value of the stock. Must be greater than zero.
	 */
	public Stock(String symbol, StockType type, int lastDividend,
			int fixedDividend, int parValue) {
		if (type == StockType.PREFERRED && fixedDividend <= 0) {
			throw new IllegalArgumentException("Preferred stocks must have a fixed dividend greater than zero.");
		}
		if (lastDividend < 0) {
			throw new IllegalArgumentException("Dividends must not be negative.");
		}
		if (parValue < 0) {
			throw new IllegalArgumentException("Par value must be an integer greater than or equal to zero.");
		}
		this.symbol = symbol;
		this.type = type;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
	}

	/**
	 * @return the type
	 */
	public StockType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(StockType type) {
		this.type = type;
	}

	/**
	 * @return the lastDividend
	 */
	public int getLastDividend() {
		return lastDividend;
	}

	/**
	 * @param lastDividend the lastDividend to set
	 */
	public void setLastDividend(int lastDividend) {
		this.lastDividend = lastDividend;
	}

	/**
	 * @return the fixedDividend
	 */
	public int getFixedDividend() {
		return fixedDividend;
	}

	/**
	 * @param fixedDividend the fixedDividend to set
	 */
	public void setFixedDividend(int fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	/**
	 * @return the parValue
	 */
	public int getParValue() {
		return parValue;
	}

	/**
	 * @param parValue the parValue to set
	 */
	public void setParValue(int parValue) {
		this.parValue = parValue;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
