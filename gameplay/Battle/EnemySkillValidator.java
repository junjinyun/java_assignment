package gameplay.Battle;

import gameplay.GamePlayer;
import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyStatusManager;
import loaddata.EnemySkills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnemySkillValidator {

    public static List<EnemySkills> getUsableSkills(String casterMid, GamePlayer gamePlayer) {
        // System.out.println("getUsableSkills() 호출됨 - casterMid: " + casterMid);

        EnemyStatusManager caster = gamePlayer.getEnemyParty().getEnemyByMappingID(casterMid);
        // System.out.println("caster 가져옴: " + caster);

        if (caster == null) {
            // System.out.println("caster가 null입니다. 빈 리스트 반환");
            return new ArrayList<>();
        }

        List<EnemySkills> allSkills = caster.getSkillList();
        // System.out.println("전체 스킬 수: " + allSkills.size());

        List<EnemySkills> usableSkills = new ArrayList<>();

        for (EnemySkills skill : allSkills) {
            // System.out.println("스킬 확인 중: " + skill);
            if (skill == null) {
                // System.out.println("스킬이 null입니다. continue");
                continue;
            }

            int casterPos = caster.getPosition();
            // System.out.println("caster 위치: " + casterPos);

            if (!isPositionValid(casterPos, skill.getSkillActivationZone())) {
                // System.out.println("포지션 유효하지 않음. continue");
                continue;
            }

            if (hasAttackableTarget(skill, caster, gamePlayer)) {
                // System.out.println("사용 가능한 스킬 발견: " + skill.getId());
                usableSkills.add(skill);
            } else {
                // System.out.println("대상 없음. 스킬 무시: " + skill.getId());
            }
        }

        // System.out.println("사용 가능한 스킬 수: " + usableSkills.size());
        return usableSkills;
    }

    private static boolean isPositionValid(int position, String validPositions) {
        // System.out.println("isPositionValid() 호출됨 - position: " + position + ", validPositions: " + validPositions);
        if (validPositions == null) {
            // System.out.println("validPositions가 null임");
            return false;
        }
        List<String> allowed = Arrays.asList(validPositions.trim().split("\\s+"));
        // System.out.println("허용 포지션 리스트: " + allowed);
        boolean result = allowed.contains(String.valueOf(position));
        // System.out.println("포지션 유효 여부: " + result);
        return result;
    }

    private static boolean hasAttackableTarget(EnemySkills skill, EnemyStatusManager caster, GamePlayer gamePlayer) {
        // System.out.println("hasAttackableTarget() 호출됨 - skill: " + skill.getId());

        String targetType = skill.getTarget();
        String targetLocation = skill.getTargetLocation();
        // System.out.println("targetType: " + targetType + ", targetLocation: " + targetLocation);

        if ("아군".equals(targetType)) {
            for (EnemyStatusManager ally : gamePlayer.getEnemyParty().getEnemyParty()) {
                if (ally != null) {
                    // System.out.println("아군 확인 중 - 위치: " + ally.getPosition() + ", isAttackable: " + ally.isAttackable());
                }
                if (ally != null && !ally.equals(caster) && ally.isAttackable()) {
                    if (isPositionValid(ally.getPosition(), targetLocation)) {
                        // System.out.println("대상 아군 유효함");
                        return true;
                    }
                }
            }
        } else if ("적군".equals(targetType)) {
            for (AllyStatusManager enemy : gamePlayer.getAllyParty().getParty()) {
                if (enemy != null) {
                    // System.out.println("적군 확인 중 - 위치: " + enemy.getPosition() + ", isAttackable: " + enemy.getBaseStats().isAttackable());
                }
                if (enemy != null && enemy.getBaseStats().isAttackable()) {
                    if (isPositionValid(enemy.getPosition(), targetLocation)) {
                        // System.out.println("대상 적군 유효함");
                        return true;
                    }
                }
            }
        } else if ("자신".equals(targetType)) {
            boolean result = caster.isAttackable() && isPositionValid(caster.getPosition(), targetLocation);
            // System.out.println("자신 타겟 확인 결과: " + result);
            return result;
        }

        // System.out.println("대상 없음");
        return false;
    }
}
