package gameplay;

public class only_test {

    public static void main(String[] args) {
        // 아군/적군 초기화
        AllyParty.initializeParty();
        EnemyParty.initializeEnemies();

        // 속도 및 행동 순서 설정
        SetSpeedAct.setSpeed(AllyParty.party, EnemyParty.enemyParty);
        SetSpeedAct.setActionOrder(AllyParty.party, EnemyParty.enemyParty);

        // 아군 정보 출력
        System.out.println("===== 아군 스탯 출력 =====");
        for (int i = 0; i < AllyParty.party.length; i++) {
            AllyStatusManager ally = AllyParty.party[i];
            System.out.println("▶ [아군] " + ally.getName());
            System.out.println("  - 위치: " + ally.getPosition());
            System.out.println("  - 속도: " + ally.getCurrentSpeed());
            System.out.println("  - 행동순서: " + ally.getActionOrder());
            if (ally.getStatusEffects().isEmpty()) {
                System.out.println("  - 상태이상: 없음");
            } else {
                System.out.println("  - 상태이상:");
                for (StatusEffect effect : ally.getStatusEffects()) {
                    System.out.println("    * " + effect.getName() + " (위력: " + effect.getPower() + ", 남은 턴: " + effect.getDuration() + ")");
                }
            }
            System.out.println();
        }

        // 적군 정보 출력
        System.out.println("===== 적군 스탯 출력 =====");
        for (int i = 0; i < EnemyParty.enemyParty.length; i++) {
            EnemyStatusManager enemy = EnemyParty.enemyParty[i];
            System.out.println("▶ [적군] " + enemy.getName());
            System.out.println("  - 위치: " + enemy.getPosition());
            System.out.println("  - 속도: " + enemy.getCurrentSpeed());
            System.out.println("  - 행동순서: " + enemy.getActionOrder());
            if (enemy.getStatusEffects().isEmpty()) {
                System.out.println("  - 상태이상: 없음");
            } else {
                System.out.println("  - 상태이상:");
                for (StatusEffect effect : enemy.getStatusEffects()) {
                    System.out.println("    * " + effect.getName() + " (위력: " + effect.getPower() + ", 남은 턴: " + effect.getDuration() + ")");
                }
            }
            System.out.println();
        }
        RandomEventGenerator.EventGenerator();
    }
}