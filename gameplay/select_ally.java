package gameplay;

import java.util.List;
import java.util.Random;

import dungeon.ally;
import dungeon.*;

public class select_ally {
	String name; // 이름
	int health; // 체력
	int max_health; // 최대체력
	int attack; // 공격력
	int defense; // 방어력
	int min_speed; // 최소속도
	int max_speed; // 최대속도
	int current_speed; // 현재속도
	int action_order; // 행동순서
	int evasion; // 회피율
	int evadereduction; // 공격시 회피수치 무시율
	Boolean alive; // 생사여부
	Boolean isAttackable; // 대상의 공격 가능 여부

	// 생성자
	public select_ally(String name, int health, int max_health, int attack, int defense, int min_speed, int max_speed,
			int evasion, int evadereduction, boolean alive, boolean isAttackable) {
		this.name = name;
		this.health = health;
		this.max_health = max_health;
		this.attack = attack;
		this.defense = defense;
		this.min_speed = min_speed;
		this.max_speed = max_speed;
		this.evasion = evasion;
		// this.current_speed = current_speed;
		// this.action_order = action_order;
		this.evadereduction = evadereduction;
		this.alive = alive;
		this.isAttackable = isAttackable;
	}

	public static select_ally[] ally = new select_ally[4];
	public static List<ally> ally_list = get_ally_json.loadAlly();

	// 배열의 형식으로 아군 편성창을 생성
	public static void select_allychar() {
		for (int i = 0; i < ally.length; i++) {
			Random RandomEnenmy = new Random();
			int C_ally = RandomEnenmy.nextInt(ally_list.size());// 도감에서 랜덤한 칸의 적의 주소를 불러옴으로서 정보를 확인 하는데 사용
			ally[i] = new select_ally(ally_list.get(C_ally).getName(), ally_list.get(C_ally).getHealth(),
					ally_list.get(C_ally).getMaxHealth(), ally_list.get(C_ally).getAttack(),
					ally_list.get(C_ally).getDefense(), ally_list.get(C_ally).getMinSpeed(),
					ally_list.get(C_ally).getMaxSpeed(), ally_list.get(C_ally).getEvasion(),
					ally_list.get(C_ally).getEvadereduction(), ally_list.get(C_ally).isAlive(),
					ally_list.get(C_ally).isAttackable());
		}
		// 추후에 도감 파일과 연동하여 캐릭터 리스트를 출력하며 그 중에서 4명을 선택하여 배치하는 방식으로 변경}
	}
}