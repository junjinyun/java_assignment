package loaddata;

public class Enemy {
    private int id; // 스킬 가져올 떄 사용 할 제이슨 파일의 식별자
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
    private String spawnPosition; // 생성 위치
    private boolean isElite; // 엘리트 여부

    public Enemy() {}

    public Enemy(int id, String name, String category, int health, int maxHealth, int attack, int defense, int minSpeed, 
                 int maxSpeed, int evasion, int cost, int evadeReduction, boolean alive, boolean isAttackable, String spawnPosition, boolean isElite) {
        this.id = id; // id 초기화
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
        this.spawnPosition = spawnPosition;
        this.isElite = isElite;
    }
    
 // 적 소환시 같은 객체를 여러개 생성하면 얕은 복사 로 인해 데이터를 공유 함 -> 해당 방식을 통해 깊은 복사를 실행하여 서로 다른 인스턴스를 부여
    public Enemy(Enemy other) {
        this.id = other.id;
        this.name = other.name;
        this.category = other.category;
        this.health = other.health;
        this.maxHealth = other.maxHealth;
        this.attack = other.attack;
        this.defense = other.defense;
        this.minSpeed = other.minSpeed;
        this.maxSpeed = other.maxSpeed;
        this.evasion = other.evasion;
        this.cost = other.cost;
        this.evadeReduction = other.evadeReduction;
        this.alive = other.alive;
        this.isAttackable = other.isAttackable;
        this.mappingId = other.mappingId; // 복사 후 새로운 ID 할당 가능
        this.spawnPosition = other.spawnPosition;
        this.isElite = other.isElite;
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

    public String getSpawnPosition() {
        return spawnPosition;
    }

    public void setSpawnPosition(String spawnPosition) {
        this.spawnPosition = spawnPosition;
    }

    public boolean isElite() {
        return isElite;
    }

    public void setElite(boolean isElite) {
        this.isElite = isElite;
    }
    public void setName(String name) {
        this.name = name;
    }
}
