package gameplay;

import java.util.Random;

public class ActEnemy {

    public static void enemyattack(int attacker) {
        EnemyStatusManager attackerEnemy = EnemyParty.enemyParty[attacker];

        if (!attackerEnemy.isAlive()) {
            System.out.println(attackerEnemy.getName() + "는 이미 사망했습니다. 행동 불가.");
            return;
        }

        Random random = new Random();

        while (true) {
            int target = random.nextInt(AllyParty.party.length);

            // 대상이 공격 불가능하거나 사망 상태일 경우
            if (!AllyParty.party[target].getBaseStats().getisAttackable() ||
                !AllyParty.party[target].getBaseStats().getAlive()) {
                continue;
            }

            // 공격 로그
            String attackerName = attackerEnemy.getName();
            String targetName = AllyParty.party[target].getName();

            System.out.println(attackerName + "가 " + targetName + "를 공격합니다.");

            int damage = attackerEnemy.getBaseStats().getAttack(); // getAttack()은 baseStats에서 가져오는게 더 명확함
            int originalHP = AllyParty.party[target].getBaseStats().getHealth();
            int newHP = Math.max(0, originalHP - damage);

            AllyParty.party[target].getBaseStats().setHealth(newHP);

            System.out.println(targetName + "의 남은 체력 : " + newHP);

            if (newHP <= 0) {
                AllyParty.party[target].getBaseStats().setAlive(false);
                AllyParty.party[target].getBaseStats().setIsAttackable(false);
                System.out.println(targetName + "가 쓰러졌습니다!");
            }

            break; // 공격 성공 후 반복 종료
        }
    }
}