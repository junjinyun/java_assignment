package gameplay;

import java.util.Random;
import gameplay.SelectAlly;

@SuppressWarnings("unused")
public class EventLauncher {
	public void MerchantEvent(String YesNo) {
		System.out.println("상점 이벤트 실행됨");
		// 상점기능 추가후 연동
	}

	public void BlackMarketEvent(String item, String YesNo, String EventResult) {
		processItemSubmitResult(EventResult);
		// 상점기능 추가후 연동
	}

	public void RoomCollapseEvent(String item, String YesNo, String EventResult) {
		processItemSubmitResult(EventResult);
	}

	public void CampsiteEvent(String item, String YesNo, String EventResult) {
		processItemSubmitResult(EventResult);
	}

	public void MysteriousStatueEvent(String YesNo) {
	    System.out.println("불가사의한 석상 이벤트 실행됨");

	    if (YesNo.equalsIgnoreCase("yes"))
	    	executeStatueEffect();
	    	
	    else
	    	System.out.println("당신은 불길한 기운을 외면한 채 발걸음을 옮깁니다.");

	    
	}

	public void TreasureChestEvent(String item, String YesNo, String EventResult) {
		processItemSubmitResult(EventResult);
		// 인벤토리 구현 후 연동
	}

	public void TrapActivatedEvent(String item, String YesNo, String EventResult) {
		processItemSubmitResult(EventResult);
	}

	private void processItemSubmitResult(String EventResult) {
		if (EventResult.equals("없음")) {
			System.out.println("아무일도 일어나지 않음");
			return;
		}

		// 보물 이벤트는 하나의 메서드에서 처리
		if (EventResult.equals("보물") || EventResult.equals("파괴")) {
			handleTreasureResult(EventResult);
			return;
		}

		System.out.println(EventResult + " 가 발생하였습니다.");

		if (EventResult.contains("피해")) {
			applyDamageFromEvent(EventResult);
		}
	}

	private void handleTreasureResult(String result) {
		Random rand = new Random();

		if (result.equals("보물")) {
			System.out.println("보물상자가 열렸습니다!.");
			for (int i = 0; i < 6; i++) {
				ItemDrop.runSingleDrop();
			}
		} else if (result.equals("파괴")) {
			if (rand.nextInt(100) < 60) {
				System.out.println("내용물이 손상되었습니다.");
				for (int i = 0; i < 3; i++) {
					ItemDrop.runSingleDrop();
				}
			} else {
				System.out.println("내용물이 파손되어 아무것도 얻을 수 없습니다.");
			}
		}
	}

	private void applyDamageFromEvent(String EventResult) {
	    try {
	        int damagePercent = 0;
	        if (EventResult.contains("(") && EventResult.contains(")")) {
	            int percentStart = EventResult.indexOf('(');
	            int percentEnd = EventResult.indexOf(')');
	            damagePercent = Integer.parseInt(EventResult.substring(percentStart + 1, percentEnd));
	        } else {
	            System.out.println("피해 수치가 명시되지 않았습니다.");
	            return;
	        }

	        if (EventResult.contains("랜덤")) {
	            Random rand = new Random();
	            int targetIndex = rand.nextInt(SelectAlly.Ally.length);
	            SelectAlly target = SelectAlly.Ally[targetIndex];
	            int damage = target.MaxHealth * damagePercent / 100;

	            target.Health -= damage;
	            if (target.Health <= 0) target.Health = 1;

	            // 원본에 반영 (참조 방식이므로 자동으로 반영됨)
	            SelectAlly.Ally[targetIndex] = target;

	            System.out.println("[랜덤 대상] " + target.Name + " 이(가) " + damage + " 피해를 입었습니다. 현재 체력: " + target.Health + "/" + target.MaxHealth);

	        } else if (EventResult.contains("전체")) {
	            for (int i = 0; i < SelectAlly.Ally.length; i++) {
	                SelectAlly ally = SelectAlly.Ally[i];
	                int damage = ally.MaxHealth * damagePercent / 100;

	                ally.Health -= damage;
	                if (ally.Health <= 0) ally.Health = 1;

	                // 원본에 반영
	                SelectAlly.Ally[i] = ally;

	                System.out.println("[전체 대상] " + ally.Name + " 이(가) " + damage + " 피해를 입었습니다. 현재 체력: " + ally.Health + "/" + ally.MaxHealth);
	            }
	        } else {
	            System.out.println("피해 유형을 확인할 수 없습니다.");
	        }
	    } catch (Exception e) {
	        System.out.println("[오류] 피해 처리 중 문제가 발생했습니다.");
	        e.printStackTrace();
	    }
	}
	private void executeStatueEffect() {
	    Random rand = new Random();
	    int action = rand.nextInt(4); // 0 ~ 3

	    switch (action) {
	        case 0 -> {
	            System.out.println("석상의 눈이 붉게 빛납니다... 어딘가에서 오래 묻힌 보물이 모습을 드러냅니다");
	            for (int i = 0; i < 5; i++) {
	                ItemDrop.runSingleDrop();
	            }
	        }

	        case 1 -> {
	            if (SelectAlly.Ally.length == 0) return;
	            SelectAlly ally = SelectAlly.Ally[rand.nextInt(SelectAlly.Ally.length)];
	            int healAmount = ally.MaxHealth * 50 / 100;
	            ally.Health = Math.min(ally.Health + healAmount, ally.MaxHealth);
	            System.out.println(ally.Name + " 이(가) 석상의 힘으로 체력을 " + healAmount + " 회복했습니다.");
	        }

	        case 2 -> {
	            if (SelectAlly.Ally.length == 0) return;
	            SelectAlly ally = SelectAlly.Ally[rand.nextInt(SelectAlly.Ally.length)];
	            int damage = ally.MaxHealth * 30 / 100;
	            ally.Health -= damage;
	            if (ally.Health < 1) ally.Health = 1;
	            System.out.println(ally.Name + " 이(가) 석상의 저주로 " + damage + " 피해를 입었습니다.");
	        }

	        case 3 -> {
	            System.out.println("지면이 갈라지고, 어둠 속에서 말 없는 적이 모습을 드러냅니다");
	            // 전투 로직 등 추가 가능
	        }
	    }
	}
}