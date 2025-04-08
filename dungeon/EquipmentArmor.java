package dungeon;

public class EquipmentArmor {
    private String name;
    private int defense;
    private int defense_growth;
    private int health;
    private int health_growth;
    private int evade;
    private int evade_growth;
    private int level;
    private int max_level;
    private String owner;

    // 기본 생성자
    public EquipmentArmor() {
    }

    // 매개변수가 있는 생성자
    public EquipmentArmor(String name, int defense, int defense_growth, int health, int health_growth, int evade,
            int evade_growth, int level, int max_level, String owner) {
        this.name = name;
        this.defense = defense;
        this.defense_growth = defense_growth;
        this.health = health;
        this.health_growth = health_growth;
        this.evade = evade;
        this.evade_growth = evade_growth;
        this.level = level;
        this.max_level = max_level;
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
        return defense_growth;
    }

    public int getHealth() {
        return health;
    }

    public int getHealthGrowth() {
        return health_growth;
    }

    public int getEvade() {
        return evade;
    }

    public int getEvadeGrowth() {
        return evade_growth;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxLevel() {
        return max_level;
    }

    public String getOwner() {
        return owner;
    }
}