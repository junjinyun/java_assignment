package dungeon;

public class GimmickItem(){
    private int id;
    private String name;
    private String information;
    private int price;
    private int max_number;

} 
public UsableItem() {}

    public UsableItem(int id, String name, int information, int price, int max_number) {
        
        this.id = id;
        this.name = name;
        this.information = information;
        this.price = price;
        this.max_number = max_number;
    }

    public int get_Id() {
        return id;
    }
        public int get_name() {
        return name;
    }
        public int get_information() {
        return information;
    }
        public int get_price() {
        return price;
    }
        public int get_max_number() {
        return max_number;
    }