package dungeon;

/* 환전용아이템 JSON 정보 관리하는 클래스
1. 경로에 오류가 뜰 경우?
=> 불러올 데이터를 우클릭 하여 경로/참조 복사 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CashableItemManager {
    private static final String JSON_PATH = "src/data/cashableitem.json"; // JSON 파일 경로

    public static List<CashableItem> loadCashableItem() {
        try (FileReader reader = new FileReader(JSON_PATH)) {
            Gson gson = new Gson();

            // JSON을 Map 형태로 읽기
            Map<String, List<CashableItem>> list = gson.fromJson(reader, new TypeToken<Map<String, List<CashableItem>>>(){}.getType());

            return list.get("CashableItem"); // "CashableItem" 키에 해당하는 리스트 가져오기
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        List<CashableItem> CashableItems = loadCashableItem();
        if (CashableItems != null) {
            for (CashableItem a : CashableItems) {
                System.out.println("아이템 이름: " + a.getName() + "\n정보: " + a.getInformation());
            }
        } else {
            System.out.println("아이템 데이터를 불러오지 못했습니다.");
        }
    }
}
