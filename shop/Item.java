package shop;

/* 아이템 관리 */

public class Item {
    int id;
    String name;
    String information;
    int price;
    int max_number;


    public Item() { }

    public Item(int id, String name, String information, int price, int max_number) {
        this.id = id;
        this.name = name;
        this.information = information;
        this.price = price;
        this.max_number = max_number;
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

    public int getPrice() {
        return price;
    }

    public int getMax_number() {
        return max_number;
    }

    @Override
    public String toString() {
        return name + " - " + price + "G";
    }
}
