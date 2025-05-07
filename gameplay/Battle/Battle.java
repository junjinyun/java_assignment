package gameplay.Battle;


import java.util.Random;

import gameplay.Event.StageInfo;
import gameplay.Party.AllyParty;
import gameplay.Party.EnemyParty;
import gameplay.Party.SetSpeedAct;
import loaddata.Stage;

public class Battle {

    public static void main(String[] args) {
        // 아군/적군 초기화
    	AllyParty ap = new AllyParty(); 
    	
        //적 파티 생성에는 스테이지의 정보를 요구함
        StageInfo stageInfo = new StageInfo();
    	Stage randomStage = stageInfo.getRandomValidStage();
        stageInfo.printStageInfo(randomStage.getId());

        // 적 파티 초기화
        EnemyParty ep = new EnemyParty(randomStage);

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