package gameplay.Battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gameplay.GamePlayer;
import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyStatusManager;
import loaddata.EnemySkills;

public class EnemySkillSelector {

    private final GamePlayer gamePlayer;
    private final EnemyStatusManager enemy;
    private final List<AllyStatusManager> allies;
    private final List<EnemyStatusManager> enemies;
    private final List<EnemySkills> skills;
    private final Random random = new Random();

    public EnemySkillSelector(GamePlayer gamePlayer, String enemyMappingId) {
        // System.out.println("EnemySkillSelector 생성자 호출됨");
        this.gamePlayer = gamePlayer;
        // System.out.println("gamePlayer 설정됨");
        this.enemy = gamePlayer.getEnemyParty().getEnemyByMappingID(enemyMappingId);
        // System.out.println("enemy 설정됨: " + enemyMappingId);
        this.allies = gamePlayer.getAllyParty().getParty();
        // System.out.println("allies 리스트 획득");
        this.enemies = gamePlayer.getEnemyParty().getEnemyParty();
        // System.out.println("enemies 리스트 획득");
        this.skills = EnemySkillValidator.getUsableSkills(enemyMappingId, gamePlayer);
        // System.out.println("usableSkills 획득됨: " + skills.size());
    }

    public List<EnemySkills> getUsableSkills() {
        // System.out.println("getUsableSkills() 호출됨");
        return skills;
    }

    public String selectSkill() {
        // System.out.println("selectSkill() 호출됨");
        if (skills.isEmpty()) {
            // System.out.println("skills가 비어있음");
            return "행동 불가능: 현재 위치에서 사용 가능한 스킬이 없습니다.";
        }

        String algorithm = enemy.getBaseStats().getAlgorithm();
        // System.out.println("algorithm: " + algorithm);
        if (algorithm == null || algorithm.isEmpty()) {
            // System.out.println("algorithm이 없음. 무작위 선택으로 진행");
            String result = randomSkillString();
            if (result == null) {
                return "행동 불가능: 무작위 스킬 대상이 유효하지 않습니다.";
            }
            return result;
        }

        String[] rules = algorithm.split(",");
        for (String ruleRaw : rules) {
            String rule = ruleRaw.trim();
            // System.out.println("알고리즘 룰 평가중: " + rule);

            if (rule.startsWith("if")) {
                if (checkCondition(rule)) {
                    String type = extractType(rule);
                    // System.out.println("조건 만족. type: " + type);
                    String result = selectByType(type);
                    if (result != null) {
                        // System.out.println("스킬 선택됨: " + result);
                        return result;
                    }
                }
            } else {
                String result = selectByType(rule);
                if (result != null) {
                    // System.out.println("스킬 선택됨: " + result);
                    return result;
                }
            }
        }

        // System.out.println("알고리즘 상 스킬 있음, 대상 없음");
        return "행동 불가능: 사용 가능한 스킬 중 유효한 대상이 없습니다.";
    }

    private boolean checkCondition(String condition) {
        // System.out.println("checkCondition() 호출됨. condition: " + condition);
        condition = condition.trim();

        if (condition.startsWith("if(") && condition.contains("target.hp")) {
            int threshold = Integer.parseInt(condition.replaceAll(".*target.hp\\s*<\\s*(\\d+).*", "$1"));
            // System.out.println("threshold: " + threshold);
            for (AllyStatusManager target : allies) {
                int hpPercent = (int) ((target.getBaseStats().getHealth() * 100.0f) / target.getBaseStats().getMaxHealth());
                // System.out.println("타겟 hpPercent: " + hpPercent);
                if (hpPercent < threshold) {
                    // System.out.println("hpPercent 조건 만족");
                    if (canTargetSkillOnTarget(target)) {
                        // System.out.println("타겟팅 가능");
                        return true;
                    }
                }
            }
            return false;
        } else if (condition.contains("hp<")) {
            int threshold = Integer.parseInt(condition.replaceAll("[^0-9]", ""));
            // System.out.println("자신 hp threshold: " + threshold);
            int hpPercent = (int) ((enemy.getBaseStats().getHealth() * 100.0f) / enemy.getBaseStats().getMaxHealth());
            // System.out.println("자신 hpPercent: " + hpPercent);
            return hpPercent < threshold;
        }
        return false;
    }

    private String extractType(String condition) {
        // System.out.println("extractType() 호출됨: " + condition);
        int idx = condition.indexOf(")");
        if (idx != -1 && idx + 1 < condition.length()) {
            String result = condition.substring(idx + 1).trim();
            // System.out.println("추출된 타입: " + result);
            return result;
        }
        return "공격";
    }

    private String selectByType(String type) {
        // System.out.println("selectByType() 호출됨: " + type);
        switch (type) {
            case "공격":
                return selectAttackSkill();
            case "마무리":
                return selectFinisherSkill();
            case "디버프":
                return selectDebuffSkill();
            case "버프":
                return selectBuffSkill();
            case "상태이상":
                return selectStatusEffectSkill();
            default:
                // System.out.println("알 수 없는 타입: " + type);
                return null;
        }
    }

    private String selectAttackSkill() {
        // System.out.println("selectAttackSkill() 호출됨");
        EnemySkills bestSkill = null;
        int bestPos = -1;
        int maxDamage = -1;

        for (EnemySkills skill : skills) {
            // System.out.println("스킬 평가중: " + skill.getId());
            if (!("공격".equals(skill.getType()) || "디버프".equals(skill.getType()))) continue;

            for (AllyStatusManager target : allies) {
                if (!canTarget(skill, target)) continue;

                int estimated = estimateDamage(skill, target);
                // System.out.println("예상 데미지: " + estimated);
                if (estimated > maxDamage) {
                    maxDamage = estimated;
                    bestSkill = skill;
                    bestPos = target.getPosition();
                    // System.out.println("최고 데미지 갱신: " + maxDamage + " at " + bestPos);
                }
            }
        }

        if (bestSkill == null) return null;
        return bestSkill.getId() + ":" + bestPos;
    }

    private String selectFinisherSkill() {
        // System.out.println("selectFinisherSkill() 호출됨");
        AllyStatusManager lowestHpTarget = null;
        int lowestHp = Integer.MAX_VALUE;

        for (AllyStatusManager target : allies) {
            int hp = target.getBaseStats().getHealth();
            if (hp > 0 && hp < lowestHp) {
                lowestHp = hp;
                lowestHpTarget = target;
                // System.out.println("가장 낮은 hp 타겟 발견: " + hp);
            }
        }

        if (lowestHpTarget == null) return null;

        EnemySkills bestSkill = null;
        int maxDamage = -1;

        for (EnemySkills skill : skills) {
            if (!"공격".equals(skill.getType())) continue;
            if (!canTarget(skill, lowestHpTarget)) continue;

            int estimated = estimateDamage(skill, lowestHpTarget);
            if (estimated > maxDamage) {
                maxDamage = estimated;
                bestSkill = skill;
                // System.out.println("최고 마무리 데미지 갱신: " + maxDamage);
            }
        }

        if (bestSkill == null) return null;
        return bestSkill.getId() + ":" + lowestHpTarget.getPosition();
    }

    private String selectDebuffSkill() {
        // System.out.println("selectDebuffSkill() 호출됨");
        List<EnemySkills> debuffSkills = new ArrayList<>();
        for (EnemySkills skill : skills) {
            if ("디버프".equals(skill.getType())) {
                debuffSkills.add(skill);
                // System.out.println("디버프 스킬 추가됨: " + skill.getId());
            }
        }

        if (debuffSkills.isEmpty()) return null;
        EnemySkills chosenSkill = debuffSkills.get(random.nextInt(debuffSkills.size()));
        // System.out.println("선택된 디버프 스킬: " + chosenSkill.getId());

        AllyStatusManager highestAttackTarget = null;
        int maxAttack = -1;
        for (AllyStatusManager target : allies) {
            if (!canTarget(chosenSkill, target)) continue;
            int attack = target.getBaseStats().getAttack();
            if (attack > maxAttack) {
                maxAttack = attack;
                highestAttackTarget = target;
                // System.out.println("최고 공격력 타겟 발견: " + attack);
            }
        }

        if (highestAttackTarget == null) return null;
        return chosenSkill.getId() + ":" + highestAttackTarget.getPosition();
    }

    private String selectBuffSkill() {
        // System.out.println("selectBuffSkill() 호출됨");
        List<EnemySkills> buffSkills = new ArrayList<>();
        for (EnemySkills skill : skills) {
            if ("버프".equals(skill.getType())) {
                buffSkills.add(skill);
                // System.out.println("버프 스킬 추가됨: " + skill.getId());
            }
        }

        if (buffSkills.isEmpty()) return null;
        EnemySkills chosenSkill = buffSkills.get(random.nextInt(buffSkills.size()));
        // System.out.println("선택된 버프 스킬: " + chosenSkill.getId());

        List<EnemyStatusManager> candidates = new ArrayList<>();
        for (EnemyStatusManager e : enemies) {
            if (!e.getMappingId().equals(enemy.getMappingId()) && e.getBaseStats().getHealth() > 0) {
                candidates.add(e);
                // System.out.println("버프 대상 추가: " + e.getMappingId());
            }
        }

        if (candidates.isEmpty()) return null;
        EnemyStatusManager chosenTarget = candidates.get(random.nextInt(candidates.size()));
        // System.out.println("버프 타겟 선택됨: " + chosenTarget.getMappingId());

        return chosenSkill.getId() + ":" + chosenTarget.getPosition();
    }

    private String selectStatusEffectSkill() {
        // System.out.println("selectStatusEffectSkill() 호출됨");
        List<EnemySkills> statusSkills = new ArrayList<>();
        for (EnemySkills skill : skills) {
            if ("상태이상".equals(skill.getType())) {
                statusSkills.add(skill);
                // System.out.println("상태이상 스킬 추가됨: " + skill.getId());
            }
        }

        if (statusSkills.isEmpty()) return null;
        EnemySkills chosenSkill = statusSkills.get(random.nextInt(statusSkills.size()));
        // System.out.println("선택된 상태이상 스킬: " + chosenSkill.getId());

        AllyStatusManager bestTarget = null;
        int maxPowerSum = -1;
        for (AllyStatusManager target : allies) {
            if (!canTarget(chosenSkill, target)) continue;

            int powerSum = 0;
            for (var effect : target.getStatusEffects()) {
                powerSum += effect.getPower();
            }

            if (powerSum > maxPowerSum) {
                maxPowerSum = powerSum;
                bestTarget = target;
                // System.out.println("최고 상태이상 파워 타겟 갱신: " + powerSum);
            }
        }

        if (bestTarget == null) return null;
        return chosenSkill.getId() + ":" + bestTarget.getPosition();
    }

    private String randomSkillString() {
        // System.out.println("randomSkillString() 호출됨");
        EnemySkills skill = skills.get(random.nextInt(skills.size()));
        // System.out.println("무작위 스킬 선택됨: " + skill.getId());
        List<Integer> candidatePositions = new ArrayList<>();

        if ("적".equals(skill.getTarget()) || "적군".equals(skill.getTarget())) {
            for (EnemyStatusManager e : enemies) {
                if (canTarget(skill, e)) {
                    candidatePositions.add(e.getPosition());
                    // System.out.println("적 대상 가능 위치 추가: " + e.getPosition());
                }
            }
        } else if ("아군".equals(skill.getTarget())) {
            for (AllyStatusManager a : allies) {
                if (canTarget(skill, a)) {
                    candidatePositions.add(a.getPosition());
                    // System.out.println("아군 대상 가능 위치 추가: " + a.getPosition());
                }
            }
        } else {
            candidatePositions.add(0);
            // System.out.println("기본 위치 추가: 0");
        }

        if (candidatePositions.isEmpty()) {
            candidatePositions.add(0);
            // System.out.println("후보 위치 없음. 기본값 사용: 0");
        }

        int randomPos = candidatePositions.get(random.nextInt(candidatePositions.size()));
        // System.out.println("최종 선택 위치: " + randomPos);
        return skill.getId() + ":" + randomPos;
    }

    private boolean canTarget(EnemySkills skill, AllyStatusManager target) {
        boolean result = target.getBaseStats().isAttackable() && skill.getTargetLocation().contains(String.valueOf(target.getPosition()));
        // System.out.println("canTarget(Ally): " + target.getPosition() + " -> " + result);
        return result;
    }

    private boolean canTarget(EnemySkills skill, EnemyStatusManager target) {
        boolean result = target.getBaseStats().isAttackable() && skill.getTargetLocation().contains(String.valueOf(target.getPosition()));
        // System.out.println("canTarget(Enemy): " + target.getPosition() + " -> " + result);
        return result;
    }

    private int estimateDamage(EnemySkills skill, AllyStatusManager target) {
        // System.out.println("estimateDamage() 호출됨: " + skill.getId());
        int attackPower = enemy.getBaseStats().getAttack();
        int defense = target.getBaseStats().getDefense();
        int baseMultiplier = skill.getDamageMultiplier();

        String effect = skill.getAEffect();
        if (effect != null && effect.contains("방어무시")) {
            defense = 0;
            // System.out.println("방어무시 효과 적용");
        }

        double defenseFactor = 1.0 - (defense / 100.0);
        if (defenseFactor < 0) defenseFactor = 0;

        int estimatedDamage = (int) (attackPower * baseMultiplier * defenseFactor);
        // System.out.println("예상 데미지 계산됨: " + estimatedDamage);
        return Math.max(estimatedDamage, 0);
    }

    private boolean canTargetSkillOnTarget(AllyStatusManager target) {
        for (EnemySkills skill : skills) {
            if (!"공격".equals(skill.getType())) continue;
            if (!canTarget(skill, target)) continue;
            if (target.getBaseStats().getHealth() > 0) {
                // System.out.println("공격 가능한 타겟 확인됨");
                return true;
            }
        }
        return false;
    }
}
