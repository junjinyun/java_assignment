package gameplay.Item;

public class Item {
    private int id;
    private String name;
    private int price;
    private String information;

    public Item(int id, String name, int price, String information) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.information = information;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getInformation() {
        return information;
    }

    @Override
    public String toString() {
        return name + " - " + price + "G";
    }
}