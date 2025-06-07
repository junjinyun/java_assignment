package gameplay.Battle;

import java.util.Arrays;
import java.util.List;

import gameplay.AdditionalEffects.SpecialEffect;
import gameplay.GamePlayer;
import gameplay.Party.AllyParty;
import gameplay.Party.EnemyParty;
import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyStatusManager;
import loaddata.EnemySkills;

public class EnemySkillExecutor {

    private GamePlayer gamePlayer;
    private MovementManager movementManager;

    public EnemySkillExecutor(GamePlayer gamePlayer) {
        // System.out.println("EnemySkillExecutor 생성자 호출됨");
        this.gamePlayer = gamePlayer;
        this.movementManager = new MovementManager(gamePlayer);
    }

    public void execute() {
        // System.out.println("execute() 메서드 시작");

        String mappingId = gamePlayer.getEMappingId();
        // System.out.println("공격자 mappingId: " + mappingId);

        EnemyParty enemyParty = gamePlayer.getEnemyParty();
        // System.out.println("enemyParty 가져옴");

        AllyParty allyParty = gamePlayer.getAllyParty();
        // System.out.println("allyParty 가져옴");

        EnemyStatusManager attacker = enemyParty.getEnemyByMappingID(mappingId);
        // System.out.println("attacker 가져옴: " + attacker);

        if (attacker == null) {
            System.out.println("공격자 정보를 찾을 수 없습니다.");
            return;
        }

        EnemySkillSelector selector = new EnemySkillSelector(gamePlayer, mappingId);
        // System.out.println("EnemySkillSelector 생성 완료");

        String selected = selector.selectSkill();
        // System.out.println("선택된 스킬 문자열: " + selected);

        if (selected.startsWith("행동 불가능:")) {
            System.out.println("스킬 선택 실패: " + selected);
            
            // === 이동 로직 추가 ===
            int currentPosition = attacker.getPosition();
            int direction = (currentPosition <= 2) ? 0 : 1;

            System.out.println("스킬이 없으므로 이동 수행: 현재 위치 " + currentPosition + " → 방향 " + (direction == 0 ? "앞으로" : "뒤로"));
            movementManager.moveCharacter(mappingId, 1, direction == 0);
            
            return;
        }

        String[] parts = selected.split("\\s*:\\s*");
        // System.out.println("스킬 파싱 결과: " + Arrays.toString(parts));

        if (parts.length < 1) {
            System.out.println("잘못된 스킬 선택 결과입니다: " + selected);
            return;
        }
        
        

        int skillId = Integer.parseInt(parts[0]);
        // System.out.println("선택된 스킬 ID: " + skillId);

        String targetLocationFromSelector = (parts.length > 1) ? parts[1] : null;
        // System.out.println("선택된 대상 위치: " + targetLocationFromSelector);

        List<EnemySkills> skillList = attacker.getSkillList();
        // System.out.println("공격자의 스킬 목록 가져옴");

        EnemySkills selectedSkill = null;

        for (EnemySkills skill : skillList) {
            // System.out.println("스킬 확인 중: " + skill.getId());
            if (skill.getId() == skillId) {
                selectedSkill = skill;
                // System.out.println("선택된 스킬 찾음: " + selectedSkill);
                break;
            }
        }

        if (selectedSkill == null) {
            System.out.println("해당 ID를 가진 스킬이 없습니다. skillId: " + skillId);
            return;
        }

        String targetLocationRaw = (targetLocationFromSelector != null) 
                                   ? targetLocationFromSelector 
                                   : selectedSkill.getTargetLocation();
        // System.out.println("실제 대상 위치: " + targetLocationRaw);

        boolean isTargetEnemy = selectedSkill.getTarget().equals("적군");
        // System.out.println("대상이 적군인가? " + isTargetEnemy);

        if (targetLocationRaw != null && !targetLocationRaw.isEmpty()) {
            try {
                int targetPosNum = Integer.parseInt(targetLocationRaw.trim());
                // System.out.println("대상 위치 번호로 타겟 설정 시도: " + targetPosNum);
                selectTargetByPosition(targetPosNum, isTargetEnemy);
            } catch (NumberFormatException e) {
                // System.out.println("대상 위치가 숫자가 아님: " + targetLocationRaw);
            }
        }

        int multiplierPercent = selectedSkill.getDamageMultiplier();
        double multiplier = multiplierPercent / 100.0;

        List<String> targetPositions = Arrays.asList(targetLocationRaw.split("\\s+"));
        var attackerStats = attacker.getBaseStats();

        int lastDamageDealt = 0;
        int lastHealingAmount = 0;

        if (selectedSkill.isAoE()) {
            if (isTargetEnemy) {
                for (AllyStatusManager ally : allyParty.getParty()) {
                    if (ally == null) continue;
                    if (!targetPositions.contains(String.valueOf(ally.getPosition()))) continue;

                    boolean isMarked = checkMarking(ally);

                    int damage = DamageCalculator.calculateDamage(
                        attackerStats.getAttack(),
                        multiplier,
                        ally.getBaseStats().getDefense(),
                        selectedSkill.getAEffect(),
                        attacker.getCurrentSpeed(),
                        ally.getCurrentSpeed(),
                        isMarked
                    );
                    lastDamageDealt = damage;

                    ally.getBaseStats().setHealth(Math.max(ally.getBaseStats().getHealth() - damage, 0));
                    ally.addEffects(selectedSkill.getAEffect());
                }
            } else {
                for (EnemyStatusManager enemy : enemyParty.getEnemyParty()) {
                    if (enemy == null || !targetPositions.contains(String.valueOf(enemy.getPosition()))) continue;

                    int newHealth = DamageCalculator.calculateHealing(
                        attackerStats.getAttack(),
                        multiplier,
                        enemy.getBaseStats().getHealth(),
                        enemy.getBaseStats().getMaxHealth()
                    );
                    lastHealingAmount = newHealth - enemy.getBaseStats().getHealth();

                    enemy.getBaseStats().setHealth(newHealth);
                    enemy.addEffects(selectedSkill.getAEffect());
                }
            }
        } else {
            String targetMappingId = gamePlayer.getTargetMappingId();

            if (isTargetEnemy) {
                AllyStatusManager ally = allyParty.getAllyByMappingID(targetMappingId);
                if (ally != null && targetPositions.contains(String.valueOf(ally.getPosition()))) {
                    boolean isMarked = checkMarking(ally);

                    int damage = DamageCalculator.calculateDamage(
                        attackerStats.getAttack(),
                        multiplier,
                        ally.getBaseStats().getDefense(),
                        selectedSkill.getAEffect(),
                        attacker.getCurrentSpeed(),
                        ally.getCurrentSpeed(),
                        isMarked
                    );
                    lastDamageDealt = damage;

                    ally.getBaseStats().setHealth(Math.max(ally.getBaseStats().getHealth() - damage, 0));
                    ally.addEffects(selectedSkill.getAEffect());
                }
            } else {
                EnemyStatusManager enemy = enemyParty.getEnemyByMappingID(targetMappingId);
                if (enemy != null && targetPositions.contains(String.valueOf(enemy.getPosition()))) {
                    int newHealth = DamageCalculator.calculateHealing(
                        attackerStats.getAttack(),
                        multiplier,
                        enemy.getBaseStats().getHealth(),
                        enemy.getBaseStats().getMaxHealth()
                    );
                    lastHealingAmount = newHealth - enemy.getBaseStats().getHealth();

                    enemy.getBaseStats().setHealth(newHealth);
                    enemy.addEffects(selectedSkill.getAEffect());
                }
            }
        }

        // 최종 결과 요약 블록
        System.out.println("====[실행 결과 요약]====");

        System.out.println("사용한 스킬 이름: " + selectedSkill.getName());

        String targetMappingId = gamePlayer.getTargetMappingId();
        if (isTargetEnemy) {
            AllyStatusManager targetAlly = allyParty.getAllyByMappingID(targetMappingId);
            if (targetAlly != null) {
                System.out.println("타겟 아군 위치: " + targetAlly.getPosition());
            } else {
                System.out.println("타겟 아군을 찾을 수 없습니다.");
            }
        } else {
            EnemyStatusManager targetEnemy = enemyParty.getEnemyByMappingID(targetMappingId);
            if (targetEnemy != null) {
                System.out.println("타겟 적군 위치: " + targetEnemy.getPosition());
            } else {
                System.out.println("타겟 적군을 찾을 수 없습니다.");
            }
        }

        System.out.println("타겟 mappingId: " + targetMappingId);

        if (isTargetEnemy) {
            AllyStatusManager targetAlly = allyParty.getAllyByMappingID(targetMappingId);
            if (targetAlly != null) {
                int hp = targetAlly.getBaseStats().getHealth();
                System.out.println("타겟 아군 체력: " + hp);
                System.out.println("입힌 데미지: " + lastDamageDealt);
            } else {
                System.out.println("타겟 아군을 찾을 수 없습니다.");
            }
        } else {
            EnemyStatusManager targetEnemy = enemyParty.getEnemyByMappingID(targetMappingId);
            if (targetEnemy != null) {
                int hp = targetEnemy.getBaseStats().getHealth();
                System.out.println("타겟 적군 체력: " + hp);
                System.out.println("회복량: " + lastHealingAmount);
            } else {
                System.out.println("타겟 적군을 찾을 수 없습니다.");
            }
        }

        System.out.println("=======================");
    }

    private boolean checkMarking(AllyStatusManager ally) {
        // System.out.println("checkMarking() 호출됨");
        for (SpecialEffect effect : ally.getSpecialEffects()) {
            // System.out.println("이펙트 확인 중: " + effect.getName());
            if ("마킹".equals(effect.getName())) {
                // System.out.println("마킹 효과 발견됨");
                return true;
            }
        }
        // System.out.println("마킹 효과 없음");
        return false;
    }

    private void selectTargetByPosition(int position, boolean isTargetEnemy) {
        if (isTargetEnemy) {
            AllyParty allyParty = gamePlayer.getAllyParty();
            for (AllyStatusManager ally : allyParty.getParty()) {
                if (ally != null && ally.getPosition() == position) {
                    gamePlayer.setTargetMappingId(ally.getMappingId(), false);
                    // System.out.println("적 행동을 위한 타겟 mappingId 설정됨 (아군): " + gamePlayer.getTargetMappingId());
                    return;
                }
            }
            // System.out.println(position + " 위치에 아군이 존재하지 않습니다.");
        } else {
            EnemyParty enemyParty = gamePlayer.getEnemyParty();
            for (EnemyStatusManager enemy : enemyParty.getEnemyParty()) {
                if (enemy != null && enemy.getPosition() == position) {
                    gamePlayer.setTargetMappingId(enemy.getMappingId(), false);
                    // System.out.println("적 행동을 위한 타겟 mappingId 설정됨 (적군): " + gamePlayer.getTargetMappingId());
                    return;
                }
            }
            // System.out.println(position + " 위치에 적군이 존재하지 않습니다.");
        }
    }
}
