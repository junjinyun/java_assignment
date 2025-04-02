package 팀플;

public class only_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//select_enemy.select_enemychar();// 적군 캐릭터 리스트 생성
		//select_ally.select_allychar();// 아군 캐릭터 리스트 생성 (추후 게임 시작시 선택하여 생성하게 변경할 예정[추후에는 삭제])
		//Set_speed_act.set_speed();
		Mk_Use_Map.mkmap();
		Mk_Use_Map.mkroom();
		EntryPointSelector.EntryPoint();
		Mk_Use_Map.loadmap();
	}// 각각의 코드를 실험하기위한 임시 클래스 이므로 이 클래스는 무시할것

}
