package gameplay;

public class Battle {

	public static void main(String[] args) {
		SelectEnemy.SelectEnemyChar();// 적군 캐릭터 리스트 생성
		SelectAlly.SelectAllyChar();// 아군 캐릭터 리스트 생성 (추후 게임 시작시 선택하여 생성하게 변경할 예정[추후에는 삭제])

		System.out.println(SelectEnemy.Enemy[0].Name + " " + SelectEnemy.Enemy[1].Name + " "
				+ SelectEnemy.Enemy[2].Name + " " + SelectEnemy.Enemy[3].Name + " 이 생성되었습니다.");
		// 디버깅용
		
		System.out.println(SelectAlly.Ally[0].Name + " " + SelectAlly.Ally[1].Name + " " + SelectAlly.Ally[2].Name
				+ " " + SelectAlly.Ally[3].Name + "가 생성되었습니다.");
		// 디버깅용
		boolean battleend = false;// 전투종료 여부를 판단하기 위한 변수
		int turn = 1;// 현재 턴을 표시하기 위한 변수
		battle: // 전투 종료시 while문 바로 나가기 위한 이름 정의
		while (true) {
			System.out.println(turn + " turn");// 현재 턴 표시
			SetSpeedAct.setSpeed();// 모든 캐릭터의 속도 선정 후 행동순서 선정
			for (int i = 1; i <= 8; i++) {// 행동순서 1 ~ 8를 돌리며 행동
				for (int y = 0; y < 4; y++) {
					// 각 캐릭터가 자신의 행동 순서이면 행동
					if (SelectAlly.Ally[y].ActionOrder == i) {
						ActAlly.allyattack(y);// 아군 공격 행동
						CheckBattleStat.CheckHp(); // 체력체크 및 사망처리
					} else if (SelectEnemy.Enemy[y].ActionOrder == i) {
						ActEnemy.enemyattack(y);// 적군 공격 행동
						CheckBattleStat.CheckHp(); // 체력체크 및 사망처리
					}
					battleend = CheckBattleStat.CheckEnd();// 전투 종료여부 판별
					if (battleend == true)
						break battle;// 전투 종료시 반복문 종료
				}
			}
			turn++; // 턴 종료시 턴을 1 증가시킴
		}
			
	}
}
