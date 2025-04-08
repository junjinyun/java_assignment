package dungeon;

/* 소비아이템 JSON 정보 관리하는 클래스
1. 경로에 오류가 뜰 경우?
=> 불러올 데이터를 우클릭 하여 경로/참조 복사 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class equipmentManager {
    private static final String JSON_PATH = "src/data/equipment.json"; // JSON 파일 경로

    public static List<equipment_armor> load_equipment_armor() {
        try (FileReader reader = new FileReader(JSON_PATH)) {
            Gson gson = new Gson();

            // JSON을 Map 형태로 읽기
            Map<String, List<equipment_armor>> list = gson.fromJson(reader, new TypeToken<Map<String, List<equipment_armor>>>(){}.getType());

            return list.get("equipment_armor"); // "equipment_armor" 키에 해당하는 리스트 가져오기
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
     public static List<equipment_weapon> load_equipment_weapon() {
        try (FileReader reader = new FileReader(JSON_PATH)) {
            Gson gson = new Gson();

            // JSON을 Map 형태로 읽기
            Map<String, List<equipment_weapon>> list = gson.fromJson(reader, new TypeToken<Map<String, List<equipment_weapon>>>(){}.getType());

            return list.get("equipment_weapon"); // "equipment_weapon" 키에 해당하는 리스트 가져오기
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        List<equipment_armor> armors = load_equipment_armor();
        if (armors != null) {
            for (equipment_armor a : armors) {
                System.out.println("방어구 이름: " + a.getName());
            }
        } else {
            System.out.println("방어구 데이터를 불러오지 못했습니다.");
        }

        List<equipment_weapon> armors = load_equipment_weapon();
        if (weapons != null) {
            for (equipment_weapon a : weapons) {
                System.out.println("무기 이름: " + a.getName());
            }
        } else {
            System.out.println("무기 데이터를 불러오지 못했습니다.");
        }

    }
}
