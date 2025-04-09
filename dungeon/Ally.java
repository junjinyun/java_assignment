package dungeon;

/* 적 정보 클래스 */

public class Ally {
    private String Name; // 이름
    private String Information; // 추가 정보 (Name 아래로 이동)
    private int Health; // 현재_체력
    private int MaxHealth; // 최대_체력
    private int Attack; // 공격력
    private int Defense; // 방어력
    private int MinSpeed; // 최소 스피드
    private int MaxSpeed; // 최대 스피드
    private int Evasion; // 회피율
    private int Evadereduction; // 회피율 무시
    private boolean Alive; // 생사 여부
    private boolean IsAttackable; // 공격 가능 여부

    public Ally() {
    }

    public Ally(String name, String information, int health, int maxHealth, int attack, int defense, int minSpeed, int maxSpeed,
                int evasion, int evadereduction, boolean alive, boolean isAttackable) {
        this.Name = name;
        this.Information = information; // 정보 초기화
        this.Health = health;
        this.MaxHealth = maxHealth;
        this.Attack = attack;
        this.Defense = defense;
        this.MinSpeed = minSpeed;
        this.MaxSpeed = maxSpeed;
        this.Evasion = evasion;
        this.Evadereduction = evadereduction;
        this.Alive = alive;
        this.IsAttackable = isAttackable;
    }

    public String getName() {
        return Name;
    }

    public String getInformation() {
        return Information; // Information getter
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

    public int getEvadereduction() {
        return Evadereduction;
    }

    public boolean getAlive() {
        return Alive;
    }

    public boolean getisAttackable() {
        return IsAttackable;
    }
}