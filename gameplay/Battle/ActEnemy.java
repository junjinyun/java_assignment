package gameplay.Battle;

import gameplay.Party.AllyParty;
import gameplay.Party.EnemyParty;
import java.util.Random;

public class ActEnemy {

    // 적이 아군을 공격하는 메서드
    public static void enemyattack(int attacker, AllyParty ap, EnemyParty ep) {
        // 공격할 적의 상태 확인
        Random random = new Random();

        // 공격할 아군을 무작위로 선택
        while (true) {
            int target = random.nextInt(ap.getParty().size());

            // 대상이 공격 불가능하거나 사망 상태일 경우
            if (!ap.getParty().get(target).getBaseStats().getisAttackable() || 
                !ap.getParty().get(target).getBaseStats().getAlive()) {
                continue; // 다시 시도
            }

            // 공격 로그
            String attackerName = ep.getEnemyParty().get(attacker).getName();
            String targetName = ap.getParty().get(target).getName();

            System.out.println(attackerName + "가 " + targetName + "를 공격합니다.");

            // 피해 계산
            int damage = ep.getEnemyParty().get(attacker).getBaseStats().getAttack();
            int originalHP = ap.getParty().get(target).getBaseStats().getHealth();
            int newHP = Math.max(0, originalHP - damage);

            ap.getParty().get(target).getBaseStats().setHealth(newHP);

            // 피해 후 상태 출력
			System.out.println(targetName + "의 남은 체력 : " + ap.getParty().get(target).getBaseStats().getHealth()
					+ "/" + ap.getParty().get(target).getBaseStats().getMaxHealth());


            break; // 공격 성공 후 반복 종료
        }
    }
}