package loaddata;

public class EquipmentWeapon {
    private String name;
    private int attack;
    private int attackGrowth;
    private int evadeReductionGrowth;
    private int level;
    private int maxLevel;
    private String owner;

    // 기본 생성자
    public EquipmentWeapon() {
    }

    // 매개변수가 있는 생성자
    public EquipmentWeapon(String name, int attack, int attackGrowth, int evadeReductionGrowth, int level, int maxLevel, String owner) {
        this.name = name;
        this.attack = attack;
        this.attackGrowth = attackGrowth;
        this.evadeReductionGrowth = evadeReductionGrowth;
        this.level = level;
        this.maxLevel = maxLevel;
        this.owner = owner;
    }

    // Getter 메서드들
    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getAttackGrowth() {
        return attackGrowth;
    }

    public int getEvadeReductionGrowth() {
        return evadeReductionGrowth;
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
