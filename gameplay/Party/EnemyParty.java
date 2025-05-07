package gameplay.Party;

import loaddata.Enemy;
import loaddata.EnemyManager;
import loaddata.Stage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            enemyType = "humanoid";  // 기본값 'humanoid'
        } else {
            enemyType = stage.getEnemyType();
        }

        // 적 리스트를 로드
        enemyList = EnemyManager.SpawnEnemyCategory(enemyType);

        if (enemyList == null || enemyList.isEmpty()) {
            System.out.println("적 리스트가 비어 있습니다. 적 생성 실패.");
            return;
        }

        int idCounter = 1; // 매핑 ID 순번 카운터
        Set<String> eliteMappingIds = new HashSet<>();  // 엘리트 적군 ID 중복을 막기 위한 Set

        // 각 적에 대해 EnemyStatusManager 객체 생성
        for (Enemy selected : enemyList) {
            // 엘리트 적군인 경우 중복 ID를 체크
            if (selected.isElite()) {
                if (eliteMappingIds.contains(selected.getMappingId())) {
                    continue;  // 이미 추가된 엘리트 적군이면 추가하지 않음
                } else {
                    eliteMappingIds.add(selected.getMappingId());
                }
            }

            // 매핑 ID가 없을 경우 자동으로 부여
            if (selected.getMappingId() == null || selected.getMappingId().isBlank()) {
                selected.setMappingId("E" + idCounter++);  // 'E'는 적군을 의미
            }

            // EnemyStatusManager 객체 생성 및 enemyParty에 추가
            EnemyStatusManager esm = new EnemyStatusManager(selected, selected.getMappingId());
            enemyParty.add(esm);
        }
    }

    // 파티에 속한 모든 적군 객체를 반환
    public List<EnemyStatusManager> getEnemyParty() {
        return enemyParty;
    }

    // 고유 값을 기반으로 적군 찾기
    public EnemyStatusManager getEnemyByMappingID(String mappingId) {
        return enemyParty.stream()
            .filter(esm -> mappingId.equalsIgnoreCase(esm.getBaseStats().getMappingId()))  // 대소문자 구분없이 비교
            .findFirst()
            .orElse(null);
    }

    // 적군 상태 출력
    public void printEnemyStatus() {
        for (EnemyStatusManager enemy : enemyParty) {
            System.out.println(enemy);
        }
    }
}
