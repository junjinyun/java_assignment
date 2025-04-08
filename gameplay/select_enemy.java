package gameplay;

import java.util.List;
import java.util.Random;

import dungeon.*;

public class select_enemy {
	String name; // 이름
	int health; // 체력
	int max_health; // 최대체력
	int attack; // 공격력
	int defense; // 방어력
	int min_speed; // 최소속도
	int max_speed; // 최대속도
	int evasion; // 회피율
	int current_speed; // 현재속도
	int action_order; // 행동순서
	int cost; // 적 조합 생성시 필요한 코스트 소비량
	int evadereduction; // 공격시 회피수치 무시율
	Boolean alive; // 생사여부
	Boolean isAttackable; // 대상의 공격 가능 여부

	// 생성자
	public select_enemy(String name, int health, int max_health, int attack, int defense, int min_speed, int max_speed,
			int evasion, int cost, int evadereduction, boolean alive, boolean isAttackable) {
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
		this.cost = cost;
		this.evadereduction = evadereduction;
		this.alive = alive;
		this.isAttackable = isAttackable;
	}

	public static select_enemy[] enemy = new select_enemy[4];// select_enemy의 특성을 가진 enemy객체 배열로생성
	public static List<Enemy> enemies = EnemyFactory.loadEnemies();// 적 도감 불러와서 enemies에 리스트로 저장

	public static void select_enemychar() {
		for (int i = 0; i < enemy.length; i++) {
			Random RandomEnenmy = new Random();
			int C_enemy = RandomEnenmy.nextInt(enemies.size());// 도감에서 랜덤한 칸의 적의 주소를 불러옴으로서 정보를 확인 하는데 사용
			enemy[i] = new select_enemy(enemies.get(C_enemy).getName(), enemies.get(C_enemy).getHealth(),
					enemies.get(C_enemy).getMaxHealth(), enemies.get(C_enemy).getAttack(),
					enemies.get(C_enemy).getDefense(), enemies.get(C_enemy).getMinSpeed(),
					enemies.get(C_enemy).getMaxSpeed(), enemies.get(C_enemy).getEvasion(),
					enemies.get(C_enemy).getCost(), enemies.get(C_enemy).getEvadereduction(),
					enemies.get(C_enemy).isAlive(), enemies.get(C_enemy).isAttackable());
		}
	}
	
	// WIP 추후에 도감과 연동하여 데이터를 받아서 코스트 값의 한도 내에서 랜덤 생성
}