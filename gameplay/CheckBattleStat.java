package gameplay;

public class CheckBattleStat {
	public static void CheckHp() {
		for (int y = 0; y < 8; y++) {
			if (y < 4) {// System.out.println(select_ally.ally[y].name+"의 체력 :
						// "+select_ally.ally[y].health);
				if (SelectAlly.Ally[y].Health <= 0 && SelectAlly.Ally[y].Alive == true) {
					SelectAlly.Ally[y].Alive = false;
					System.out.println(SelectAlly.Ally[y].Name + "이 사망하였습니다.");
				}
			} // 아군의 체력을 먼저 순서대로 출력 및 사망여부 판단
			else if (y >= 4) {
				// System.out.println(select_enemy.enemy[y-4].name+"의 체력 :
				// "+select_enemy.enemy[y-4].health);
				if (SelectEnemy.Enemy[y - 4].Health <= 0 && SelectEnemy.Enemy[y - 4].Alive == true) {
					SelectEnemy.Enemy[y - 4].Alive = false;
					System.out.println(SelectEnemy.Enemy[y - 4].Name + "이 사망하였습니다.");
				} // 적군의 체력을 순서대로 출력 및 사망여부 판단
			}
		}
	}

	public static boolean CheckEnd() {
		boolean isend = false, allyend = true, enemyend = true;
		for (int i = 0; i < SelectAlly.Ally.length; i++) {
			if (SelectAlly.Ally[i].Alive) { // 살아있는 아군이 있으면 allyend를 false로 설정
				allyend = false;
				break; // 하나라도 살아있으면 즉시 종료
			}
		}
		for (int i = 0; i < SelectEnemy.Enemy.length; i++) {
			if (SelectEnemy.Enemy[i].Alive) { // 살아있는 적군이 있으면 enemyend를 false로 설정
				enemyend = false;
				break; // 하나라도 살아있으면 즉시 종료
			}
		}
		if (allyend || enemyend)
			isend = true; // 적, 아군 중에서 한쪽 진영이라도 전부 사망 시 전투종료 트리거를 true로 변환
		return isend;
	}
}
