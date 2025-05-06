package gameplay.Battle;


import gameplay.Party.AllyParty;
import gameplay.Party.EnemyParty;
import gameplay.Party.SetSpeedAct;

public class Battle {

    public static void main(String[] args) {
        // 아군/적군 초기화
    	AllyParty ap = new AllyParty(); 
        EnemyParty ep = new EnemyParty();

        // 디버깅용 출력
        System.out.println("적군 생성 완료:");
        for (int i = 0; i < ep.getEnemyParty().size(); i++) {
            System.out.println("  - " + ep.getEnemyParty().get(i).getName());
        }

        System.out.println("아군 생성 완료:");
        for (int i = 0; i < ap.getParty().size(); i++) {
            System.out.println("  - " + ap.getParty().get(i).getName());
        }

        boolean battleend = false;
        int turn = 1;

        battle:
        while (true) {
            System.out.println("\n====== 턴 " + turn + " 시작 ======");

            // 속도 및 행동 순서 설정
            SetSpeedAct.setSpeed(ap.getParty(), ep.getEnemyParty());
            SetSpeedAct.setActionOrder(ap.getParty(), ep.getEnemyParty());

            for (int i = 1; i <= 8; i++) { // 행동 순서 1~8
                for (int y = 0; y < 4; y++) {
                    // 아군 행동
                    if (ap.getParty().get(y).getActionOrder() == i && ap.getParty().get(y).getBaseStats().isAlive()) {
						ActAlly.allyattack(y, ap, ep);
                        CheckBattleStat.CheckHp(ap, ep);
                        battleend = CheckBattleStat.CheckEnd(ap, ep);
                        if (battleend) break battle;
                        break;
                    }

                    // 적군 행동
                    else if (ep.getEnemyParty().get(y).getActionOrder() == i && ep.getEnemyParty().get(y).isAlive()) {
                        ActEnemy.enemyattack(y,ap,ep);
                        CheckBattleStat.CheckHp(ap,ep);
                        battleend = CheckBattleStat.CheckEnd(ap, ep);
                        if (battleend) break battle;
                        break;
                    }
                }
            }

            turn++;
        }
    }
}