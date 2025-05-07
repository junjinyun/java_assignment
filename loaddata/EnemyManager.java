package loaddata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EnemyManager {
    private static final String JSON_PATH = "src/data/enemies.json"; // JSON 파일 경로

    // 특정 카테고리의 적 4개를 랜덤으로 (중복 가능) 골라서 반환하는 메서드
    public static List<Enemy> SpawnEnemyCategory(String targetCategory) {
        // 카테고리별로 적 분류
        try (FileReader reader = new FileReader(JSON_PATH)) {
            Gson gson = new Gson();
            Map<String, List<Enemy>> categorizedEnemies = gson.fromJson(reader,
                    new TypeToken<Map<String, List<Enemy>>>() {}.getType());

            // 특정 카테고리의 적 리스트 가져오기
            List<Enemy> targetEnemies = categorizedEnemies.get(targetCategory);

            // 카테고리가 존재하지 않으면 빈 리스트 반환
            if (targetEnemies == null) {
                System.out.println("해당 카테고리의 적이 없습니다: " + targetCategory);
                return new ArrayList<>(); // 빈 리스트 반환
            }

            List<Enemy> randomEnemies = new ArrayList<>();
            Random random = new Random();
            boolean eliteSpawned = false; // 엘리트 적이 이미 선택되었는지 추적

            // 중복 가능하게 4개의 적을 랜덤으로 선택
            for (int i = 1; i < 5; i++) {
                List<Enemy> avaivableEnemy = new ArrayList<>();

                // 스폰 포지션이 현재 인덱스와 일치하는 적만 선택
                for (int y = 0; y < targetEnemies.size(); y++) {
                    // spawnPosition이 문자열로 저장되어 있음
                    String spawnPositions = targetEnemies.get(y).getSpawnPosition(); // 예: "1,2,3"
                    // 문자열을 쉼표로 분리하여 각 위치를 리스트로 변환
                    String[] positions = spawnPositions.split(",");

                    // 현재 스폰 포지션이 해당 적의 스폰 포지션 목록에 포함되어 있는지 확인
                    for (String position : positions) {
                        if (position.trim().equals(String.valueOf(i))) {
                            // 엘리트 적은 최대 1기만 존재하도록 검사
                            if (targetEnemies.get(y).isElite() && !eliteSpawned) {
                                avaivableEnemy.add(targetEnemies.get(y)); // 엘리트 적을 추가
                                eliteSpawned = true; // 엘리트 적이 선택되었음을 표시
                                break; // 엘리트 적은 1기만 추가되므로 바로 break
                            } else if (!targetEnemies.get(y).isElite()) {
                                avaivableEnemy.add(targetEnemies.get(y)); // 일반 적만 추가
                            }
                        }
                    }
                }

                // avaivableEnemy 리스트에서 랜덤으로 적을 하나 선택
                if (!avaivableEnemy.isEmpty()) {
                    int randomIndex = random.nextInt(avaivableEnemy.size()); // 랜덤 인덱스
                    randomEnemies.add(avaivableEnemy.get(randomIndex)); // 해당 인덱스의 적을 리스트에 추가
                }
            }

            // 엘리트 적군이 한 번도 선택되지 않은 경우, 일반 적군으로만 4명 채우기
            if (!eliteSpawned) {
                // 엘리트 적군이 없다면 일반 적군으로만 4명을 채운다
                List<Enemy> availableNormalEnemies = new ArrayList<>();
                for (Enemy enemy : targetEnemies) {
                    if (!enemy.isElite()) {
                        availableNormalEnemies.add(enemy);
                    }
                }

                // 4명이 될 때까지 일반 적군을 중복해서 추가
                while (randomEnemies.size() < 4) {
                    int randomIndex = random.nextInt(availableNormalEnemies.size());
                    randomEnemies.add(availableNormalEnemies.get(randomIndex));
                }
            }

            // 4명의 적을 보장하기 위해 일반 적군으로 부족한 자리를 채운다
            while (randomEnemies.size() < 4) {
                List<Enemy> availableEnemies = new ArrayList<>();
                for (Enemy enemy : targetEnemies) {
                    availableEnemies.add(enemy); // 모든 적을 추가
                }

                int randomIndex = random.nextInt(availableEnemies.size());
                randomEnemies.add(availableEnemies.get(randomIndex)); // 부족한 자리는 일반 적군으로 채우기
            }

            return randomEnemies;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
