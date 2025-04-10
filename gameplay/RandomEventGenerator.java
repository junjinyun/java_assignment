package gameplay;

import java.util.List;
import java.util.Random;
import java.util.Random;

import dungeon.MapEvent;
import dungeon.EventManager;

public class RandomEventGenerator {
    private int Id;
    private String Name;
    private String Information;
    private String RequestedItem;
    private String EventType;

    // 생성자
    public MapEvent(int id, String name, String information, String requestedItem, String eventType) {
        this.Id = id;
        this.Name = name;
        this.Information = information;
        this.RequestedItem = requestedItem;
        this.EventType = eventType;
    }

    public static List<Ally> MapEvent = GetAllyJson.loadAlly();

    // 각 이벤트의 ID와 난수를 비교하여 일치하는 이벤트를 불러와 실행
    public static void EventGenerator() {
        Random EventId = new Random();
        int Key = (EventId.nextInt(MapEvent.length)+1);
        System.out.println(MapEvent[key].name);
        if(MapEvent[key].EventType == "상호작용")
    }
}