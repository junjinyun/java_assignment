package loaddata;

public class EquipmentArmor {
    private String Name;
    private int Defense;
    private int DefenseGrowth;
    private int Health;
    private int HealthGrowth;
    private int Evade;
    private int EvadeGrowth;
    private int Level;
    private int MaxLevel;
    private String Owner;

    // 기본 생성자
    public EquipmentArmor() {
    }

    // 매개변수가 있는 생성자
    public EquipmentArmor(String name, int defense, int defenseGrowth, int health, int healthGrowth, int evade,
            int evadeGrowth, int level, int maxLevel, String owner) {
        this.Name = name;
        this.Defense = defense;
        this.DefenseGrowth = defenseGrowth;
        this.Health = health;
        this.HealthGrowth = healthGrowth;
        this.Evade = evade;
        this.EvadeGrowth = evadeGrowth;
        this.Level = level;
        this.MaxLevel = maxLevel;
        this.Owner = owner;
    }

    // Getter 메서드들
    public String getName() {
        return Name;
    }

    public int getDefense() {
        return Defense;
    }

    public int getDefenseGrowth() {
        return DefenseGrowth;
    }

    public int getHealth() {
        return Health;
    }

    public int getHealthGrowth() {
        return HealthGrowth;
    }

    public int getEvade() {
        return Evade;
    }

    public int getEvadeGrowth() {
        return EvadeGrowth;
    }

    public int getLevel() {
        return Level;
    }

    public int getMaxLevel() {
        return MaxLevel;
    }

    public String getOwner() {
        return Owner;
    }
}