package dungeon;

public class EquipmentWeapon {
    private String name;
    private int attack;
    private int attack_growth;
    private int evadereduction_growth;
    private int level;
    private int max_level;
    private String owner;

    // 기본 생성자
    public EquipmentWeapon() {
    }

    // 매개변수가 있는 생성자
    public EquipmentWeapon(String name, int attack, int attack_growth, int evadereduction_growth, int level, int max_level, String owner) {
        this.name = name;
        this.attack = attack;
        this.attack_growth = attack_growth;
        this.evadereduction_growth = evadereduction_growth;
        this.level = level;
        this.max_level = max_level;
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
        return attack_growth;
    }

    public int getEvadereductionGrowth() {
        return evadereduction_growth;
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