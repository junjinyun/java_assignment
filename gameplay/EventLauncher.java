package gameplay;

public class EventLauncher {

    public void MerchantEvent(String YesNo) {
        System.out.println("상점 - 거래 시도 여부: " + YesNo);
    }

    public void BlackMarketEvent(String item, String YesNo) {
        System.out.println("암상인 - 제출 아이템: " + item + ", 거래 여부: " + YesNo);
    }

    public void RoomCollapseEvent(String item, String YesNo) {
        System.out.println("방 붕괴 - 아이템 사용: " + item + ", 사용 여부: " + YesNo);
    }

    public void CampsiteEvent(String item, String YesNo) {
        System.out.println("야영지 - 아이템 사용: " + item + ", 휴식 여부: " + YesNo);
    }

    public void MysteriousStatueEvent(String YesNo) {
        System.out.println("불가사의한 석상 - 상호작용 여부: " + YesNo);
    }

    public void TreasureChestEvent(String item, String YesNo) {
        System.out.println("보물상자 - 사용한 아이템: " + item + ", 열기 여부: " + YesNo);
    }

    public void TrapActivatedEvent(String item, String YesNo) {
        System.out.println("함정 - 조치 아이템: " + item + ", 조치 여부: " + YesNo);
    }
}