package dungeon;

/* 적 정보 클래스 */

public class Enemy {
    private int id; // 아이디
    private String name; // 이름
    private int health; // 현재_체력
    private int max_health; // 최대_체력
    private int attack; // 공격력
    private int defense; // 방어력
    private int min_speed; // 최소 스피드
    private int max_speed; // 최대 스피드
    private int evasion; // 회피율
    private int cost; // 비용
    private int evadereduction; // 회피율 무시
    private boolean alive; // 생사 여부
    private boolean issneak; // 공격 가능 여

    public Enemy() {}

    public Enemy(int id, String name, int health, int max_health, int attack, int defense, int min_speed, int max_speed, int evasion, int cost, int evadereduction, boolean alive, boolean issneak) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.max_health = max_health;
        this.attack = attack;
        this.defense = defense;
        this.min_speed = min_speed;
        this.max_speed = max_speed;
        this.evasion = evasion;
        this.cost = cost;
        this.evadereduction = evadereduction;
        this.alive = alive;
        this.issneak = issneak;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return max_health;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getMinSpeed() {
        return min_speed;
    }

    public int getMaxSpeed() {
        return max_speed;
    }


    public int getEvasion() {
        return evasion;
    }

    public int getCost() {
        return cost;
    }

    public int getEvadereduction() {
        return evadereduction;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isIssneak() {
        return issneak;
    }
}
