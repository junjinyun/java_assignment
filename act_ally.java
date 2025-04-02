package 팀플;

import java.util.Scanner;

public class act_ally {
	private static Scanner scan = new Scanner(System.in);

	public static void allyattack(int attacker) {
		if (select_ally.ally[attacker].alive == true) {
			System.out.print("공격할 적의 번호를 입력하세요 (0부터 " + (select_enemy.enemy.length - 1) + "까지): ");
			int target = 0;
			while (true) {
				try {
					target = scan.nextInt(); // 정수 입력 받기
					if (target < 0 || target >= select_enemy.enemy.length) {
						System.out.println("잘못된 입력입니다. 0부터 " + (select_enemy.enemy.length - 1) + "까지의 번호를 입력하세요.");
						// 잘못된 대상 값 입력 시 재 입력
					} else if (select_enemy.enemy[target].alive == false
							|| select_enemy.enemy[target].issneak == true) {
						System.out.println("대상은 이미 사망하였거나, 유효하지 않은 대상입니다 다른 대상을 선택 하십시오.");
						// 잘못된 대상 값 입력 시 재 입력
					} else {
						break; // 유효한 입력이면 반복문 종료
					}
				} catch (Exception e) {
					System.out.println("잘못된 입력입니다. 숫자만 입력해주세요.");
					scan.nextLine(); // 잘못된 입력으로 발생한 버퍼를 처리하기 위해 nextLine() 호출
				}
			}
			System.out.println(select_ally.ally[attacker].name + "가 " + select_enemy.enemy[target].name + "를 공격합니다");
			select_enemy.enemy[target].health -= select_ally.ally[attacker].attack;
			// 대상의 체력을 공격자의 공격력 만큼 감소(추후에 각 캐릭터 별 기술 구현 후 기술 배율, 공격력, 회피 등의 스텟에 따라 공격 하도록
			// 변경)
			System.out.println(select_enemy.enemy[target].name + "의 남은 체력 : " + select_enemy.enemy[target].health);
		}
		//System.out.println("공격자가 사망 또는 행동 불가능 상태 입니다.");
	}

	public static void allyuseitem(int user) {
		// 인벤토리의 아이템 리스트를 출력하는 코드 구현(인벤토리 클래스의 데이터를 가져오는 방식)
		// 사용 할 수 있는 아이템 리스트 출력
		// 아이템 선택 및 해당 아이템의 사용 가능한 대상 리스트 출력
		// 아이템 사용 대상 선정 및 사용
	}
}
