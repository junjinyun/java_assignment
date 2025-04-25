package gameplay.Party;

import loaddata.Enemy;
import loaddata.EnemyFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyParty {
	private List<EnemyStatusManager> enemyParty;

    // 랜덤하게 적을 선택하여 파티 구성
    public EnemyParty() {
    	this.enemyParty = new ArrayList<>();
        List<Enemy> enemyList = EnemyFactory.loadEnemies();
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            // 리스트에서 무작위 적 선택
            Enemy selected = enemyList.get(random.nextInt(enemyList.size()));

            // EnemyStatusManager 생성 (위치 정보 포함)
            EnemyStatusManager esm = new EnemyStatusManager(selected, i + 1);
            // 적 파티에 등록
            enemyParty.add(esm);
        }
    }
	public List<EnemyStatusManager> getEnemyParty() {
		return enemyParty;
	}

}