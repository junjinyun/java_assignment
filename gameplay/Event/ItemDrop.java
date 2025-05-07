package gameplay.Event;

import loaddata.CashableItem;
import loaddata.CashableItemManager;
import loaddata.UsableItem;
import loaddata.UsableItemManager;

import java.util.List;
import java.util.Random;

public class ItemDrop {
    private static final Random random = new Random();
    private static final double CASHABLE_DROP_RATE = 0.85; // 환금성 아이템 드롭 확률
    private static final double USABLE_DROP_RATE = 0.70;   // 소비 아이템 드롭 확률

    private static List<CashableItem> cashableItems;
    private static List<UsableItem> usableItems;

    // 클래스 초기화 시 아이템 데이터 로드
    static {
        cashableItems = CashableItemManager.loadCashableItem();
        usableItems = UsableItemManager.loadUsableItem();
    }

    // 아이템 드롭 1회 실행 (단 1개의 아이템만 드롭 시도)
    public static void runSingleDrop() {
        boolean dropCashable = random.nextBoolean(); // 50:50 확률

        if (dropCashable) {
            CashableItem item = getRandomCashableItem();
            if (random.nextDouble() < CASHABLE_DROP_RATE) {
                int amount = getRandomAmount(item.getMinDropAmount(), item.getMaxDropAmount());
                if (amount > 0) {
                    System.out.println("▶ [보물 드랍] " + item.getName() + " x" + amount);
                }
            }
        } else {
            UsableItem item = getRandomUsableItem();
            if (random.nextDouble() < USABLE_DROP_RATE) {
                int amount = getRandomAmount(item.getMinDropAmount(), item.getMaxDropAmount());
                if (amount > 0) {
                    System.out.println("▶ [소비 드랍] " + item.getName() + " x" + amount);
                }
            }
        }
    }

    // 지정 범위에서 드롭 수량 랜덤 선택
    private static int getRandomAmount(int min, int max) {
        if (max < min) return min;
        return random.nextInt(max - min + 1) + min;
    }

    // 환금성 아이템 중 하나를 무작위 선택
    private static CashableItem getRandomCashableItem() {
        return cashableItems.get(random.nextInt(cashableItems.size()));
    }

    // 소비 아이템 중 하나를 무작위 선택
    private static UsableItem getRandomUsableItem() {
        return usableItems.get(random.nextInt(usableItems.size()));
    }
}