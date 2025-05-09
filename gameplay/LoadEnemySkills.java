package gameplay;

import java.util.List;

import gameplay.Event.StageInfo;
import gameplay.Party.EnemyParty;
import gameplay.Party.EnemyStatusManager;
import loaddata.EnemySkills;
import loaddata.SkillManager;
import loaddata.Stage;
import gameplay.Party.NameMapper;

public class LoadEnemySkills {

    public static void main(String[] args) {
        // 1. 스테이지 정보 초기화
        StageInfo stageInfo = new StageInfo();
        Stage randomStage = stageInfo.getRandomValidStage();

        // 2. 적 파티 생성
        EnemyParty enemyParty = new EnemyParty(randomStage);

        // 3. 적 정보 + 스킬 할당
        for (EnemyStatusManager enemy : enemyParty.getEnemyParty()) {
            String systemName = NameMapper.toSystemName(enemy.getName());
            List<EnemySkills> skills = SkillManager.loadUsableEnemySkillsByType(systemName, enemy.getId());

            for (EnemySkills skill : skills) {
                enemy.addSkill(skill);
            }
        }

        // 4. 적 정보 출력
        printEnemyInfoWithSkills(enemyParty);
    }

    private static void printEnemyInfoWithSkills(EnemyParty enemyParty) {
        System.out.println("=== 적군 전체 정보 ===");
        for (EnemyStatusManager enemy : enemyParty.getEnemyParty()) {
            System.out.println("이름: " + enemy.getName());
            System.out.println("속도: " + enemy.getCurrentSpeed());
            System.out.println("위치: " + enemy.getPosition());
            System.out.println("행동 순서: " + enemy.getActionOrder());

            List<EnemySkills> skills = enemy.getSkillList();
            System.out.println("보유 스킬 수: " + skills.size());

            if (skills.isEmpty()) {
                System.out.println("  → 보유한 스킬 없음");
            } else {
                for (EnemySkills skill : skills) {
                    System.out.println("  - 스킬 이름: " + skill.getName());
                    // 필요시: 추가 정보 출력
                    // System.out.println("    설명: " + skill.getDescription());
                }
            }

            System.out.println("======================");
        }
    }
}
