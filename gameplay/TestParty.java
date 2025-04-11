package gameplay;


import data.AllyParty;
import gameplay.AllyStatusManager;

public class TestParty {
    public static void main(String[] args) {
        // 1. 아군 파티 구성
        AllyParty party = new AllyParty();

        // 2. 파티 전체 상태 출력
        System.out.println("===== 전체 파티 상태 출력 =====");
        party.printPartyStatus();

        // 3. 특정 캐릭터의 스탯 확인
        int targetPosition = 2; // 위치 2번: 기사
        AllyStatusManager ally = party.getAllyByPosition(targetPosition);

        // 4. 정보 출력
        System.out.println("\n===== 위치 " + targetPosition + " 캐릭터 정보 =====");
        System.out.println("이름: " + ally.getName());
        System.out.println("체력: " + ally.getBaseStats().getHealth());
        System.out.println("공격력: " + ally.getBaseStats().getAttack());
        System.out.println("방어력: " + ally.getBaseStats().getDefense());
    }
}
