package loaddata;

public class MapEvent {
    private int id;
    private String name;
    private String information;
    private String requestedItem;
    private String eventType;
    private String internalId;

    // 기본 생성자
    public MapEvent() {}

    // 매개변수가 있는 생성자
    public MapEvent(int id, String name, String information, String requestedItem, String eventType, String internalId) {
        this.id = id;
        this.name = name;
        this.information = information;
        this.requestedItem = requestedItem;
        this.eventType = eventType;
        this.internalId = internalId;
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

    public String getRequestedItem() {
        return requestedItem;
    }

    public String getEventType() {
        return eventType;
    }

    public String getInternalId() {
        return internalId;
    }
}
