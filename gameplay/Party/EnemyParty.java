package gameplay.Party;

import loaddata.Enemy;
import loaddata.EnemyManager;
import loaddata.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyParty {
    private List<EnemyStatusManager> enemyParty;

    // 랜덤하게 적을 선택하여 파티 구성
    public EnemyParty(Stage stage) {
        this.enemyParty = new ArrayList<>();
        List<Enemy> enemyList = new ArrayList<>();

        // 예외 처리: 스테이지나 적 타입이 잘못된 경우 기본값 사용
        String enemyType;

        if (stage == null || stage.getEnemyType() == null || stage.getEnemyType().isEmpty()) {
            System.out.println("스테이지 정보가 없거나 잘못되었습니다. 기본 적 타입 'humanoid'를 사용합니다.");
            enemyType = "humanoid"; // 기본값
        } else {
            enemyType = stage.getEnemyType();
        }

        // 적 리스트를 로드
        enemyList = EnemyManager.SpawnEnemyCategory(enemyType);

        if (enemyList == null || enemyList.isEmpty()) {
            System.out.println("적 리스트가 비어 있습니다. 적 생성 실패.");
            return;
        }

        List<Enemy> selectedEnemy = new ArrayList<>();
        List<Enemy> spawnedEnemy = new ArrayList<>();
        Random random = new Random();

        boolean eliteGenerated = false; // 엘리트 적이 이미 생성되었는지 확인하는 변수

        for (int i = 1; i < 5; i++) { // 4명의 적을 생성
            selectedEnemy.clear();

            // enemyList에서 spawnPosition이 해당 인덱스를 포함하는 적을 찾음
            for (Enemy enemy : enemyList) {
                if (enemy.getSpawnPosition().contains(String.valueOf(i))) {
                    selectedEnemy.add(enemy);
                }
            }

            if (!selectedEnemy.isEmpty()) {
                int spawnindex = random.nextInt(selectedEnemy.size());

                Enemy original = selectedEnemy.get(spawnindex);

                // 엘리트 적을 먼저 처리
                if (!eliteGenerated && original.isElite()) {
                    eliteGenerated = true;
                } else {
                    if (original.isElite()) {
                        continue; // 이미 엘리트 생성됨 → 스킵
                    }
                }

                // 깊은 복사로 적 생성
                Enemy copied = new Enemy(original);
                spawnedEnemy.add(copied);
            }
        }

        // 4명이 아니라면, 남은 자리를 일반 적으로 채운다.
        while (spawnedEnemy.size() < 4) {
            selectedEnemy.clear();
            selectedEnemy.addAll(enemyList);

            int spawnindex = random.nextInt(selectedEnemy.size());
            Enemy original = selectedEnemy.get(spawnindex);

            if (!original.isElite()) {
                Enemy copied = new Enemy(original);
                spawnedEnemy.add(copied);
            }
        }

        // 선택된 적들만 파티에 추가
        int idCounter = 1;
        for (Enemy selected : spawnedEnemy) {
            if (selected.getMappingId() == null || selected.getMappingId().isBlank()) {
                selected.setMappingId("E" + idCounter++);
            }

            EnemyStatusManager esm = new EnemyStatusManager(selected, selected.getMappingId());
            enemyParty.add(esm);
        }
    }

    public List<EnemyStatusManager> getEnemyParty() {
        return enemyParty;
    }

    public EnemyStatusManager getEnemyByMappingID(String mappingId) {
        return enemyParty.stream()
                .filter(esm -> mappingId.equalsIgnoreCase(esm.getBaseStats().getMappingId()))
                .findFirst()
                .orElse(null);
    }

    public void printEnemyStatus() {
        for (EnemyStatusManager enemy : enemyParty) {
            System.out.println(enemy);
        }
    }
}
