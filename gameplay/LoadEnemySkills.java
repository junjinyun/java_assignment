package gameplay;

import gameplay.Event.StageInfo;
import gameplay.Party.EnemyParty;
import loaddata.SkillManager;
import loaddata.Stage;
import gameplay.Party.NameMapper;
import gameplay.Party.EnemyStatusManager;

public class LoadEnemySkills {

    public static void main(String[] args) {
        // EnemyParty 객체 생성
        //적 파티 생성에는 스테이지의 정보를 요구함
        StageInfo stageInfo = new StageInfo();
    	Stage randomStage = stageInfo.getRandomValidStage();
        //stageInfo.printStageInfo(randomStage.getId());

        // 적 파티 초기화
        EnemyParty enemyParty = new EnemyParty(randomStage);

        // 각 적의 상태 관리 객체를 가져와 스킬 로드
        for (int i = 1; i <= enemyParty.getEnemyParty().size(); i++) {
            EnemyStatusManager enemyStatusManager = enemyParty.getEnemyByMappingID("E" + i); // EnemyStatusManager 객체 가져오기
            if (enemyStatusManager != null) {
                String enemyName = NameMapper.toSystemName(enemyStatusManager.getName()); // 적의 이름을 시스템 이름으로 변환
                //System.out.println("Loading skills for: " + enemyStatusManager.getName());
                // 스킬을 로드하고 해당 적의 스킬 리스트에 추가
                SkillManager.loadUsableEnemySkillsByType(enemyName, i).forEach(skill -> enemyStatusManager.addSkill(skill));
            }
        }

        // 스킬이 로드된 후, 각 적의 스킬 리스트 출력
        for (int i = 1; i <= enemyParty.getEnemyParty().size(); i++) {
            EnemyStatusManager enemyStatusManager = enemyParty.getEnemyByMappingID("E" + i); //  EnemyStatusManager 객체 가져오기
            if (enemyStatusManager != null) {
                System.out.println("Enemy: " + enemyStatusManager.getName());

                int size = enemyStatusManager.getSkillList().size();
                System.out.println("Number of skills: " + size);

                if (size == 0) {
                    System.out.println("No skills loaded for this enemy.");
                } else {
                    for (int x = 0; x < size; x++) {
                        System.out.println("Skill ID: " + x + " Skill Name: " + enemyStatusManager.getSkillList().get(x).getName());
                    }
                }
            }
        }
    }
}
