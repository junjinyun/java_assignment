package gameplay;

import gameplay.Event.StageInfo;
import gameplay.Party.AllyParty;
import gameplay.Party.EnemyParty;
import gameplay.Party.SetSpeedAct;
import loaddata.Stage;

public class GamePlayer {

    private AllyParty allyParty;

    public Stage generateStage() {
        StageInfo stageInfo = new StageInfo();
        Stage randomStage = stageInfo.getRandomValidStage();
        stageInfo.printStageInfo(randomStage.getId());
        return randomStage;
    }

    public void generateParties() {
        if (allyParty == null) {
            allyParty = new AllyParty();
        }

        Stage randomStage = generateStage();
        EnemyParty enemyParty = new EnemyParty(randomStage);

        SetSpeedAct.setSpeed(allyParty.getParty(), enemyParty.getEnemyParty());
        SetSpeedAct.setActionOrder(allyParty.getParty(), enemyParty.getEnemyParty());

        PartyInfoPrinter infoPrinter = new PartyInfoPrinter();
        infoPrinter.printAllyInfo(allyParty);
        infoPrinter.printEnemyInfo(enemyParty);
    }

    public void startGame() {
        System.out.println("게임이 시작되었습니다.");
        generateParties();
    }
}
