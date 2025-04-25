package gameplay;

public class Battle {

    public static void main(String[] args) {
        // 아군/적군 초기화
        AllyParty.initializeParty();
        EnemyParty.initializeEnemies();

        // 디버깅용 출력
        System.out.println("적군 생성 완료:");
        for (int i = 0; i < EnemyParty.enemyParty.length; i++) {
            System.out.println("  - " + EnemyParty.enemyParty[i].getName());
        }

        System.out.println("아군 생성 완료:");
        for (int i = 0; i < AllyParty.party.length; i++) {
            System.out.println("  - " + AllyParty.party[i].getName());
        }

        boolean battleend = false;
        int turn = 1;

        battle:
        while (true) {
            System.out.println("\n====== 턴 " + turn + " 시작 ======");

            // 속도 및 행동 순서 설정
            SetSpeedAct.setSpeed(AllyParty.party, EnemyParty.enemyParty);
            SetSpeedAct.setActionOrder(AllyParty.party, EnemyParty.enemyParty);

            for (int i = 1; i <= 8; i++) { // 행동 순서 1~8
                for (int y = 0; y < 4; y++) {
                    // 아군 행동
                    if (AllyParty.party[y].getActionOrder() == i && AllyParty.party[y].getBaseStats().getAlive()) {
                        ActAlly.allyattack(y);
                        CheckBattleStat.CheckHp();
                    }

                    // 적군 행동
                    else if (EnemyParty.enemyParty[y].getActionOrder() == i && EnemyParty.enemyParty[y].isAlive()) {
                        ActEnemy.enemyattack(y);
                        CheckBattleStat.CheckHp();
                    }

                    // 전투 종료 조건
                    battleend = CheckBattleStat.CheckEnd();
                    if (battleend) break battle;
                }
            }

            turn++;
        }
    }
}