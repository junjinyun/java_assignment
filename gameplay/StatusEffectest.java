package gameplay;

import gameplay.Party.AllyParty;
import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyParty;
import gameplay.Party.EnemyStatusManager;
import gameplay.Party.StatusEffect;

import java.util.Random;

public class StatusEffectest {

    private AllyParty allyParty;
    private EnemyParty eParty;

    public StatusEffectest(AllyParty allyParty, EnemyParty eParty) {
        this.allyParty = allyParty;
        this.eParty = eParty;
    }

    // 아군 스텟 출력 메서드
    private void printAllyStats() {
        System.out.println("=== 아군 스텟 ===");
        for (AllyStatusManager ally : allyParty.getParty()) {
            System.out.println(ally);
        }
        System.out.println("==================");
    }
    private void printEStats() {
        System.out.println("=== 적군 스텟 ===");
        for (EnemyStatusManager enemy : eParty.getEnemyParty()) {
            System.out.println(enemy);
        }
        System.out.println("==================");
    }

    // 랜덤한 1명에게 출혈 상태이상 부여
    public void applyBleedStatusEffect() {
        Random random = new Random();

        // 5번 반복하여 각각 다른 아군에게 출혈 상태이상 부여
        for (int i = 0; i < 5; i++) {
            // 출혈 상태이상 생성 (위력 5, 지속 2턴) - 상태이상 객체를 매번 새로 생성
            StatusEffect bleed = new StatusEffect("출혈", 5, 2);

            // 랜덤으로 1명 아군 선택
            int randomAllyIndex = random.nextInt(allyParty.getParty().size());  // 0~3 중 랜덤으로 선택
            AllyStatusManager selectedAlly = allyParty.getParty().get(randomAllyIndex);

            // 선택된 아군 출력
            System.out.println("=== 랜덤으로 선택된 아군 ===");
            // 출혈 상태이상 부여
            selectedAlly.addStatusEffect(bleed);  // 선택된 아군에 출혈 상태이상 추가
            System.out.println(selectedAlly);  // 랜덤으로 선택된 아군 출력
            System.out.println("========================");
        }
        System.out.println("asdsadqwdqwd");
        for (int i = 0; i < 5; i++) {
            // 출혈 상태이상 생성 (위력 5, 지속 2턴) - 상태이상 객체를 매번 새로 생성
            StatusEffect bleed = new StatusEffect("출혈", 5, 2);

            // 랜덤으로 1명 아군 선택
            int randomEIndex = random.nextInt(eParty.getEnemyParty().size());  // 0~3 중 랜덤으로 선택
            EnemyStatusManager selectedE = eParty.getEnemyParty().get(randomEIndex);

            // 선택된 아군 출력
            System.out.println("=== 랜덤으로 선택된 적군 ===");
            // 출혈 상태이상 부여
            selectedE.addStatusEffect(bleed);  // 선택된 아군에 출혈 상태이상 추가
            System.out.println(selectedE);  // 랜덤으로 선택된 적군 출력
            System.out.println("========================");
        }

        // 상태이상 적용 (출혈 발동)
        System.out.println("=== 상태이상 발동 후 ===");
        for (AllyStatusManager ally : allyParty.getParty()) {
            ally.applyStatusEffects();  // 모든 아군에게 상태이상 발동 (출혈)
        }
        for (EnemyStatusManager enemy : eParty.getEnemyParty()) {
        	enemy.applyStatusEffects();  // 모든 아군에게 상태이상 발동 (출혈)
        }

        // 상태 발동 후 아군 상태 출력
        printAllyStats();
        printEStats();
    }

    // 디버깅을 위한 메인 실행 메서드
    public static void main(String[] args) {
        // AllyParty 객체를 생성하여 아군 정보를 로드
        AllyParty allyParty = new AllyParty();
        EnemyParty eParty = new EnemyParty();
        // Debugger 객체 생성
        StatusEffectest debugger = new StatusEffectest(allyParty, eParty);

        // 랜덤으로 선택된 대상에게 출혈 상태이상 5회 부여 후 발동
        debugger.applyBleedStatusEffect();
    }
}