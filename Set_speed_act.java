package 팀플;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Set_speed_act {
	public static void set_speed() {
		for (int i = 0; i < 4; i++) {
			Random random = new Random();// Random 객체로 난수 시드 생성
			select_ally.ally[i].current_speed = random.nextInt(
					select_ally.ally[i].max_speed - select_ally.ally[i].min_speed) + select_ally.ally[i].min_speed;
			select_enemy.enemy[i].current_speed = random
					.nextInt(select_enemy.enemy[i].max_speed - select_enemy.enemy[i].min_speed)
					+ select_enemy.enemy[i].min_speed;
			// System.out.println(select_ally.ally[i].current_speed + " " +
			// select_enemy.enemy[i].current_speed);//디버깅 용
		}
	}

	public static void set_action_order() {
		Integer[] speedrank = new Integer[select_ally.ally.length + select_enemy.enemy.length];// 각 캐릭터의 속도를 저장하는 배열
		for (int i = 0; i < speedrank.length; i++) {
			if (i < select_ally.ally.length) {
				speedrank[i] = select_ally.ally[i].current_speed;
				select_ally.ally[i].action_order = -1;
			} else {
				speedrank[i] = select_enemy.enemy[i - select_ally.ally.length].current_speed;
				select_enemy.enemy[i].action_order = -1;
			} // 각 캐릭터의 속도를 저장 및 각 캐릭터의 행동 순서를 초기화
		}
		Arrays.sort(speedrank, Collections.reverseOrder());// 배열 정렬
		for (int i = 1; i < speedrank.length + 1; i++) {
			for (int x = 0; x < select_ally.ally.length + select_enemy.enemy.length; x++) {
				if (i < select_ally.ally.length) {
					if (speedrank[i - 1] == select_ally.ally[i].current_speed && select_ally.ally[i].action_order == -1)
						select_ally.ally[i].action_order = i;
				} else {
					if (speedrank[i - 1] == select_enemy.enemy[i].current_speed
							&& select_enemy.enemy[i].action_order == -1)
						select_enemy.enemy[i].action_order = i;
				} // 각 캐릭터의 속도를 저장 및 각 캐릭터의 행동 순서를 초기화
			}
		}

	}
}
