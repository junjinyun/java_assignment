package gameplay.Battle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DamageCalculator {

    private static final Pattern MARKING_PATTERN = Pattern.compile("마킹공격\\+(\\d+)");

    public static int calculateDamage(int attackPower, double multiplier, int defense,
                                      String effect, int attackerSpeed, int targetSpeed, boolean isTargetMarked) {
        if (effect == null) effect = "";

        // 방어무시 처리
        int effectiveDefense = effect.contains("방어무시") ? 0 : defense;
        double defenseFactor = Math.max(1.0 - (effectiveDefense / 100.0), 0);

        double damage = attackPower * multiplier * defenseFactor;

        // 기습공격 처리
        if (effect.contains("기습공격") && attackerSpeed > targetSpeed) {
            damage *= 1.5;
        }

        // 마킹공격 처리
        if (effect.contains("마킹공격") && isTargetMarked) {
            damage += extractMarkingBonus(effect);
        }

        return Math.max((int) damage, 0);
    }

    private static int extractMarkingBonus(String effect) {
        Matcher matcher = MARKING_PATTERN.matcher(effect);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
    }

    public static int calculateHealing(int attackPower, double multiplier, int currentHP, int maxHP) {
        int healAmount = (int)(attackPower * multiplier);
        return Math.min(currentHP + healAmount, maxHP);
    }
}
