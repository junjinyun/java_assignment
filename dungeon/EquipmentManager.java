package dungeon;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EquipmentManager {
    private static final String JSON_PATH = "src/data/equipment.json"; // JSON 파일 경로

    public static List<EquipmentArmor> loadEquipmentArmor() {
        try (FileReader reader = new FileReader(JSON_PATH)) {
            Gson gson = new Gson();

            // JSON을 Map 형태로 읽기
            Map<String, List<EquipmentArmor>> list = gson.fromJson(reader, new TypeToken<Map<String, List<EquipmentArmor>>>(){}.getType());

            return list.get("equipment_armor"); // "equipment_armor" 키에 해당하는 리스트 가져오기
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<EquipmentWeapon> loadEquipmentWeapon() {
        try (FileReader reader = new FileReader(JSON_PATH)) {
            Gson gson = new Gson();

            // JSON을 Map 형태로 읽기
            Map<String, List<EquipmentWeapon>> list = gson.fromJson(reader, new TypeToken<Map<String, List<EquipmentWeapon>>>(){}.getType());

            return list.get("equipment_weapon"); // "equipment_weapon" 키에 해당하는 리스트 가져오기
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        List<EquipmentArmor> armors = loadEquipmentArmor();
        if (armors != null) {
            for (EquipmentArmor a : armors) {
                System.out.println("방어구 이름: " + a.getName());
            }
        } else {
            System.out.println("방어구 데이터를 불러오지 못했습니다.");
        }

        List<EquipmentWeapon> weapons = loadEquipmentWeapon();
        if (weapons != null) {
            for (EquipmentWeapon a : weapons) {
                System.out.println("무기 이름: " + a.getName());
            }
        } else {
            System.out.println("무기 데이터를 불러오지 못했습니다.");
        }
    }
}