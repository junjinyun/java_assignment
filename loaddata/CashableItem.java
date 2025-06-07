package loaddata;

public class CashableItem {
    private int id;
    private String name;
    private String information;
    private int marketPrice;
    private int maxNumber;
    private int minDropAmount;
    private int maxDropAmount;

    public CashableItem() {
    }

    public CashableItem(int id, String name, String information, int marketPrice, int maxNumber, int minDropAmount, int maxDropAmount) {
        this.id = id;
        this.name = name;
        this.information = information;
        this.marketPrice = marketPrice;
        this.maxNumber = maxNumber;
        this.minDropAmount = minDropAmount;
        this.maxDropAmount = maxDropAmount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    public int getMarketPrice() {
        return marketPrice;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public int getMinDropAmount() {
        return minDropAmount;
    }

    public int getMaxDropAmount() {
        return maxDropAmount;
    }
}
