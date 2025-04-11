package gameplay;

import dungeon.Enemy;
import dungeon.EnemyFactory;
import java.util.List;
import java.util.Random;

public class EnemyParty {
    public static EnemyStatusManager[] enemyParty = new EnemyStatusManager[4];

    // 랜덤하게 적을 선택하여 파티 구성
    public static void initializeEnemies() {
        List<Enemy> enemyList = EnemyFactory.loadEnemies();
        Random random = new Random();

        for (int i = 0; i < enemyParty.length; i++) {
            // 리스트에서 무작위 적 선택
            Enemy selected = enemyList.get(random.nextInt(enemyList.size()));

            // EnemyStatusManager 생성 (위치 정보 포함)
            EnemyStatusManager esm = new EnemyStatusManager(selected, i + 1);

            // 랜덤 속도 설정
            int speedRange = selected.getMaxSpeed() - selected.getMinSpeed() + 1;
            int currentSpeed = selected.getMinSpeed() + random.nextInt(speedRange);
            esm.setCurrentSpeed(currentSpeed);

            // 행동 순서 임시 설정 (나중에 SetSpeedAct 에서 재설정됨)
            esm.setActionOrder(i + 1);

            // 적 파티에 등록
            enemyParty[i] = esm;
        }
    }
}