package gameplay;

import java.util.List;
import java.util.Random;
import java.util.Random;
import java.util.Scanner;

import dungeon.MapEvent;
import dungeon.EventManager;

public class RandomEventGenerator {
    private static Scanner scan = new Scanner(System.in);

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
        String SelectedItem, YesOrNo, EventInternalId;
        
        Random EventId = new Random();
        int Key = (EventId.nextInt(MapEvent.length)+1);
        System.out.println(MapEvent[key].name);
        if(MapEvent[key].EventType == "상호작용"){
            System.out.println(MapEvent[key].name + "\n" + MapEvent[key].Information);
            YesOrNo = scan.nextline();// YesOrNo 의 결과에 따라 이벤트 진행 또는 무시
            if(YesOrNo==yes)
                //call

        else if(MapEvent[key].EventType == "아이템 제출"){
            SelectedItem = = scan.nextline();// 제출 할 아이템 선택
            YesOrNo = scan.nextline();// yes = SelectedItem으로 받아온 아이템을 제출하고 이벤트 진행 no = 아이템을 제출하지 않고 진행

            //call
            
        }

    }
}// 각주 처리된 call 부분에 danymic을 이용한 string값과 같은 메서드를 불러오는 기능으로 이벤트 매서드(다른 클래스에 생성) 실행