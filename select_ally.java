package 팀플;

public class select_ally {
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
    int evadereduction; // 공격시 회피수치 무시율

    // 생성자
    public select_ally(int health, int max_health, int attack, int armorpoint, 
                        int evasion, int min_speed, int max_speed, int current_speed, 
                        int action_order, Boolean alive, Boolean issneak, String name,
                        int evadereduction) {
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
        this.evadereduction = evadereduction;
    }
    public static select_ally[] ally = new select_ally[4];
    //배열의 형식으로 아군 편성창을 생성
    public static void select_allychar() {
        // 객체 생성
    	ally[0] = new select_ally(10, 10, 5, 1, 0, 1, 10, 1, 1, true, false,"echo", 0);
    	ally[1] = new select_ally(10, 10, 5, 1, 0, 1, 10, 1, 3, true, false,"foxtrot", 0);
    	ally[2] = new select_ally(10, 10, 5, 1, 0, 1, 10, 1, 5, true, false,"golf",0);
    	ally[3] = new select_ally(10, 10, 5, 1, 0, 1, 10, 1, 7, true, false,"hotel", 0);
        //추후에 도감 파일과 연동하여 캐릭터 리스트를 출력하며 그 중에서 4명을 선택하여 배치하는 방식으로 변경
    }
}