package dungeon;

/* 적 정보 클래스 */

public class Enemy {
    private int Id; // 아이디
    private String Name; // 이름
    private int Health; // 현재_체력
    private int MaxHealth; // 최대_체력
    private int Attack; // 공격력
    private int Defense; // 방어력
    private int MinSpeed; // 최소 스피드
    private int MaxSpeed; // 최대 스피드
    private int Evasion; // 회피율
    private int Cost; // 비용
    private int EvadeReduction; // 회피율 무시
    private boolean Alive; // 생사 여부
    private boolean IsAttackable; // 공격 가능 여부

    public Enemy() {}

    public Enemy(int id, String name, int health, int maxHealth, int attack, int defense, int minSpeed, int maxSpeed, int evasion, int cost, int evadeReduction, boolean alive, boolean isAttackable) {
        this.Id = id;
        this.Name = name;
        this.Health = health;
        this.MaxHealth = maxHealth;
        this.Attack = attack;
        this.Defense = defense;
        this.MinSpeed = minSpeed;
        this.MaxSpeed = maxSpeed;
        this.Evasion = evasion;
        this.Cost = cost;
        this.EvadeReduction = evadeReduction;
        this.Alive = alive;
        this.IsAttackable = isAttackable;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public int getHealth() {
        return Health;
    }

    public int getMaxHealth() {
        return MaxHealth;
    }

    public int getAttack() {
        return Attack;
    }

    public int getDefense() {
        return Defense;
    }

    public int getMinSpeed() {
        return MinSpeed;
    }

    public int getMaxSpeed() {
        return MaxSpeed;
    }

    public int getEvasion() {
        return Evasion;
    }

    public int getCost() {
        return Cost;
    }

    public int getEvadeReduction() {
        return EvadeReduction;
    }

    public boolean getAlive() {
        return Alive;
    }

    public boolean getIsAttackable() {
        return IsAttackable;
    }
}