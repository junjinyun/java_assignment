package loaddata;

/* 적 JSON 정보 관리하는 클래스
*
* 1. 경로에 오류가 뜰 경우?
*   => 불러올 데이터를 우클릭 하여 경로/참조 복사 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EnemyFactory {
    private static final String JSON_PATH = "src/data/enemies.json"; // JSON 파일 경로

    public static List<Enemy> loadEnemies() {
        try (FileReader reader = new FileReader(JSON_PATH)) {
            Gson gson = new Gson();

            // JSON을 Map 형태로 읽기
            Map<String, List<Enemy>> list = gson.fromJson(reader, new TypeToken<Map<String, List<Enemy>>>(){}.getType());
            return list.get("Enemies"); // "enemies" 키에 해당하는 리스트 가져오기
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

	    // 특정 카테고리의 적 4개를 랜덤으로 (중복 가능) 골라서 반환하는 메서드
    public static List<Enemy> SpawnEnemyCategory(List<Enemy> enemies, String targetCategory) {
        // 카테고리별로 적 분류
        Map<String, List<Enemy>> categorizedEnemies = categorizeEnemies(enemies);
        
        // 특정 카테고리의 적 리스트 가져오기
        List<Enemy> targetEnemies = categorizedEnemies.get(targetCategory);

        // 카테고리가 존재하지 않으면 빈 리스트 반환
        if (targetEnemies == null) {
            System.out.println("해당 카테고리의 적이 없습니다: " + targetCategory);
            return new ArrayList<>();  // 빈 리스트 반환
        }

        List<Enemy> randomEnemies = new ArrayList<>();
        Random random = new Random();

        // 중복 가능하게 4개의 적을 랜덤으로 선택
        for (int i = 0; i < 4; i++) {
            int randomIndex = random.nextInt(targetEnemies.size());  // 랜덤 인덱스
            randomEnemies.add(targetEnemies.get(randomIndex));       // 해당 인덱스의 적을 리스트에 추가
        }

        return randomEnemies;
    }

    // category별로 적을 분류하는 메서드
    public static Map<String, List<Enemy>> categorizeEnemies(List<Enemy> enemies) {
        Map<String, List<Enemy>> categorizedEnemies = new java.util.HashMap<>();

        // 카테고리별로 적들을 분류
        for (Enemy enemy : enemies) {
            String category = enemy.getCategory();
            categorizedEnemies.putIfAbsent(category, new ArrayList<>());
            categorizedEnemies.get(category).add(enemy);
        }

        return categorizedEnemies;
    }

}
