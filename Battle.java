package 팀플;

public class Battle {

	
	public static void main(String[] args) {
		select_enemy.select_enemychar();// 적군 캐릭터 리스트 생성
    	select_ally.select_allychar();// 아군 캐릭터 리스트 생성 (추후 게임 시작시 선택하여 생성하게 변경할 예정[추후에는 삭제])

    	System.out.println(select_enemy.enemy[0].name+" "+select_enemy.enemy[1].name+" "
    	+select_enemy.enemy[2].name+" s"+select_enemy.enemy[3].name+"가 생성되었습니다.");
		//디버깅용
    	System.out.println(select_ally.ally[0].name+" "+select_ally.ally[1].name+" "
    	+select_ally.ally[2].name+" "+select_ally.ally[3].name+"가 생성되었습니다.");
		//디버깅용
    	boolean battleend=false;//전투종료 여부를 판단하기 위한 변수
    	int turn=1;// 현재 턴을 표시하기 위한 변수
    	battle://전투 종료시 while문 바로 나가기 위한 이름 정의
    	while(true) {
    		System.out.println(turn);//현재 턴 표시
    		for(int i = 1; i <= 8; i++) {//행동순서 1 ~ 8를 돌리며 행동
    			for(int y = 0; y<4; y++) {
    				//각 캐릭터가 자신의 행동 순서이면 행동
    				if(select_ally.ally[y].action_order==i) {
    					act_ally.allyattack(y);//아군 공격 행동
    					//check_battle_stat.CheckHp(); 체력체크 및 사망처리(디버깅 창이 너무 길어져서 임시로 각주처리)
    				}
    				else if(select_enemy.enemy[y].action_order==i) {
    					act_enemy.enemyattack(y);//적군 공격 행동
    					//check_battle_stat.CheckHp(); 체력체크 및 사망처리(디버깅 창이 너무 길어져서 임시로 각주처리)
    				}
    				battleend=check_battle_stat.CheckEnd();// 전투 종료여부 판별
    				if(battleend==true) break battle;//전투 종료시 반복문 종료
    			}
    		}
    	turn++; // 턴 종료시 턴을 1 증가시킴
    	}
	}
}
