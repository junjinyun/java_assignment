package gameplay;

import java.util.List;
import java.util.Random;

import dungeon.Ally;
import dungeon.GetAllyJson;

public class SelectAlly {
    String Name; // 이름
    int Health; // 체력
    int MaxHealth; // 최대체력
    int Attack; // 공격력
    int Defense; // 방어력
    int MinSpeed; // 최소속도
    int MaxSpeed; // 최대속도
    int CurrentSpeed; // 현재속도
    int ActionOrder; // 행동순서
    int Evasion; // 회피율
    int Evadereduction; // 공격시 회피수치 무시율
    Boolean Alive; // 생사여부
    Boolean IsAttackable; // 대상의 공격 가능 여부

    // 생성자
    public SelectAlly(String name, int health, int maxHealth, int attack, int defense, int minSpeed, int maxSpeed,
                      int evasion, int evadereduction, boolean alive, boolean isAttackable) {
        this.Name = name;
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

    public static SelectAlly[] Ally = new SelectAlly[4];
    public static List<Ally> AllyList = GetAllyJson.loadAlly();

    // 배열의 형식으로 아군 편성창을 생성
    public static void SelectAllyChar() {
        for (int i = 0; i < Ally.length; i++) {
            Random randomEnemy = new Random();
            int CurrentAlly = randomEnemy.nextInt(AllyList.size()); // 도감에서 랜덤한 칸의 적의 주소를 불러옴으로서 정보를 확인하는데 사용
            Ally[i] = new SelectAlly(
                AllyList.get(CurrentAlly).getName(),
                AllyList.get(CurrentAlly).getHealth(),
                AllyList.get(CurrentAlly).getMaxHealth(),
                AllyList.get(CurrentAlly).getAttack(),
                AllyList.get(CurrentAlly).getDefense(),
                AllyList.get(CurrentAlly).getMinSpeed(),
                AllyList.get(CurrentAlly).getMaxSpeed(),
                AllyList.get(CurrentAlly).getEvasion(),
                AllyList.get(CurrentAlly).getEvadereduction(),
                AllyList.get(CurrentAlly).isAlive(),
                AllyList.get(CurrentAlly).isAttackable()
            );
        }
        // 추후에 도감 파일과 연동하여 캐릭터 리스트를 출력하며 그 중에서 4명을 선택하여 배치하는 방식으로 변경
    }
}