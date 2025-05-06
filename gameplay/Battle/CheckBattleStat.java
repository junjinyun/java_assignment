package gameplay.Battle;

import gameplay.Party.AllyParty;
import gameplay.Party.EnemyParty;

public class CheckBattleStat {

    // 아군과 적군의 체력 확인 후 사망 처리
    public static void CheckHp(AllyParty ap, EnemyParty ep) {
        // 아군 체력 확인
        for (int i = 0; i < ap.getParty().size(); i++) {
            if (ap.getParty().get(i).getBaseStats().getHealth() <= 0 &&
                ap.getParty().get(i).getBaseStats().isAlive()) {

                ap.getParty().get(i).getBaseStats().setAlive(false);
                ap.getParty().get(i).getBaseStats().setAttackable(false);
                System.out.println(ap.getParty().get(i).getName() + "이(가) 사망하였습니다.");
            }
        }

        // 적군 체력 확인
        for (int i = 0; i < ep.getEnemyParty().size(); i++) {
            if (ep.getEnemyParty().get(i).getBaseStats().getHealth() <= 0 &&
                ep.getEnemyParty().get(i).isAlive()) {

                ep.getEnemyParty().get(i).setAlive(false);
                ep.getEnemyParty().get(i).setAttackable(false);
                System.out.println(ep.getEnemyParty().get(i).getName() + "이(가) 사망하였습니다.");
            }
            
        }
    }

    // 아군 또는 적군이 전멸했는지 확인
    public static boolean CheckEnd(AllyParty ap, EnemyParty ep) {
        boolean allyend = true;
        boolean enemyend = true;

        // 아군 생존 여부 확인
        for (int i = 0; i < ap.getParty().size(); i++) {
            if (ap.getParty().get(i).getBaseStats().isAlive()) {
                allyend = false;
                break;
            }
        }

        // 적군 생존 여부 확인
        for (int i = 0; i < ep.getEnemyParty().size(); i++) {
            if (ep.getEnemyParty().get(i).isAlive()) {
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