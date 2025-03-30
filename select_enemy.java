package 팀플;

public class select_enemy {
    int health; // 체력
    int max_health; // 최대체력
    int attack; // 공격력
    int armorpoint; // 방어력
    int evasion; // 회피율
    int min_speed; // 최소속도
    int max_speed; // 최대속도
    int current_speed; // 현재속도
    int action_order; // 행동순서
    Boolean alive;  // 생사여부
    Boolean issneak; // 대상의 공격 가능 여부
    String name; // 이름
    int cost; // 적 조합 생성시 필요한 코스트 소비량
    int evadereduction; // 공격시 회피수치 무시율

    // 생성자
    public select_enemy(int health, int max_health, int attack, int armorpoint, 
                        int evasion, int min_speed, int max_speed, int current_speed, 
                        int action_order, Boolean alive, Boolean issneak, String name,
                        int cost, int evadereduction) {
        this.name = name;
        this.health = health;
        this.max_health = max_health;
        this.attack = attack;
        this.armorpoint = armorpoint;
        this.evasion = evasion;
        this.min_speed = min_speed;
        this.max_speed = max_speed;
        this.current_speed = current_speed;
        this.action_order = action_order;
        this.alive = alive;
        this.issneak = issneak;
        this.cost = cost;
        this.evadereduction = evadereduction;
    }
    
	public static select_enemy[] enemy = new select_enemy[4];//select_enemy의 특성을 가진 enemy객체 배열로생성
	public static void select_enemychar() {
	enemy[0] = new select_enemy(10, 10, 5, 1, 0, 1, 10, 1, 2, true,false,"alpha",  1, 0);
	enemy[1] = new select_enemy(10, 10, 5, 1, 0, 1, 10, 1, 4, true, false,"bravo",  1, 0);
	enemy[2] = new select_enemy(10, 10, 5, 1, 0, 1, 10, 1, 6, true, false,"charie",  1, 0);
	enemy[3] = new select_enemy(10, 10, 5, 1, 0, 1, 10, 1, 8, true, false,"delta",  1, 0);
	}
	//WIP 추후에 데이터를 받아서 랜덤 생성
}