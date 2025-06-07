package gameplay.Battle;

import gameplay.GamePlayer;
import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyStatusManager;
import loaddata.AllySkills;

import java.util.Arrays;
import java.util.List;

public class SkillValidator {

    public static AllySkills validateAllySkill(AllySkills skill, String casterMid, String targetMid, GamePlayer gameplayer) {
        if (skill == null) {
            System.out.println("SkillValidator 오류: skill 객체가 null입니다.");
            return null;
        }

        AllyStatusManager caster = gameplayer.getAllyParty().getAllyByMappingID(casterMid);
        if (caster == null) {
            System.out.println("SkillValidator 오류: 캐스터(" + casterMid + ")가 존재하지 않습니다.");
            return null;
        }

        Object target = null;
        if (targetMid != null) {
            if (targetMid.contains("A")) {
                target = gameplayer.getAllyParty().getAllyByMappingID(targetMid);
            } else {
                target = gameplayer.getEnemyParty().getEnemyByMappingID(targetMid);
            }
            if (target == null) {
                System.out.println("SkillValidator 오류: 타겟(" + targetMid + ")이 존재하지 않습니다.");
                return null;
            }
        }

        String targetType = skill.getTarget();
        String activationZone = skill.getSkillActivationZone();
        String targetLocation = skill.getTargetLocation();
        boolean isAoE = skill.isAoE();

        // 이동 스킬은 항상 사용 가능하게 처리
        if (isMovementSkill(skill)) {
            return skill;
        }

        // [추가] 발동 위치와 대상 위치가 모두 self이면 무조건 통과
        if ("self".equals(activationZone.trim()) && "self".equals(targetLocation.trim())) {
            return skill;
        }

        // 캐스터 위치 유효성 검사 (self가 포함된 경우 무조건 통과)
        int casterPos = caster.getPosition();
        if (!activationZone.contains("self") && !isPositionValid(casterPos, activationZone)) {
            System.out.println("SkillValidator 오류: 시전자 위치가 유효하지 않습니다.");
            return null;
        }

        if (isAoE) {
            if (!hasValidAoETarget(skill, caster, gameplayer)) {
                System.out.println("SkillValidator 오류: 광역기지만 유효한 대상이 없음.");
                return null;
            }
            return skill;
        }

        // 단일 대상 스킬일 경우
        boolean isValidType = false;

        if (target instanceof AllyStatusManager allyTarget) {
            switch (targetType) {
                case "자신" -> isValidType = caster.equals(allyTarget);
                case "아군" -> isValidType = !caster.equals(allyTarget);
                case "적군" -> isValidType = false;
            }
        } else if (target instanceof EnemyStatusManager enemyTarget) {
            isValidType = "적군".equals(targetType);
        } else {
            isValidType = false;
        }

        if (!isValidType) {
            System.out.println("SkillValidator 오류: 잘못된 대상 유형입니다.");
            return null;
        }

        // 대상이 자신이면 무조건 허용
        if ("자신".equals(targetType)) {
            return skill;
        }

        int targetPos;
        if (target instanceof AllyStatusManager allyTarget) {
            targetPos = allyTarget.getPosition();
        } else if (target instanceof EnemyStatusManager enemyTarget) {
            targetPos = enemyTarget.getPosition();
        } else {
            System.out.println("SkillValidator 오류: 타겟 위치를 확인할 수 없습니다.");
            return null;
        }

        // self가 포함되어 있으면 무조건 유효
        if (!targetLocation.contains("self") && !isPositionValid(targetPos, targetLocation)) {
            System.out.println("SkillValidator 오류: 타겟 위치가 유효하지 않습니다.");
            return null;
        }

        return skill;
    }

    private static boolean isPositionValid(int position, String validPositions) {
        if (validPositions == null) return false;
        List<String> allowed = Arrays.asList(validPositions.trim().split("\\s+"));
        return allowed.contains(String.valueOf(position));
    }

    private static boolean hasValidAoETarget(AllySkills skill, AllyStatusManager caster, GamePlayer gamePlayer) {
        String targetType = skill.getTarget();
        String targetLocation = skill.getTargetLocation();

        if ("아군".equals(targetType)) {
            for (AllyStatusManager ally : gamePlayer.getAllyParty().getParty()) {
                if (ally != null && ally.getBaseStats().isAttackable()) {
                    if (!ally.equals(caster) && isPositionValid(ally.getPosition(), targetLocation)) return true;
                    if (targetLocation.contains("self")) return true;
                }
            }
        } else if ("적군".equals(targetType)) {
            for (EnemyStatusManager enemy : gamePlayer.getEnemyParty().getEnemyParty()) {
                if (enemy != null && enemy.isAttackable()) {
                    if (isPositionValid(enemy.getPosition(), targetLocation)) return true;
                }
            }
        } else if ("자신".equals(targetType)) {
            return caster.getBaseStats().isAttackable();
        }

        return false;
    }

    // 이동 스킬 판단 : 추가 효과 문자열에 "이동" 포함 여부 체크
    private static boolean isMovementSkill(AllySkills skill) {
        String effect = skill.getAEffect();
        if (effect == null) return false;
        return effect.contains("이동");
    }
}
