package gameplay;

import gameplay.AdditionalEffects.*;
import gameplay.Event.StageInfo;
import gameplay.Party.AllyParty;
import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyParty;
import gameplay.Party.EnemyStatusManager;
import loaddata.Ally;
import loaddata.Enemy;
import loaddata.Stage;

import java.util.Scanner;

public class StatusEffectTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("테스트할 대상 선택 (1: 아군, 2: 적군): ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            testAlly();
        } else if (choice == 2) {
            testEnemy();
        } else {
            System.out.println("잘못된 입력입니다. 프로그램 종료.");
        }
        scanner.close();
    }

    private static void testAlly() {
        System.out.println("\n[아군 테스트 시작]");
        AllyParty allyParty = new AllyParty(); // 아군 파티 생성
        AllyStatusManager allyStatusManager = allyParty.getAllyByMappingID("A1");

        applyEffects(allyStatusManager);

        System.out.println("초기 스탯:");
        printAllyStats(allyStatusManager);
        allyStatusManager.printStatusEffects();
        System.out.println("활성화된 버프/디버프 목록:");
        allyStatusManager.printStatModifierEffects();

        for (int turn = 1; turn <= 4; turn++) {
            System.out.println("\n[턴 " + turn + "]");

            // 1턴은 적용 직후이므로 턴 종료 처리 생략, 2턴부터 체크
            if (turn > 1) {
                allyStatusManager.checkStatModifierEffectsEnd();
            }

            printAllyStats(allyStatusManager);
            allyStatusManager.printStatusEffects();
            allyStatusManager.applyStatusEffects();
            System.out.println("활성화된 버프/디버프 목록:");
            allyStatusManager.printStatModifierEffects();
        }
    }

    private static void testEnemy() {
        System.out.println("\n[적군 테스트 시작]");
        StageInfo stageInfo = new StageInfo();
        Stage randomStage = stageInfo.getRandomValidStage();

        EnemyParty enemyParty = new EnemyParty(randomStage);
        EnemyStatusManager enemyStatusManager = enemyParty.getEnemyByMappingID("E1");

        applyEffects(enemyStatusManager);

        System.out.println("초기 스탯:");
        printEnemyStats(enemyStatusManager);
        enemyStatusManager.printStatusEffects();
        System.out.println("활성화된 버프/디버프 목록:");
        enemyStatusManager.printStatModifierEffects();

        for (int turn = 1; turn <= 4; turn++) {
            System.out.println("\n[턴 " + turn + "]");

            if (turn > 1) {
                enemyStatusManager.checkStatModifierEffectsEnd();
            }

            printEnemyStats(enemyStatusManager);
            enemyStatusManager.printStatusEffects();
            enemyStatusManager.applyStatusEffects();
            System.out.println("활성화된 버프/디버프 목록:");
            enemyStatusManager.printStatModifierEffects();
        }
    }

    private static void applyEffects(Object target) {
        StatModifierEffect buff = new StatModifierEffect("공격력 증가", 20, 3);
        StatModifierEffect debuff = new StatModifierEffect("방어력 증가", 30, 2);
        StatModifierEffect buff2 = new StatModifierEffect("공격력 증가", 30, 2);
        StatModifierEffect debuff2 = new StatModifierEffect("방어력 증가", 20, 3);
        StatusEffect a = new StatusEffect("출혈", 10, 2);
        StatusEffect a1 = new StatusEffect("출혈", 5, 3);
        StatusEffect a2 = new StatusEffect("지속회복", 10, 3);

        if (target instanceof AllyStatusManager ally) {
            ally.addStatModifierEffect(buff);
            ally.addStatModifierEffect(debuff);
            ally.addStatModifierEffect(buff2);
            ally.addStatModifierEffect(debuff2);

            ally.addStatusEffect(a);
            ally.addStatusEffect(a1);
            ally.addStatusEffect(a2);
        } else if (target instanceof EnemyStatusManager enemy) {
            enemy.addStatModifierEffect(buff);
            enemy.addStatModifierEffect(debuff);
            enemy.addStatModifierEffect(buff2);
            enemy.addStatModifierEffect(debuff2);

            enemy.addStatusEffect(a);
            enemy.addStatusEffect(a1);
            enemy.addStatusEffect(a2);
        }
    }

    private static void printAllyStats(AllyStatusManager allyStatusManager) {
        Ally ally = allyStatusManager.getBaseStats();
        System.out.println("이름: " + ally.getName());
        System.out.println("체력: " + ally.getHealth() + "/" + ally.getMaxHealth());
        System.out.println("공격력: " + ally.getAttack());
        System.out.println("방어력: " + ally.getDefense());
    }

    private static void printEnemyStats(EnemyStatusManager enemyStatusManager) {
        Enemy enemy = enemyStatusManager.getBaseStats();
        System.out.println("이름: " + enemy.getName());
        System.out.println("체력: " + enemy.getHealth() + "/" + enemy.getMaxHealth());
        System.out.println("공격력: " + enemy.getAttack());
        System.out.println("방어력: " + enemy.getDefense());
    }
}
