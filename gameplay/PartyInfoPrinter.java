package gameplay;

import java.util.Random;

import gameplay.Event.StageInfo;
import gameplay.Party.AllyParty;
import gameplay.Party.EnemyParty;
import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyStatusManager;
import gameplay.Party.SetSpeedAct;
import loaddata.Stage;

public class PartyInfoPrinter {

    // 아군의 기초 정보 출력
    public void printAllyInfo(AllyParty allyParty) {
        System.out.println("=== 아군 기초 정보 ===");
        for (AllyStatusManager ally : allyParty.getParty()) {
            System.out.println("이름: " + ally.getName());
            System.out.println("속도: " + ally.getCurrentSpeed());
            System.out.println("위치: " + ally.getPosition());
            System.out.println("행동 순서: " + ally.getActionOrder());
            System.out.println("======================");
        }
    }

    // 적군의 기초 정보 출력
    public void printEnemyInfo(EnemyParty enemyParty) {
        System.out.println("=== 적군 기초 정보 ===");
        for (EnemyStatusManager enemy : enemyParty.getEnemyParty()) {
            System.out.println("이름: " + enemy.getName());
            System.out.println("속도: " + enemy.getCurrentSpeed());
            System.out.println("위치: " + enemy.getPosition());
            System.out.println("행동 순서: " + enemy.getActionOrder());
            System.out.println("======================");
        }
    }

    public static void main(String[] args) {
        // 아군 파티 초기화
        AllyParty allyParty = new AllyParty();
        
        //적 파티 생성에는 스테이지의 정보를 요구함
        StageInfo stageInfo = new StageInfo();
    	Stage randomStage = stageInfo.getRandomValidStage();
        stageInfo.printStageInfo(randomStage.getId());
        
        // 적 파티 초기화
        EnemyParty enemyParty = new EnemyParty(randomStage);
        
        SetSpeedAct.setSpeed(allyParty.getParty(), enemyParty.getEnemyParty()); // 속도 설정
        SetSpeedAct.setActionOrder(allyParty.getParty(), enemyParty.getEnemyParty()); // 행동순서
        // PartyInfoPrinter 객체 생성
        PartyInfoPrinter infoPrinter = new PartyInfoPrinter();

        // 아군 기초 정보 출력
        infoPrinter.printAllyInfo(allyParty);

        // 적군 기초 정보 출력
        infoPrinter.printEnemyInfo(enemyParty);
    }
}