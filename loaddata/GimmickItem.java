package loaddata;

public class GimmickItem {

    private int id;
    private String name;
    private String information;
    private int price;
    private int maxNumber;

    // 기본 생성자
    public GimmickItem() {
    }

    // 매개변수가 있는 생성자
    public GimmickItem(int id, String name, String information, int price, int maxNumber) {
        this.id = id;
        this.name = name;
        this.information = information;
        this.price = price;
        this.maxNumber = maxNumber;
    }

    // Getter 메서드들
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

    public int getMaxNumber() {
        return maxNumber;
    }
}
