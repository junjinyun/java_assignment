package gameplay.Party;

import loaddata.Enemy;
import loaddata.EnemyManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyParty {
	private List<EnemyStatusManager> enemyParty;

    // 랜덤하게 적을 선택하여 파티 구성
    public EnemyParty() {
        this(null); // 스테이지 생성 없이 테스팅 용 코드
}
    public EnemyParty(Stage stage) {
        this.enemyParty = new ArrayList<>();

        // 예외 처리: 스테이지나 적 타입이 잘못된 경우 기본값 사용
        String enemyType;

        if (stage == null || stage.getEnemyType() == null || stage.getEnemyType().isEmpty()) {
            System.out.println("스테이지 정보가 없거나 잘못되었습니다. 기본 적 타입 'humanoid'를 사용합니다.");
            enemyType = "humanoid";
        } else {
            enemyType = stage.getEnemyType();
        }

        List<Enemy> enemyList = EnemyManager.SpawnEnemyCategory(enemyType);

        if (enemyList == null || enemyList.isEmpty()) {
            System.out.println("적 리스트가 비어 있습니다. 적 생성 실패.");
            return;
        }

        for (int i = 0; i < enemyList.size(); i++) {
            Enemy selected = enemyList.get(i);  

            // mappingId를 고유 인스턴스 생성 기준으로 사용
            EnemyStatusManager esm = new EnemyStatusManager(selected, selected.getMappingId());
            enemyParty.add(esm);
        }
    }
	public List<EnemyStatusManager> getEnemyParty() {
		return enemyParty;
	}
    // 고유 값를 기반으로 적군 찾기
    public EnemyStatusManager getAllyByMappingID(int mappingId) {
        return enemyparty.stream()
            .filter(selected -> selected.mappingId() == mappingId)
            .findFirst()
            .orElse(null);
    }
}