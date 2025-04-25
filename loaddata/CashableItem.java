package loaddata;


public class CashableItem {
	private String Name;
    private String Information;
    private int MarketPrice;
    private int MaxNumber;
    private int MinDropAmount;
    private int MaxDropAmount;

	public CashableItem() {
	}

	public CashableItem(String name, String information, int marketprice, int max_number, int mindrop_amount, int maxdrop_amount) {
		this.Name = name;
        this.Information = information;
        this.MarketPrice = marketprice;
        this.MaxNumber = max_number;
        this.MinDropAmount = mindrop_amount;
        this.MaxDropAmount = maxdrop_amount;
	}

	public String getName() {
		return Name;
	}

	public String getInformation() {
		return Information;
	}

	public int getmarketprice() {
		return MarketPrice;
	}

	public int getMaxNumber() {
		return MaxNumber;
	}

	public int getMindropAmount() {
		return MinDropAmount;
	}

	public int getMaxDropAmount() {
		return MaxDropAmount;
	}

}
