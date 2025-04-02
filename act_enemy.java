package 팀플;

import java.util.Random;

public class act_enemy {

	public static void enemyattack(int attacker) {
		if (select_enemy.enemy[attacker].alive = true) {
			while (true) {
				Random random = new Random();// Random 객체로 난수 시드 생성
				int target = random.nextInt(select_ally.ally.length);// 아군배열 크기를 최대 값으로 하는 난수 생성

				if (select_ally.ally[target].issneak == true || select_ally.ally[target].alive == false)
					continue; // 대상이 공격 불가능 하거나 이미 사망 상태일 시 난수 재 생성

				System.out.println(select_enemy.enemy[attacker].name + "가 " + select_ally.ally[target].name + "를 공격합니다");
				select_ally.ally[target].health -= select_enemy.enemy[attacker].attack;
				// 대상의 체력을 공격자의 공격력 만큼 감소(추후에 각 캐릭터 별 기술 구현 후 기술 배율, 공격력, 회피 등의 스텟에 따라 공격 하도록
				// 변경)
				System.out.println(select_ally.ally[target].name + "의 남은 체력 : " + select_ally.ally[target].health);
				break; // 대상 공격 후 반복문 종료 및 행동 종료
			}
			//System.out.println("공격자가 사망 또는 행동 불가능 상태 입니다.");
		}
	}// 추후에 각 적 캐릭터의 행동 알고리즘(ex : 체력이 적은 대상 우선, 특정 스텟이 가장 높거나 낮은 대상) 선정 후 해당 알고리즘에 따라
		// 행동 하도록 전체적으로 수정 할 것.
}
