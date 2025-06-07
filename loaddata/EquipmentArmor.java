package loaddata;

public class EquipmentArmor {
    private String name;
    private int defense;
    private int defenseGrowth;
    private int health;
    private int healthGrowth;
    private int evade;
    private int evadeGrowth;
    private int level;
    private int maxLevel;
    private String owner;

    // 기본 생성자
    public EquipmentArmor() {
    }

    // 매개변수가 있는 생성자
    public EquipmentArmor(String name, int defense, int defenseGrowth, int health, int healthGrowth, int evade,
            int evadeGrowth, int level, int maxLevel, String owner) {
        this.name = name;
        this.defense = defense;
        this.defenseGrowth = defenseGrowth;
        this.health = health;
        this.healthGrowth = healthGrowth;
        this.evade = evade;
        this.evadeGrowth = evadeGrowth;
        this.level = level;
        this.maxLevel = maxLevel;
        this.owner = owner;
    }

    // Getter 메서드들
    public String getName() {
        return name;
    }

    public int getDefense() {
        return defense;
    }

    public int getDefenseGrowth() {
        return defenseGrowth;
    }

    public int getHealth() {
        return health;
    }

    public int getHealthGrowth() {
        return healthGrowth;
    }

    public int getEvade() {
        return evade;
    }

    public int getEvadeGrowth() {
        return evadeGrowth;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public String getOwner() {
        return owner;
    }
}
