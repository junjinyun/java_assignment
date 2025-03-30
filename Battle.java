package 팀플;

public class Battle {

	
	public static void main(String[] args) {
		select_enemy.select_enemychar();
    	select_ally.select_allychar();
    	System.out.println(select_enemy.enemy[0].name+" "+select_enemy.enemy[1].name+" "
    	+select_enemy.enemy[2].name+" s"+select_enemy.enemy[3].name+"가 생성되었습니다.");
    	System.out.println(select_ally.ally[0].name+" "+select_ally.ally[1].name+" "
    	+select_ally.ally[2].name+" "+select_ally.ally[3].name+"가 생성되었습니다.");
    	boolean battleend=false;
    	int turn=1;
    	battle:
    	while(true) {
    		System.out.println(turn);
    		for(int i = 1; i <= 8; i++) {//행동순서 1 ~ 8를 돌리며 행동
    			for(int y = 0; y<4; y++) {
    				
    				if(select_ally.ally[y].action_order==i) {
    					act_ally.allyattack(y);
    					//check_battle_stat.CheckHp(); 체력체크 및 사망처리
    				}
    				else if(select_enemy.enemy[y].action_order==i) {
    					act_enemy.enemyattack(y);
    					//check_battle_stat.CheckHp(); 체력체크 및 사망처리
    				}
    				battleend=check_battle_stat.CheckEnd();
    				if(battleend==true) break battle;
    			}
    		}
    	turn++;
    	}
	}
}
