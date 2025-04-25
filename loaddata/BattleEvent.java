package loaddata;

public class BattleEvent {
    private int Id;
    private String Name;
    private String Information;
    private String result;

    // 기본 생성자
    public BattleEvent() {}

    // 매개변수가 있는 생성자
    public BattleEvent(int id, String name, String information, String result) {
        this.Id = id;
        this.Name = name;
        this.Information = information;
        this.result = result;
    }

    // Getter 메서드들
    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getInformation() {
        return Information;
    }

    public String getResult() {
        return result;
    }
}