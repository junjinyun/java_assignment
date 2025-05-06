package gameplay.Party;

import java.util.List;
import java.util.Random;

import loaddata.EnemyManager;
import loaddata.Enemy;

public class SelectEnemy {
    String Name; // 이름
    int Health; // 체력
    int MaxHealth; // 최대체력
    int Attack; // 공격력
    int Defense; // 방어력
    int MinSpeed; // 최소속도
    int MaxSpeed; // 최대속도
    int Evasion; // 회피율
    int CurrentSpeed; // 현재속도
    int ActionOrder; // 행동순서
    int Cost; // 적 조합 생성시 필요한 코스트 소비량
    int Evadereduction; // 공격시 회피수치 무시율
    Boolean Alive; // 생사여부
    Boolean IsAttackable; // 대상의 공격 가능 여부

    // 생성자
    public SelectEnemy(String name, int health, int maxHealth, int attack, int defense, int minSpeed, int maxSpeed,
                       int evasion, int cost, int evadereduction, boolean alive, boolean isAttackable) {
        this.Name = name;
        this.Health = health;
        this.MaxHealth = maxHealth;
        this.Attack = attack;
        this.Defense = defense;
        this.MinSpeed = minSpeed;
        this.MaxSpeed = maxSpeed;
        this.Evasion = evasion;
        this.Cost = cost;
        this.Evadereduction = evadereduction;
        this.Alive = alive;
        this.IsAttackable = isAttackable;
    }

    public static SelectEnemy[] Enemy = new SelectEnemy[4]; // SelectEnemy의 특성을 가진 Enemy 객체 배열로 생성
    public static List<Enemy> Enemies = EnemyManager.loadEnemies(); // 적 도감 불러와서 Enemies에 리스트로 저장

    // 적 선택 메서드
    public static void SelectEnemyChar() {
        for (int i = 0; i < Enemy.length; i++) {
            Random randomEnemy = new Random();
            int CurrentEnemy = randomEnemy.nextInt(Enemies.size()); // 도감에서 랜덤한 칸의 적의 주소를 불러옴으로서 정보를 확인하는데 사용
            Enemy[i] = new SelectEnemy(
                Enemies.get(CurrentEnemy).getName(),
                Enemies.get(CurrentEnemy).getHealth(),
                Enemies.get(CurrentEnemy).getMaxHealth(),
                Enemies.get(CurrentEnemy).getAttack(),
                Enemies.get(CurrentEnemy).getDefense(),
                Enemies.get(CurrentEnemy).getMinSpeed(),
                Enemies.get(CurrentEnemy).getMaxSpeed(),
                Enemies.get(CurrentEnemy).getEvasion(),
                Enemies.get(CurrentEnemy).getCost(),
                Enemies.get(CurrentEnemy).getEvadeReduction(),
                Enemies.get(CurrentEnemy).isAlive(),
                Enemies.get(CurrentEnemy).isAttackable()
            );
        }
    }
    
    // WIP 추후에 도감과 연동하여 데이터를 받아서 코스트 값의 한도 내에서 랜덤 생성
}