package gameplay.Battle;

import gameplay.Party.AllyParty;
import gameplay.Party.EnemyParty;

import java.util.Scanner;

public class ActAlly {
	private static Scanner scan = new Scanner(System.in);

	// 아군이 적을 공격하는 메서드
	public static void allyattack(int attacker, AllyParty ap, EnemyParty ep) {

		// ap.getParty() 메서드를 통해 파티에 접근합니다.
		if (ap.getParty().get(attacker).getBaseStats().getAlive()) {
			System.out.print("공격할 적의 번호를 입력하세요 (0부터 " + (ep.getEnemyParty().size() - 1) + "까지): ");
			int target = 0;

			while (true) {
				try {
					target = scan.nextInt();

					if (target < 0 || target >= ep.getEnemyParty().size()) {
						System.out.println("잘못된 입력입니다. 0부터 " + (ep.getEnemyParty().size() - 1) + "까지의 번호를 입력하세요.");
					} else if (!ep.getEnemyParty().get(target).isAlive()
							|| !ep.getEnemyParty().get(target).isAttackable()) {
						System.out.println("대상은 이미 사망하였거나, 유효하지 않은 대상입니다. 다른 대상을 선택하십시오.");
					} else {
						break;
					}
				} catch (Exception e) {
					System.out.println("잘못된 입력입니다. 숫자만 입력해주세요.");
					scan.nextLine(); // 버퍼 비우기
				}
			}
			
			// 공격 처리
			String attackerName = ap.getParty().get(attacker).getName();
			String targetName = ep.getEnemyParty().get(target).getName();
			int attackPower = ap.getParty().get(attacker).getBaseStats().getAttack();

			int currentHealth = ep.getEnemyParty().get(target).getBaseStats().getHealth();
			int newHealth = currentHealth - attackPower;

			ep.getEnemyParty().get(target).getBaseStats().setHealth(newHealth);
			System.out.println(attackerName + "가 " + targetName + "를 공격합니다.");
			System.out.println(targetName + "의 남은 체력 : " + ep.getEnemyParty().get(target).getBaseStats().getHealth()
					+ "/" + ep.getEnemyParty().get(target).getBaseStats().getMaxHealth());

		}
	}

	// 아이템 사용 메서드 (추후 구현 예정)
	public static void allyuseitem(int user) {
		// 추후 인벤토리 또는 회복 아이템 구현 시 사용 예정
	}
}