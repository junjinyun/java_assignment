package gameplay.Event;

import gameplay.Party.AllyParty;
import gameplay.UI.Bottom.Left.LogPanel;
import loaddata.EventManager;
import loaddata.MapEvent;
import gameplay.GamePlayer;
import gameplay.Item.InventoryItem;
import gameplay.Item.Item;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;

public class LogEventManager implements YesNoObserver {
    private final GamePlayer gamePlayer;
    private final AllyParty allyParty;
    private final List<MapEvent> eventList = EventManager.loadMapEvents();

    private enum State { IDLE, WAITING_YESNO, WAITING_ITEM_CONFIRM }
    private State currentState = State.IDLE;

    private MapEvent currentEvent;
    private String selectedItemName = "";
    private String yesOrNo = "";  // GamePlayer에서 실제로 받아오는 변수로 대체

    public LogEventManager(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
        this.allyParty = gamePlayer.getAllyParty();
        this.gamePlayer.addYesNoObserver(this);
    }

    public void startRandomEvent() {
        Random random = new Random();
        int index = random.nextInt(eventList.size());
        currentEvent = eventList.get(index);

        String name = currentEvent.getName();
        String info = currentEvent.getInformation();
        String eventType = currentEvent.getEventType().trim();

        System.out.println("🌀 이벤트 발생: " + name);
        System.out.println(info);

        if (eventType.equalsIgnoreCase("상호작용")) {
            System.out.println("대소문자 구분 없이 'yes' 입력 시 상호작용, 'no'는 무시됩니다.");
            currentState = State.WAITING_YESNO;
        } else if (eventType.equalsIgnoreCase("아이템제출")) {
            String[] parts = currentEvent.getRequestedItem().split(", ");
            String requiredItem = parts[0];
            System.out.println("📦 아이템 제출 이벤트입니다.");
            System.out.println("필요 아이템: " + requiredItem);

            boolean hasItem = hasItemInInventory(requiredItem);
            if (!hasItem) {
                System.out.println("❌ 필요한 아이템이 없습니다.");
                launchItemEvent(parts[2]);  // 실패 처리 및 실행
                currentState = State.IDLE;
            } else {
                System.out.println("✅ 아이템이 존재합니다. 제출하시겠습니까? (yes/no)");
                currentState = State.WAITING_ITEM_CONFIRM;
            }
        }
    }

    private void handleYesNo(String input) {
        yesOrNo = input.equalsIgnoreCase("yes") ? "yes" : "no";
        System.out.println("선택: " + yesOrNo);

        launchInteractionEvent(yesOrNo);
        currentState = State.IDLE;
    }

    private void handleItemConfirmation(String input) {
        String[] parts = currentEvent.getRequestedItem().split(", ");
        String requiredItem = parts[0];
        String success = parts[1];
        String fail = parts[2];

        if (input.equalsIgnoreCase("yes")) {
            System.out.println("✅ 아이템 '" + requiredItem + "'을 제출하고 이벤트를 진행합니다.");

            if (requiredItem.startsWith("돈")) {
                int requiredGold = parseGoldAmount(requiredItem);
                int currentGold = gamePlayer.getPlayer().getGold();

                if (currentGold >= requiredGold) {
                    gamePlayer.getPlayer().setGold(currentGold - requiredGold);
                    System.out.println("💰 골드 " + requiredGold + " 차감 완료. 남은 골드: " + gamePlayer.getPlayer().getGold());
                    launchItemEvent(success);
                } else {
                    System.out.println("❌ 골드가 부족하여 이벤트 실패 처리됩니다.");
                    launchItemEvent(fail);
                }
            } else {
                InventoryItem invItem = findInventoryItemByName(requiredItem);
                if (invItem != null) {
                    gamePlayer.getPlayer().removeFromInventory(invItem.getCategory(), invItem.getItemId(), 1);
                    launchItemEvent(success);
                } else {
                    System.out.println("⚠️ 인벤토리에서 아이템을 찾지 못해 제출 처리 실패");
                    launchItemEvent(fail);
                }
            }
        } else {
            System.out.println("❌ 제출 취소. 이벤트 실패 처리됩니다.");
            launchItemEvent(fail);
        }

        currentState = State.IDLE;
    }

    private void launchInteractionEvent(String yesOrNo) {
        String command = currentEvent.getInternalId();  // 예: "CampsiteEvent"
        System.out.println("🧩 호출할 인스턴스 메서드: " + command);

        try {
            EventLauncher launcher = gamePlayer.getEventLauncher(); // ✅ GamePlayer에서 가져옴
            Method method = EventLauncher.class.getMethod(command, String.class);
            method.invoke(launcher, yesOrNo);
        } catch (Exception e) {
            System.out.println("[오류] 이벤트 실행 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void launchItemEvent(String result) {
        try {
            EventLauncher launcher = gamePlayer.getEventLauncher(); // ✅ GamePlayer에서 가져옴
            Method method = EventLauncher.class.getMethod(currentEvent.getInternalId(), String.class);
            method.invoke(launcher, result);
        } catch (Exception e) {
            System.out.println("[오류] 아이템 이벤트 실행 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean hasItemInInventory(String itemName) {
        if (gamePlayer == null || gamePlayer.getPlayer() == null) return false;

        if (itemName.startsWith("돈")) {
            int requiredGold = parseGoldAmount(itemName);
            int playerGold = gamePlayer.getPlayer().getGold();
            return playerGold >= requiredGold;
        }

        List<InventoryItem> inventory = gamePlayer.getPlayer().getInventory();
        if (inventory == null || inventory.isEmpty()) return false;

        for (InventoryItem inventoryItem : inventory) {
            Item detailedItem = findItemInfo(inventoryItem.getCategory(), inventoryItem.getItemId());
            if (detailedItem != null && detailedItem.getName().equalsIgnoreCase(itemName)) {
                if (inventoryItem.getQuantity() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private Item findItemInfo(String category, int itemId) {
        Map<String, String> categoryFileMap = Map.of(
            "gimmick_item", "src/data/gimmick_item.json",
            "usable_item", "src/data/usable_item.json",
            "cashable_item", "src/data/cashable_item.json"
        );

        String path = categoryFileMap.get(category);
        if (path == null)
            return null;

        try (FileReader reader = new FileReader(path)) {
            Gson gson = new Gson();
            Map<String, List<Item>> data = gson.fromJson(reader, new com.google.gson.reflect.TypeToken<Map<String, List<Item>>>(){}.getType());
            List<Item> items = data.values().iterator().next();

            for (Item item : items) {
                if (item.getId() == itemId) {
                    return item;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onYesNoInput(String yesOrNo) {
        switch (currentState) {
            case WAITING_YESNO:
                handleYesNo(yesOrNo);
                break;
            case WAITING_ITEM_CONFIRM:
                handleItemConfirmation(yesOrNo);
                break;
        }
    }

    private InventoryItem findInventoryItemByName(String itemName) {
        List<InventoryItem> inventory = gamePlayer.getPlayer().getInventory();

        for (InventoryItem inventoryItem : inventory) {
            Item detailedItem = findItemInfo(inventoryItem.getCategory(), inventoryItem.getItemId());
            if (detailedItem != null && detailedItem.getName().equalsIgnoreCase(itemName)) {
                return inventoryItem;
            }
        }
        return null;
    }

    private int parseGoldAmount(String itemStr) {
        if (itemStr == null) return 0;

        int start = itemStr.indexOf('(');
        int end = itemStr.indexOf(')');

        if (start != -1 && end != -1 && end > start) {
            String numStr = itemStr.substring(start + 1, end);
            try {
                return Integer.parseInt(numStr.trim());
            } catch (NumberFormatException e) {
                System.out.println("골드 요구량 파싱 실패: " + numStr);
            }
        }
        return 0;
    }
}
