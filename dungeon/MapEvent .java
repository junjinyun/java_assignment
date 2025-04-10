package dungeon;

public class MapEvent {
    private int Id;
    private String Name;
    private String Information;
    private String RequestedItem;
    private String EventType;

    // 기본 생성자
    public MapEvent() {}

    // 매개변수가 있는 생성자
    public MapEvent(int id, String name, String information, String requestedItem, String eventType) {
        this.Id = id;
        this.Name = name;
        this.Information = information;
        this.RequestedItem = requestedItem;
        this.EventType = eventType;
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

    public String getRequestedItem() {
        return RequestedItem;
    }

    public String getEventType() {
        return EventType;
    }
}