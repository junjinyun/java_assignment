package 팀플;

import java.util.Random;

public class act_enemy {
	
	public static void enemyattack(int attacker) 
	{
		Random random = new Random();
		int target = random.nextInt(select_ally.ally.length);
		System.out.println(select_enemy.enemy[attacker].name+"가 "+select_ally.ally[target].name+"를 공격합니다");
		select_ally.ally[target].health-=select_enemy.enemy[attacker].attack;
		System.out.println(select_ally.ally[target].name+"의 남은 체력 : "+select_ally.ally[target].health);
	}
}
