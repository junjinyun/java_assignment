package gameplay;

public class only_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		select_enemy.select_enemychar();// 적군 캐릭터 리스트 생성
		select_ally.select_allychar();// 아군 캐릭터 리스트 생성 (추후 게임 시작시 선택하여 생성하게 변경할 예정[추후에는 삭제])
		Set_speed_act.set_speed();
		for (int i = 0; i < 4; i++) {
			System.out.println(select_enemy.enemy[i].name + " " + select_enemy.enemy[i].max_speed);
			System.out.println(select_ally.ally[i].name + " " + select_ally.ally[i].current_speed);
		}
		// Mk_Use_Map.mkmap();
		// Mk_Use_Map.mkroom();
		// EntryPointSelector.EntryPoint();
		// Mk_Use_Map.loadmap();
	}// 각각의 코드를 실험하기위한 임시 클래스 이므로 이 클래스는 무시할것

}
