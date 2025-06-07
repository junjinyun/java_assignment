package gameplay.Battle;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gameplay.Party.AllyParty;
import gameplay.Party.EnemyParty;
import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyStatusManager;
import loaddata.AllySkills;
import gameplay.GamePlayer;
import gameplay.AdditionalEffects.SpecialEffect;

public class AllyActionExecutor {

    private GamePlayer gamePlayer;
    private MovementManager movementManager;

    public AllyActionExecutor(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
        this.movementManager = new MovementManager(gamePlayer);
    }

    public boolean allyAct() {
        AllyParty allyParty = gamePlayer.getAllyParty();
        EnemyParty enemyParty = gamePlayer.getEnemyParty();
        String mappingId = gamePlayer.getMappingId();
        String targetMappingId = gamePlayer.getTargetMappingId();
        int selectedSkillNum = gamePlayer.getSelectedSkillNum();

        System.out.println("실행됨.");

        AllyStatusManager attacker = allyParty.getAllyByMappingID(mappingId);
        if (attacker == null) {
            System.out.println("공격자 정보를 찾을 수 없습니다.");
            return false;
        }

        if (selectedSkillNum < 0 || selectedSkillNum >= attacker.getSkillList().size()) {
            System.out.println("선택한 스킬 번호가 유효하지 않습니다.");
            return false;
        }

        AllySkills selectedSkill = attacker.getSkillList().get(selectedSkillNum);
        AllySkills validatedSkill = SkillValidator.validateAllySkill(selectedSkill, mappingId, targetMappingId, gamePlayer);
        if (validatedSkill == null) {
            System.out.println("사용 불가능 한 스킬 입니다.");
            return false;
        }

        System.out.println("유효성 검사 완료.");

        double multiplier = Double.parseDouble(validatedSkill.getDamageMultiplier()) / 100.0;
        List<String> targetPositions = Arrays.asList(validatedSkill.getTargetLocation().split("\\s+"));
        boolean isTargetAlly = validatedSkill.getTarget().equals("아군") || validatedSkill.getTarget().equals("자신");
        boolean isSelfSkill = validatedSkill.getTarget().equals("자신");
        boolean isMove = validatedSkill.getAEffect().contains("이동");
        var attackerStats = attacker.getBaseStats();

        if(isMove){
        	if(targetMappingId.contains("E")) {
        		return false;
        	}
        	applyEffectAndMovement(validatedSkill.getAEffect(), mappingId, targetMappingId);
        }
        else if (validatedSkill.isAoE()) {
            if (isSelfSkill) {
                // AoE self 스킬 -> 무조건 시전자 자신 회복 처리
                int newHealth = DamageCalculator.calculateHealing(
                    attackerStats.getAttack(),
                    multiplier,
                    attacker.getBaseStats().getHealth(),
                    attacker.getBaseStats().getMaxHealth()
                );
                attacker.getBaseStats().setHealth(newHealth);
                System.out.println("회복 후 " + attacker.getMappingId() + " 체력: " +
                    newHealth + " / " + attacker.getBaseStats().getMaxHealth());

                applyEffectAndMovement(validatedSkill.getAEffect(), mappingId, mappingId);
            } else if (isTargetAlly) {
                for (AllyStatusManager ally : allyParty.getParty()) {
                    if (ally == null) continue;
                    if (!targetPositions.contains(String.valueOf(ally.getPosition()))) continue;

                    int newHealth = DamageCalculator.calculateHealing(
                        attackerStats.getAttack(),
                        multiplier,
                        ally.getBaseStats().getHealth(),
                        ally.getBaseStats().getMaxHealth()
                    );
                    ally.getBaseStats().setHealth(newHealth);
                    System.out.println("회복 후 " + ally.getMappingId() + " 체력: " +
                        ally.getBaseStats().getHealth() + " / " + ally.getBaseStats().getMaxHealth());

                    applyEffectAndMovement(validatedSkill.getAEffect(), mappingId, ally.getMappingId());
                }
            } else {
                for (EnemyStatusManager enemy : enemyParty.getEnemyParty()) {
                    if (enemy == null) continue;
                    if (!targetPositions.contains(String.valueOf(enemy.getPosition()))) continue;

                    boolean isMarked = checkMarking(enemy);

                    int damage = DamageCalculator.calculateDamage(
                        attackerStats.getAttack(),
                        multiplier,
                        enemy.getBaseStats().getDefense(),
                        validatedSkill.getAEffect(),
                        attacker.getCurrentSpeed(),
                        enemy.getCurrentSpeed(),
                        isMarked
                    );
                    int newHealth = Math.max(enemy.getBaseStats().getHealth() - damage, 0);
                    enemy.getBaseStats().setHealth(newHealth);
                    System.out.println("공격 후 " + enemy.getMappingId() + " 체력: " + newHealth);

                    applyEffectAndMovement(validatedSkill.getAEffect(), mappingId, enemy.getMappingId());
                }
            }
        } else {
            if (isSelfSkill) {
                // 단일 대상 self 스킬 -> 무조건 회복
                int newHealth = DamageCalculator.calculateHealing(
                    attackerStats.getAttack(),
                    multiplier,
                    attacker.getBaseStats().getHealth(),
                    attacker.getBaseStats().getMaxHealth()
                );
                attacker.getBaseStats().setHealth(newHealth);
                System.out.println("회복 후 " + attacker.getMappingId() + " 체력: " +
                    newHealth + " / " + attacker.getBaseStats().getMaxHealth());

                applyEffectAndMovement(validatedSkill.getAEffect(), mappingId, mappingId);
            } else if (isTargetAlly) {
                AllyStatusManager ally = allyParty.getAllyByMappingID(targetMappingId);
                if (ally != null && targetPositions.contains(String.valueOf(ally.getPosition()))) {
                    int newHealth = DamageCalculator.calculateHealing(
                        attackerStats.getAttack(),
                        multiplier,
                        ally.getBaseStats().getHealth(),
                        ally.getBaseStats().getMaxHealth()
                    );
                    ally.getBaseStats().setHealth(newHealth);
                    System.out.println("회복 후 " + ally.getMappingId() + " 체력: " +
                        newHealth + " / " + ally.getBaseStats().getMaxHealth());

                    applyEffectAndMovement(validatedSkill.getAEffect(), mappingId, ally.getMappingId());
                } else {
                    return false;
                }
            } else {
                EnemyStatusManager enemy = enemyParty.getEnemyByMappingID(targetMappingId);
                if (enemy != null && targetPositions.contains(String.valueOf(enemy.getPosition()))) {
                    boolean isMarked = checkMarking(enemy);

                    int damage = DamageCalculator.calculateDamage(
                        attackerStats.getAttack(),
                        multiplier,
                        enemy.getBaseStats().getDefense(),
                        validatedSkill.getAEffect(),
                        attacker.getCurrentSpeed(),
                        enemy.getCurrentSpeed(),
                        isMarked
                    );
                    int newHealth = Math.max(enemy.getBaseStats().getHealth() - damage, 0);
                    enemy.getBaseStats().setHealth(newHealth);
                    System.out.println("공격 후 " + enemy.getMappingId() + " 체력: " + newHealth);

                    applyEffectAndMovement(validatedSkill.getAEffect(), mappingId, enemy.getMappingId());
                } else {
                    return false;
                }
            }
        }

        return true;
    }

 
    private void applyEffectAndMovement(String effect, String attackerId, String targetId) {
        if (effect == null || effect.isEmpty()) return;
        System.out.print(effect);
        if (effect.trim().split("\\s+").length >= 2) {
            if (targetId.contains("A")) {
                AllyStatusManager target = gamePlayer.getAllyParty().getAllyByMappingID(targetId);
                if (target != null) target.addEffects(effect);
            } else {
                EnemyStatusManager target = gamePlayer.getEnemyParty().getEnemyByMappingID(targetId);
                if (target != null) target.addEffects(effect);
            }
        if (effect.contains("이동")) {
            AllyStatusManager ally1 = gamePlayer.getAllyParty().getAllyByMappingID(attackerId);
            AllyStatusManager ally2 = gamePlayer.getAllyParty().getAllyByMappingID(targetId);

            if (ally1 == null || ally2 == null) return; // 둘 다 아군이어야 함
            System.out.print("자신"+ally1.getPosition() +"대상 " +ally2.getPosition());
            boolean forward = ally1.getPosition() > ally2.getPosition(); // true면 전진, false면 후퇴
            int moveAmount = 1; // 최대 1칸 이동

            movementManager.moveCharacter(attackerId, moveAmount, forward);
            return;
        }
        Pattern pattern = Pattern.compile("(전진|후퇴|끌기|밀기)(\\d+)");
        Matcher matcher = pattern.matcher(effect);
        if (!matcher.matches()) return;

        String keyword = matcher.group(1);
        int amount = Integer.parseInt(matcher.group(2));

        switch (keyword) {
            case "전진":
                movementManager.moveCharacter(attackerId, amount, true);
                break;
            case "후퇴":
                movementManager.moveCharacter(attackerId, amount, false);
                break;
            case "끌기":
                movementManager.moveCharacter(targetId, amount, true);
                break;
            case "밀기":
                movementManager.moveCharacter(targetId, amount, false);
                break;
        }
        }
    }


    private boolean checkMarking(EnemyStatusManager enemy) {
        for (SpecialEffect effect : enemy.getSpecialEffects()) {
            if ("마킹".equals(effect.getName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidEffectFormat(String effect) {
        if (effect == null || effect.isEmpty()) return false;
        String[] parts = effect.trim().split("\\s+");
        return parts.length == 1 || parts.length == 2 || parts.length == 3;
    }
}
