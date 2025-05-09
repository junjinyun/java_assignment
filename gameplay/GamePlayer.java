package gameplay;

import java.util.ArrayList;

import javax.swing.JRadioButton;

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
    private EnemyParty enemyParty;
    private Stage randomStage;
/*
    // updateSkillButtonIcons() 메서드를 추가합니다.
    private void updateSkillButtonIcons() {
        if (allyParty == null || allyParty.getParty() == null) return;

        ArrayList<String> skills = new ArrayList<>();
        for (int i = 1; i <= allyParty.getParty().size(); i++) {
            AllyStatusManager statusManager = allyParty.getAllyByMappingID("A" + i);
            if (statusManager != null) {
                statusManager.getSkillList().forEach(skill -> skills.add(skill.getName()));
            }
        }

        for (int i = 0; i < 5; i++) {  // 6번 버튼은 예비용으로 제외
            if (i < skills.size()) {
                JRadioButton skillButton = (JRadioButton) skillPanel.getComponent(i);
                skillButton.setText(skills.get(i));
            } else {
                JRadioButton skillButton = (JRadioButton) skillPanel.getComponent(i);
                skillButton.setText("");
            }
        }
    }
*/
    // 스테이지 생성
    public void generateStage() {
        StageInfo stageInfo = new StageInfo();
        randomStage = stageInfo.getRandomValidStage();
        stageInfo.printStageInfo(randomStage.getId());
    }

    // 아군과 적군 파티 생성
    public void generateParties() {
        // 아군 파티가 없으면 생성
        if (allyParty == null) {
            allyParty = new AllyParty();
            for (int i = 1; i <= 4; i++) {
                AllyStatusManager statusManager = allyParty.getAllyByMappingID("A" + i);
                }
            }
            loadAllySkills();  // 아군 스킬 로드 (생성 시)
        

        // 스테이지가 없으면 생성
        if (randomStage == null) {
            generateStage();
        }

        // 적 파티 생성
        enemyParty = new EnemyParty(randomStage);
        loadEnemySkills();  // 적군 스킬 로드 (적 생성 시마다)

        // 속도 및 행동 순서 설정
        SetSpeedAct.setSpeed(allyParty.getParty(), enemyParty.getEnemyParty());
        SetSpeedAct.setActionOrder(allyParty.getParty(), enemyParty.getEnemyParty());

        // 정보 출력
        PartyInfoPrinter infoPrinter = new PartyInfoPrinter();
        infoPrinter.printAllyInfo(allyParty);
        infoPrinter.printEnemyInfo(enemyParty);
    }

    // 아군 스킬 로딩 (생성 시, 강화 시에만 로드)
    public void loadAllySkills() {
        if (allyParty == null) return;

        for (int i = 1; i <= allyParty.getParty().size(); i++) {
            AllyStatusManager statusManager = AllyParty.getAllyByMappingID("A" + i);
            if (statusManager != null) {
                String name = NameMapper.toSystemName(statusManager.getName());
                System.out.println("Loading skills for: " + name);
                SkillManager.loadAlltSkillsByKeyName(name)
                            .forEach(skill -> statusManager.loadSkills(skill));
            }
        }
    }

    // 적군 스킬 로딩 (적 생성 시마다 수시로 호출)
    public void loadEnemySkills() {
        if (enemyParty == null) {
            System.out.println("Enemy party is not initialized.");
            return;
        }

        // 적군 파티에서 각 캐릭터에 대해 스킬을 로드
        for (int i = 1; i <= enemyParty.getEnemyParty().size(); i++) {
            EnemyStatusManager statusManager = enemyParty.getEnemyByMappingID("E" + i);
            if (statusManager != null) {
                // 적군 캐릭터의 이름을 시스템 이름으로 변환
                String name = NameMapper.toSystemName(statusManager.getName());
                System.out.println("Loading skills for (ID: E" + i + "): " + name);

                // 스킬 로드: 적군의 매핑 아이디와 ID를 이용하여 스킬 부여
                SkillManager.loadUsableEnemySkillsByType(name, enemyParty.getEnemyParty().get(i - 1).getId())
                            .forEach(skill -> statusManager.addSkill(skill));  // 해당 적군 캐릭터에 스킬 부여
            }
        }
    }

    // 아군 스킬 출력 (소지자와 스킬 이름만)
    public void printAllySkills() {
        if (allyParty == null) return;

        for (int i = 1; i <= allyParty.getParty().size(); i++) {
            AllyStatusManager statusManager = allyParty.getAllyByMappingID("A" + i);
            if (statusManager != null) {
                System.out.println("Ally: " + statusManager.getName());
                statusManager.getSkillList().forEach(skill -> {
                    System.out.println("    Skill Name: " + skill.getName());
                });
            }
        }
    }

    // 적군 스킬 출력 (소지자와 스킬 이름만) - 중복 이름도 각각 구별하여 출력
    public void printEnemySkills() {
        if (enemyParty == null) return;

        for (int i = 1; i <= enemyParty.getEnemyParty().size(); i++) {
            EnemyStatusManager statusManager = enemyParty.getEnemyByMappingID("E" + i);
            if (statusManager != null) {
                // 적군의 ID를 출력하여 중복되는 캐릭터도 구별 가능하게 함
                System.out.println("Enemy (ID: " + "E" + i + "): " + statusManager.getName());
                statusManager.getSkillList().forEach(skill -> {
                    System.out.println("    Skill Name: " + skill.getName());
                });
            }
        }
    }

    public AllyParty getAllyParty() {
        return allyParty;
    }
}
