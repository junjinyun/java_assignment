package gameplay.Event;

import gameplay.GamePlayer;
import gameplay.Party.AllyParty;
import gameplay.Party.AllyStatusManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventLauncher {

    private GamePlayer gameplayer;

    private final List<EventListener> listeners = new ArrayList<>();

    public EventLauncher(GamePlayer gameplayer) {
        this.gameplayer = gameplayer;
    }

    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }

    public void removeEventListener(EventListener listener) {
        listeners.remove(listener);
    }

    private void notifyEvent(String eventType, Object data) {
        for (EventListener listener : listeners) {
            listener.onEvent(eventType, data);
        }
    }

    public void MerchantEvent(String YesNo) {
        notifyEvent("merchant", YesNo);
        gameplayer.setOnEvent(false);
    }

    public void BlackMarketEvent(String EventResult) {
        processItemSubmitResult(EventResult);
        notifyEvent("blackMarket", EventResult);
        gameplayer.setOnEvent(false);
    }

    public void RoomCollapseEvent(String EventResult) {
        processItemSubmitResult(EventResult);
        int idx = gameplayer.getMappingId().charAt(1) - '0';
        gameplayer.selectAllyByMid(idx);
        gameplayer.setOnEvent(false);
    }

    public void CampsiteEvent(String EventResult) {
        processItemSubmitResult(EventResult);
        int idx = gameplayer.getMappingId().charAt(1) - '0';
        gameplayer.selectAllyByMid(idx);
        gameplayer.setOnEvent(false);
    }

    public void MysteriousStatueEvent(String YesNo) {
        System.out.println("불가사의한 석상 이벤트 실행됨");

        if (YesNo.equalsIgnoreCase("yes")) {
            executeStatueEffect();
            int idx = gameplayer.getMappingId().charAt(1) - '0';
            gameplayer.selectAllyByMid(idx);
            gameplayer.setOnEvent(false);
        } else {
            System.out.println("당신은 불길한 기운을 외면한 채 발걸음을 옮깁니다.");
            gameplayer.setOnEvent(false);
        }
    }

    public void TreasureChestEvent(String EventResult) {
        processItemSubmitResult(EventResult);
        int idx = gameplayer.getMappingId().charAt(1) - '0';
        gameplayer.selectAllyByMid(idx);
        gameplayer.setOnEvent(false);
    }

    public void TrapActivatedEvent(String EventResult) {
        processItemSubmitResult(EventResult);
        int idx = gameplayer.getMappingId().charAt(1) - '0';
        gameplayer.selectAllyByMid(idx);
        gameplayer.setOnEvent(false);
    }


    private void processItemSubmitResult(String EventResult) {
        if (EventResult.equals("없음")) {
            System.out.println("아무일도 일어나지 않음");
            return;
        }
        System.out.println(EventResult + " 가 발생하였습니다.");

        if (EventResult.equals("보물") || EventResult.equals("파괴")) {
            handleTreasureResult(EventResult);
            return;
        }

        if (EventResult.equalsIgnoreCase("야영")) {
            healAllAlliesToFull();
        }

        if (EventResult.contains("피해")) {
            applyDamageFromEvent(EventResult);
        }
    }

    private void handleTreasureResult(String result) {
        Random rand = new Random();

        if (result.equals("보물")) {
            System.out.println("보물상자가 열렸습니다!.");
            for (int i = 0; i < 6; i++) {
                ItemDrop.runSingleDrop(gameplayer);
            }
        } else if (result.equals("파괴")) {
            if (rand.nextInt(100) < 60) {
                System.out.println("내용물이 손상되었습니다.");
                for (int i = 0; i < 3; i++) {
                    ItemDrop.runSingleDrop(gameplayer);
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
            List<AllyStatusManager> party = gameplayer.getAllyParty().getParty();

            if (EventResult.contains("랜덤")) {
                int index = rand.nextInt(party.size());
                var ally = party.get(index);
                int damage = ally.getBaseStats().getMaxHealth() * damagePercent / 100;
                int newHealth = Math.max(ally.getBaseStats().getHealth() - damage, 1);
                ally.getBaseStats().setHealth(newHealth);

                System.out.println("[랜덤 대상] " + ally.getName() + " 이(가) " + damage + " 피해를 입었습니다. 현재 체력: " + newHealth
                        + "/" + ally.getBaseStats().getMaxHealth());

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
        int action = rand.nextInt(3);

        List<AllyStatusManager> party = gameplayer.getAllyParty().getParty();

        switch (action) {
            case 0 -> {
                System.out.println("석상의 눈이 붉게 빛납니다... 어딘가에서 오래 묻힌 보물이 모습을 드러냅니다");
                for (int i = 0; i < 5; i++) {
                    ItemDrop.runSingleDrop(gameplayer);
                }
            }
            case 1 -> {
                int index = rand.nextInt(party.size());
                var ally = party.get(index);
                int heal = ally.getBaseStats().getMaxHealth() * 50 / 100;
                int newHealth = Math.min(ally.getBaseStats().getHealth() + heal, ally.getBaseStats().getMaxHealth());
                ally.getBaseStats().setHealth(newHealth);

                System.out.println(ally.getName() + " 이(가) 석상의 힘으로 체력을 " + heal + " 회복했습니다.");
            }
            case 2 -> {
                int index = rand.nextInt(party.size());
                var ally = party.get(index);
                int damage = ally.getBaseStats().getMaxHealth() * 30 / 100;
                int newHealth = Math.max(ally.getBaseStats().getHealth() - damage, 1);
                ally.getBaseStats().setHealth(newHealth);

                System.out.println(ally.getName() + " 이(가) 석상의 저주로 " + damage + " 피해를 입었습니다.");
            }
        }
    }

    private void healAllAlliesToFull() {
        List<AllyStatusManager> party = gameplayer.getAllyParty().getParty();
        for (AllyStatusManager ally : party) {
            int maxHealth = ally.getBaseStats().getMaxHealth();
            ally.getBaseStats().setHealth(maxHealth);
            System.out.println(ally.getName() + " 의 체력이 최대치인 " + maxHealth + "로 회복되었습니다.");
        }
    }
}
