package 팀플;

public class check_battle_stat {
	public static void CheckHp() {
		for(int y = 0; y<8; y++) {
			if(y<4) {System.out.println(select_ally.ally[y].name+"의 체력 : "+select_ally.ally[y].health);
				if(select_ally.ally[y].health<=0) { select_ally.ally[y].alive=false;
					System.out.println(select_ally.ally[y].name+"이 사망하였습니다.");
				}
			}// 아군의 체력을 먼저 순서대로 출력 및 사망여부 판단
			else System.out.println(select_enemy.enemy[y-4].name+"의 체력 : "+select_enemy.enemy[y-4].health);
				if(select_enemy.enemy[y-4].health<=0) { select_enemy.enemy[y-4].alive=false;
				System.out.println(select_enemy.enemy[y-4].name+"이 사망하였습니다.");
				}// 적군의 체력을 순서대로 출력 및 사망여부 판단
		}
	}

	public static boolean CheckEnd() {
		boolean isend=false,allyend = true,enemyend = true;
		for (int i = 0; i < select_ally.ally.length; i++) {
	        if (select_ally.ally[i].alive) {  // 살아있는 아군이 있으면 allyend를 false로 설정
	            allyend = false;
	            break;  // 하나라도 살아있으면 즉시 종료
	        }
	    }
		for (int i = 0; i < select_enemy.enemy.length; i++) {
	        if (select_enemy.enemy[i].alive) {  // 살아있는 적군이 있으면 enemyend를 false로 설정
	            enemyend = false;
	            break;  // 하나라도 살아있으면 즉시 종료
	        }
	    }
		if(allyend||enemyend) isend=true; //적, 아군 중에서 한쪽 진영이라도 전부 사망 시 전투종료 트리거를 true로 변환
		return isend;
	}
}
