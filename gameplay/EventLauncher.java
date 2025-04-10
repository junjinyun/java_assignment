public class EventLauncher{
    public void MerchantEvent(String YesNo){
        System.out.println("상점");
    }
    public void BlackMarketEvent(String item, String YesNo){
        System.out.println("암상인");
    }
    public void RoomCollapseEvent(String item, String YesNo){
        System.out.println("방 붕괴");
    }
    public void CampsiteEvent(String item, String YesNo){
        System.out.println("야영지");
    }
    public void MysteriousStatueEvent(String YesNo){
        System.out.println("알수없는 석상");
    }
    public void TreasureChestEvent(String item, String YesNo){
        System.out.println("보물상자!");
    }
    public void TrapActivatedEvent(String item, String YesNo){
        System.out.println("함정발동");
    }
}