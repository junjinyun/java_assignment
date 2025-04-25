package gameplay.Event;

import gameplay.Party.AllyParty;  // AllyParty 클래스 import 추가
import gameplay.Party.AllyStatusManager; // AllyStatusManager import 추가
import java.util.List;
import java.util.Random;

public class EventLauncher {

    private AllyParty allyParty;  // AllyParty 객체를 필드로 선언

    // 생성자에서 AllyParty 객체를 받아옴
    public EventLauncher(AllyParty allyParty) {
        this.allyParty = allyParty;
    }

    public void MerchantEvent(String YesNo) {
        System.out.println("상점 이벤트 실행됨");
    }

    public void BlackMarketEvent(String item, String YesNo, String EventResult) {
        processItemSubmitResult(EventResult);
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
    }

    public void TrapActivatedEvent(String item, String YesNo, String EventResult) {
        processItemSubmitResult(EventResult);
    }

    // --------------------------- 핵심 이벤트 처리 메서드 ---------------------------

    private void processItemSubmitResult(String EventResult) {
        if (EventResult.equals("없음")) {
            System.out.println("아무일도 일어나지 않음");
            return;
        }

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
                int start = EventResult.indexOf('(');
                int end = EventResult.indexOf(')');
                damagePercent = Integer.parseInt(EventResult.substring(start + 1, end));
            } else {
                System.out.println("피해 수치가 명시되지 않았습니다.");
                return;
            }

            Random rand = new Random();
            List<AllyStatusManager> party = allyParty.getParty();  // party 리스트 가져오기

            if (EventResult.contains("랜덤")) {
                int index = rand.nextInt(party.size());  // party 크기만큼 랜덤
                var ally = party.get(index);
                int damage = ally.getBaseStats().getMaxHealth() * damagePercent / 100;
                int newHealth = Math.max(ally.getBaseStats().getHealth() - damage, 1);
                ally.getBaseStats().setHealth(newHealth);

                System.out.println("[랜덤 대상] " + ally.getName() + " 이(가) " + damage + " 피해를 입었습니다. 현재 체력: "
                        + newHealth + "/" + ally.getBaseStats().getMaxHealth());

            } else if (EventResult.contains("전체")) {
                for (var ally : party) {
                    int damage = ally.getBaseStats().getMaxHealth() * damagePercent / 100;
                    int newHealth = Math.max(ally.getBaseStats().getHealth() - damage, 1);
                    ally.getBaseStats().setHealth(newHealth);

                    System.out.println("[전체 대상] " + ally.getName() + " 이(가) " + damage + " 피해를 입었습니다. 현재 체력: "
                            + newHealth + "/" + ally.getBaseStats().getMaxHealth());
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
        int action = rand.nextInt(4);

        switch (action) {
            case 0 -> {
                System.out.println("석상의 눈이 붉게 빛납니다... 어딘가에서 오래 묻힌 보물이 모습을 드러냅니다");
                for (int i = 0; i < 5; i++) {
                    ItemDrop.runSingleDrop();
                }
            }

            case 1 -> {
                int index = rand.nextInt(allyParty.getParty().size());
                var ally = allyParty.getParty().get(index);
                int heal = ally.getBaseStats().getMaxHealth() * 50 / 100;
                int newHealth = Math.min(ally.getBaseStats().getHealth() + heal, ally.getBaseStats().getMaxHealth());
                ally.getBaseStats().setHealth(newHealth);

                System.out.println(ally.getName() + " 이(가) 석상의 힘으로 체력을 " + heal + " 회복했습니다.");
            }

            case 2 -> {
                int index = rand.nextInt(allyParty.getParty().size());
                var ally = allyParty.getParty().get(index);
                int damage = ally.getBaseStats().getMaxHealth() * 30 / 100;
                int newHealth = Math.max(ally.getBaseStats().getHealth() - damage, 1);
                ally.getBaseStats().setHealth(newHealth);

                System.out.println(ally.getName() + " 이(가) 석상의 저주로 " + damage + " 피해를 입었습니다.");
            }

            case 3 -> {
                System.out.println("지면이 갈라지고, 어둠 속에서 말 없는 적이 모습을 드러냅니다");
                // 전투 로직 등 추가 가능
            }
        }
    }
}