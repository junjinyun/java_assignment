package gameplay;

public class CheckBattleStat {

    // 아군과 적군의 체력 확인 후 사망 처리
    public static void CheckHp() {
        for (int i = 0; i < 4; i++) {
            // 아군 체력 확인
            if (AllyParty.party[i].getBaseStats().getHealth() <= 0 &&
                AllyParty.party[i].getBaseStats().getAlive()) {

                AllyParty.party[i].getBaseStats().setAlive(false);
                AllyParty.party[i].getBaseStats().setIsAttackable(false);
                System.out.println(AllyParty.party[i].getName() + "이(가) 사망하였습니다.");
            }

            // 적군 체력 확인
            if (EnemyParty.enemyParty[i].getBaseStats().getHealth() <= 0 &&
                EnemyParty.enemyParty[i].isAlive()) {

                EnemyParty.enemyParty[i].setAlive(false);
                EnemyParty.enemyParty[i].setAttackable(false);
                System.out.println(EnemyParty.enemyParty[i].getName() + "이(가) 사망하였습니다.");
            }
        }
    }

    // 아군 또는 적군이 전멸했는지 확인
    public static boolean CheckEnd() {
        boolean allyend = true;
        boolean enemyend = true;

        // 아군 생존 여부 확인
        for (int i = 0; i < AllyParty.party.length; i++) {
            if (AllyParty.party[i].getBaseStats().getAlive()) {
                allyend = false;
                break;
            }
        }

        // 적군 생존 여부 확인
        for (int i = 0; i < EnemyParty.enemyParty.length; i++) {
            if (EnemyParty.enemyParty[i].isAlive()) {
                enemyend = false;
                break;
            }
        }

        if (allyend) {
            System.out.println("아군이 전멸했습니다. 패배...");
        } else if (enemyend) {
            System.out.println("적군이 전멸했습니다. 승리!");
        }

        return allyend || enemyend;
    }
}