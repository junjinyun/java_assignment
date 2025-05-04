package loaddata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EventManager {
    private static final String JSON_PATH = "src/data/mapevent.json"; // JSON 파일 경로

    // BattleEvent를 로드하는 메서드
    public static List<BattleEvent> loadBattleEvents() {
        try (FileReader reader = new FileReader(JSON_PATH)) {
            Gson gson = new Gson();

            // JSON을 Map 형태로 읽기
            Map<String, List<BattleEvent>> events = gson.fromJson(reader, new TypeToken<Map<String, List<BattleEvent>>>(){}.getType());

            return events.get("battleEvent"); // "BattleEvent" 키에 해당하는 리스트 가져오기
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // MapEvent를 로드하는 메서드
    public static List<MapEvent> loadMapEvents() {
        try (FileReader reader = new FileReader(JSON_PATH)) {
            Gson gson = new Gson();

            // JSON을 Map 형태로 읽기
            Map<String, List<MapEvent>> events = gson.fromJson(reader, new TypeToken<Map<String, List<MapEvent>>>(){}.getType());

            return events.get("mapEvent"); // "MapEvent" 키에 해당하는 리스트 가져오기
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // BattleEvent 로드
        List<BattleEvent> battleEvents = loadBattleEvents();
        if (battleEvents != null) {
            for (BattleEvent event : battleEvents) {
                System.out.println("전투 이벤트 이름: " + event.getName());
            }
        } else {
            System.out.println("전투 이벤트 데이터를 불러오지 못했습니다.");
        }

        // MapEvent 로드
        List<MapEvent> mapEvents = loadMapEvents();
        if (mapEvents != null) {
            for (MapEvent event : mapEvents) {
                System.out.println("맵 이벤트 이름: " + event.getName()+event.getInternalId());
            }
        } else {
            System.out.println("맵 이벤트 데이터를 불러오지 못했습니다.");
        }
    }
}