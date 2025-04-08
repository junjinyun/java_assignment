package dungeon;

/* 적 JSON 정보 관리하는 클래스
*
* 1. 경로에 오류가 뜰 경우?
*   => 불러올 데이터를 우클릭 하여 경로/참조 복사 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class get_ally_json {
	private static final String JSON_PATH = "src/data/ally.json"; // JSON 파일 경로

	public static List<ally> loadAlly() {
		try (FileReader reader = new FileReader(JSON_PATH)) {
			Gson gson = new Gson();

			// JSON을 Map 형태로 읽기
			Map<String, List<ally>> list = gson.fromJson(reader, new TypeToken<Map<String, List<ally>>>() {
			}.getType());

			return list.get("ally"); // "enemies" 키에 해당하는 리스트 가져오기
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	/*
	 * public static void main(String[] args) { List<ally> ally_list = loadAlly();
	 * if (ally_list != null) { for (ally ally : ally_list) {
	 * System.out.println("적 이름: " + ally.getName() + ", 체력: " + ally.getHealth());
	 * } } else { System.out.println("적 데이터를 불러오지 못했습니다."); } }
	 */// 디버깅용(불러온 모든 적의 정보 출력 및 불러오기 유무)
}
