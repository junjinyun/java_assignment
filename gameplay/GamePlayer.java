package gameplay;

import gameplay.Event.StageInfo;
import gameplay.Party.AllyParty;
import gameplay.Party.EnemyParty;
import gameplay.Party.SetSpeedAct;
import loaddata.SkillManager;
import loaddata.Stage;
import gameplay.Party.NameMapper;
import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyStatusManager;

public class GamePlayer {

    private AllyParty allyParty;
    private Stage randomStage;  // 클래스 수준에서 Stage 객체 선언

    // 랜덤 스테이지 생성 및 최신화
    public void generateStage() {
        // randomStage가 없으면 새로 생성, 있으면 최신화
        StageInfo stageInfo = new StageInfo();
        randomStage = stageInfo.getRandomValidStage();
        stageInfo.printStageInfo(randomStage.getId());
    }

    // 아군과 적 파티 생성 및 스킬 로딩
    public void generateParties() {
        if (allyParty == null) {
            allyParty = new AllyParty();

            AllyStatusManager allyStatusManager1 = allyParty.getAllyByMappingID("A" + 1);
            AllyStatusManager allyStatusManager2 = allyParty.getAllyByMappingID("A" + 2);
            AllyStatusManager allyStatusManager3 = allyParty.getAllyByMappingID("A" + 3);
            AllyStatusManager allyStatusManager4 = allyParty.getAllyByMappingID("A" + 4);
        }

        // randomStage가 null일 경우 스테이지 생성
        if (randomStage == null) {
            generateStage();  // 스테이지 생성 메서드 호출
        }

        // 이미 생성된 randomStage 사용 (또는 최신화)
        EnemyParty enemyParty = new EnemyParty(randomStage);

        // 아군 스킬 로드
        loadAllySkills();

        // 적군 스킬 로드
        loadEnemySkills();

        // 속도 및 행동 순서 설정
        SetSpeedAct.setSpeed(allyParty.getParty(), enemyParty.getEnemyParty());
        SetSpeedAct.setActionOrder(allyParty.getParty(), enemyParty.getEnemyParty());

        // 파티 정보 출력
        PartyInfoPrinter infoPrinter = new PartyInfoPrinter();
        infoPrinter.printAllyInfo(allyParty);
        infoPrinter.printEnemyInfo(enemyParty);
    }


    // 아군 스킬 로딩
    public void loadAllySkills() {
        for (int i = 1; i <= allyParty.getParty().size(); i++) {
            AllyStatusManager allyStatusManager = allyParty.getAllyByMappingID("A" + i);
            if (allyStatusManager != null) {
                String allyName = NameMapper.toSystemName(allyStatusManager.getName());
                System.out.println("Loading skills for: " + allyName);
                // 아군 스킬 로드
                SkillManager.loadAlltSkillsByKeyName(allyName).forEach(skill -> allyStatusManager.loadSkills(skill));
            }
        }
    }

    // 적군 스킬 로딩
    public void loadEnemySkills() {
        EnemyParty enemyParty = new EnemyParty(randomStage);

        for (int i = 1; i <= enemyParty.getEnemyParty().size(); i++) {
            EnemyStatusManager enemyStatusManager = enemyParty.getEnemyByMappingID("E" + i);
            if (enemyStatusManager != null) {
                String enemyName = NameMapper.toSystemName(enemyStatusManager.getName());
                System.out.println("Loading skills for: " + enemyName);
                // 적군 스킬 로드
                SkillManager.loadUsableEnemySkillsByType(enemyName, i).forEach(skill -> enemyStatusManager.addSkill(skill));
            }
        }
    }
}
