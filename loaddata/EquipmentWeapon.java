package loaddata;

public class EquipmentWeapon {
    private String Name;
    private int Attack;
    private int AttackGrowth;
    private int EvadereductionGrowth;
    private int Level;
    private int MaxLevel;
    private String Owner;

    // 기본 생성자
    public EquipmentWeapon() {
    }

    // 매개변수가 있는 생성자
    public EquipmentWeapon(String name, int attack, int attackGrowth, int evadereductionGrowth, int level, int maxLevel, String owner) {
        this.Name = name;
        this.Attack = attack;
        this.AttackGrowth = attackGrowth;
        this.EvadereductionGrowth = evadereductionGrowth;
        this.Level = level;
        this.MaxLevel = maxLevel;
        this.Owner = owner;
    }

    // Getter 메서드들
    public String getName() {
        return Name;
    }

    public int getAttack() {
        return Attack;
    }

    public int getAttackGrowth() {
        return AttackGrowth;
    }

    public int getEvadereductionGrowth() {
        return EvadereductionGrowth;
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