package loaddata;

public class BattleEvent {
    private int id;
    private String name;
    private String information;
    private String result;

    // 기본 생성자
    public BattleEvent() {}

    // 매개변수가 있는 생성자
    public BattleEvent(int id, String name, String information, String result) {
        this.id = id;
        this.name = name;
        this.information = information;
        this.result = result;
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

    public String getResult() {
        return result;
    }
}
