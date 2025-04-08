package gameplay;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Set_speed_act {
	public static void set_speed() {
		Random random = new Random(); // Random 객체는 한 번만 생성
		for (int i = 0; i < 4; i++) {
			select_ally.ally[i].current_speed = random.nextInt(
					select_ally.ally[i].max_speed + 1 - select_ally.ally[i].min_speed) + select_ally.ally[i].min_speed;
			select_enemy.enemy[i].current_speed = random
					.nextInt(select_enemy.enemy[i].max_speed - select_enemy.enemy[i].min_speed)
					+ select_enemy.enemy[i].min_speed;
		}
	}

	public static void set_action_order() {
		// select_ally와 select_enemy의 길이를 확인하여 속도 순위를 정하기 위한 배열 크기 할당
		int totalLength = select_ally.ally.length + select_enemy.enemy.length;
		Integer[] speedrank = new Integer[totalLength]; // 각 캐릭터의 속도를 저장하는 배열

		// 1. 속도값을 speedrank 배열에 할당하고, 행동 순서를 초기화
		for (int i = 0; i < select_ally.ally.length; i++) {
			speedrank[i] = select_ally.ally[i].current_speed;
			select_ally.ally[i].action_order = -1; // 초기화
		}
		for (int i = 0; i < select_enemy.enemy.length; i++) {
			speedrank[select_ally.ally.length + i] = select_enemy.enemy[i].current_speed;
			select_enemy.enemy[i].action_order = -1; // 초기화
		}

		// 2. 속도를 내림차순으로 정렬
		Arrays.sort(speedrank, Collections.reverseOrder());

		// 3. 정렬된 속도를 기반으로 각 캐릭터의 행동 순서를 할당
		int order = 1; // 순서는 1부터 시작
		for (int i = 0; i < totalLength; i++) {
			for (int j = 0; j < select_ally.ally.length; j++) {
				if (speedrank[i] == select_ally.ally[j].current_speed && select_ally.ally[j].action_order == -1) {
					select_ally.ally[j].action_order = order++;
				}
			}
			for (int j = 0; j < select_enemy.enemy.length; j++) {
				if (speedrank[i] == select_enemy.enemy[j].current_speed && select_enemy.enemy[j].action_order == -1) {
					select_enemy.enemy[j].action_order = order++;
				}
			}
		}
	}
}