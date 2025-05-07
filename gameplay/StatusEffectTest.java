package gameplay;

import gameplay.AdditionalEffects.*;
import gameplay.Party.AllyParty;
import gameplay.Party.AllyStatusManager;
import loaddata.Ally;

public class StatusEffectTest {
	public static void main(String[] args) {
		// 아군 객체 생성 예시 (직접 입력 예정)
		AllyParty allyParty = new AllyParty(); // 예시로 Ally1을 생성. 실제로 Ally 객체는 JSON 데이터로 생성될 것입니다.

		// AllyStatusManager 생성
		AllyStatusManager allyStatusManager = allyParty.getAllyByMappingID("A1");

		// 버프나 디버프를 적용하는 StatModifierEffect 생성
		StatModifierEffect buff = new StatModifierEffect("공격력 증가", 20, 3); // 공격력 20% 증가, 지속시간 3턴
		StatModifierEffect debuff = new StatModifierEffect("방어력 증가", 30, 2); // 방어력 15% 감소, 지속시간 2턴
		StatModifierEffect buff2 = new StatModifierEffect("공격력 증가", 30, 2); // 공격력 20% 증가, 지속시간 3턴
		StatModifierEffect debuff2 = new StatModifierEffect("방어력 증가", 20, 3); // 방어력 15% 감소, 지속시간 2턴
		StatusEffect a = new StatusEffect("출혈", 10, 2); // 10의 출혈 피해, 2턴 지속
		StatusEffect a1 = new StatusEffect("출혈", 5, 3); // 5의 출혈 피해, 3턴 지속
		StatusEffect a2 = new StatusEffect("지속회복", 10, 3); // 10의 지속회복, 3턴 지속

		// 버프 및 상태이상 적용
		allyStatusManager.addStatModifierEffect(buff);
		allyStatusManager.addStatModifierEffect(debuff);
		allyStatusManager.addStatModifierEffect(buff2);
		allyStatusManager.addStatModifierEffect(debuff2);

		allyStatusManager.addStatusEffect(a);
		allyStatusManager.addStatusEffect(a1);
		allyStatusManager.addStatusEffect(a2);

		// 초기 상태 출력
		System.out.println("초기 스탯:");
		printAllyStats(allyStatusManager);

		// 턴 1부터 시작 (3턴 이상, 각 턴마다 상태 확인)
		for (int turn = 1; turn <= 4; turn++) { // 3턴 동안 상태를 테스트
			System.out.println("\n[턴 " + turn + "]");

			// 상태 효과가 종료될 때 확인
			allyStatusManager.checkStatModifierEffectsEnd();

			// 현재 상태 출력
			printAllyStats(allyStatusManager);
			allyStatusManager.printStatusEffects();
			// 상태이상 적용 (매턴마다 상태이상을 처리)
			allyStatusManager.applyStatusEffects();
			// 현재 활성화된 버프/디버프 목록 출력
			System.out.println("활성화된 버프/디버프 목록:");
			allyStatusManager.printStatModifierEffects();
		}
	}

	// 아군 스탯을 출력하는 메서드
	public static void printAllyStats(AllyStatusManager allyStatusManager) {
		Ally ally = allyStatusManager.getBaseStats();
		System.out.println("이름: " + ally.getName());
		System.out.println("체력: " + ally.getHealth() + "/" + ally.getMaxHealth());
		System.out.println("공격력: " + ally.getAttack());
		System.out.println("방어력: " + ally.getDefense());
	}

}
