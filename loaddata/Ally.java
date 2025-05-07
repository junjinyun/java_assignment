package loaddata;

public class Ally {
    static int int nextID = 1;
    private String name; // 이름
    private String information; // 추가 정보
    private int health; // 현재 체력
    private int maxHealth; // 최대 체력
    private int attack; // 공격력
    private int defense; // 방어력
    private int minSpeed; // 최소 스피드
    private int maxSpeed; // 최대 스피드
    private int evasion; // 회피율
    private int evadeReduction; // 회피율 무시
    private boolean alive; // 생사 여부
    private boolean isAttackable; // 공격 가능 여부
    private String mappingId=A;//스테이터스 매니저(각 아군별 스킬, 상태이상 등의 효과를 관리)를 각각의 인스턴스로 관리하기 위한 식별코드

    public Ally() {
    }

    public Ally(String name, String information, int health, int maxHealth, int attack, int defense, int minSpeed, int maxSpeed,
                int evasion, int evadeReduction, boolean alive, boolean isAttackable) {
        this.name = name;
        this.information = information;
        this.health = health;
        this.maxHealth = maxHealth;
        this.attack = attack;
        this.defense = defense;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.evasion = evasion;
        this.evadeReduction = evadeReduction;
        this.alive = alive;
        this.isAttackable = isAttackable;

        this.mappingId = (A + nextID++);
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
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

    public int getEvadeReduction() {
        return evadeReduction;
    }
    
    public String getMappingId(){
        return mappingId;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isAttackable() {
        return isAttackable;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setAttackable(boolean isAttackable) {
        this.isAttackable = isAttackable;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setEvasion(int evasion) {
        this.evasion = evasion;
    }
}
