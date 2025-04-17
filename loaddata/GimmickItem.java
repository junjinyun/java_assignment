package dungeon;

public class GimmickItem {

    private String Name;
    private String Information;
    private int Price;
    private int MaxNumber;

    // 기본 생성자
    public GimmickItem() {
    }

    // 매개변수가 있는 생성자
    public GimmickItem(String name, String information, int price, int maxNumber) {
        this.Name = name;
        this.Information = information;
        this.Price = price;
        this.MaxNumber = maxNumber;
    }

    // Getter 메서드들
    public String getName() {
        return Name;
    }

    public String getInformation() {
        return Information;
    }

    public int getPrice() {
        return Price;
    }

    public int getMaxNumber() {
        return MaxNumber;
    }
}