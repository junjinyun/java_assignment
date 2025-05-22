package loaddata;

public class Enemy {
    private int id;
    private String name;
    private String category;
    private int health;
    private int maxHealth;
    private int attack;
    private int defense;
    private int minSpeed;
    private int maxSpeed;
    private int evasion;
    private int cost;
    private int evadeReduction;
    private boolean alive;
    private boolean isAttackable;
    private String mappingId;
    private String spawnPosition;
    private boolean isElite;
    private String imagePath;

    public Enemy() {}

    public Enemy(int id, String name, String category, int health, int maxHealth, int attack, int defense, int minSpeed, 
                 int maxSpeed, int evasion, int cost, int evadeReduction, boolean alive, boolean isAttackable, String spawnPosition, boolean isElite,
                 String imagePath) {
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
        this.spawnPosition = spawnPosition;
        this.isElite = isElite;
        this.imagePath = imagePath;
    }

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
        this.mappingId = other.mappingId;
        this.spawnPosition = other.spawnPosition;
        this.isElite = other.isElite;
        this.imagePath = other.imagePath;
    }

    // Getters
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

    public String getSpawnPosition() {
        return spawnPosition;
    }

    public boolean isElite() {
        return isElite;
    }

    public String getImagePath() {
        return imagePath;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setMinSpeed(int minSpeed) {
        this.minSpeed = minSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setEvasion(int evasion) {
        this.evasion = evasion;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setEvadeReduction(int evadeReduction) {
        this.evadeReduction = evadeReduction;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setAttackable(boolean isAttackable) {
        this.isAttackable = isAttackable;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
    }

    public void setSpawnPosition(String spawnPosition) {
        this.spawnPosition = spawnPosition;
    }

    public void setElite(boolean isElite) {
        this.isElite = isElite;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
