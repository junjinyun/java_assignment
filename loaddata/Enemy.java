package loaddata;

/* 적 정보 클래스 */

public class Enemy {
    private int id; // 아이디
    private String name; // 이름
    private String category; // 카테고리
    private int health; // 현재 체력
    private int maxHealth; // 최대 체력
    private int attack; // 공격력
    private int defense; // 방어력
    private int minSpeed; // 최소 스피드
    private int maxSpeed; // 최대 스피드
    private int evasion; // 회피율
    private int cost; // 비용
    private int evadeReduction; // 회피율 무시
    private boolean alive; // 생사 여부
    private boolean isAttackable; // 공격 가능 여부
    private String mappingId; // 상태 관리용 식별자

    public Enemy() {}

    public Enemy(int id, String name, String category, int health, int maxHealth, int attack, int defense, int minSpeed, int maxSpeed, 
                 int evasion, int cost, int evadeReduction, boolean alive, boolean isAttackable, String mappingId) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.health = health;
        this.maxHealth = maxHealth;
        this.attack = attack;
        this.defense = defense;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.evasion = evasion;
        this.cost = cost;
        this.evadeReduction = evadeReduction;
        this.alive = alive;
        this.isAttackable = isAttackable;
        this.mappingId = mappingId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getMinSpeed() {
        return minSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getEvasion() {
        return evasion;
    }

    public int getCost() {
        return cost;
    }

    public int getEvadeReduction() {
        return evadeReduction;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isAttackable() {
        return isAttackable;
    }

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAttackable(boolean isAttackable) {
        this.isAttackable = isAttackable;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
