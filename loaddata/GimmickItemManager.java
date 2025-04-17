package dungeon;

/* 기믹아이템 JSON 정보 관리하는 클래스
1. 경로에 오류가 뜰 경우?
=> 불러올 데이터를 우클릭 하여 경로/참조 복사 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GimmickItemManager {
    private static final String JSON_PATH = "src/data/gimmickitem.json"; // JSON 파일 경로

    public static List<GimmickItem> loadGimmickItem() {
        try (FileReader reader = new FileReader(JSON_PATH)) {
            Gson gson = new Gson();

            // JSON을 Map 형태로 읽기
            Map<String, List<GimmickItem>> list = gson.fromJson(reader, new TypeToken<Map<String, List<GimmickItem>>>(){}.getType());

            return list.get("GimmickItem"); // "GimmickItem" 키에 해당하는 리스트 가져오기
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        List<GimmickItem> GimmickItems = loadGimmickItem();
        if (GimmickItems != null) {
            for (GimmickItem a : GimmickItems) {
                System.out.println("아이템 이름: " + a.getName() + "\n정보: " + a.getInformation());
            }
        } else {
            System.out.println("아이템 데이터를 불러오지 못했습니다.");
        }
    }
}
