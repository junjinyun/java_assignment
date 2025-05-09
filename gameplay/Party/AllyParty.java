package gameplay.Party;

import loaddata.Ally;
import loaddata.AllyManager;
import java.util.ArrayList;
import java.util.List;

public class AllyParty {
    private static List<AllyStatusManager> party;

    public AllyParty() {
        this.party = new ArrayList<>();
        List<Ally> loadedAllies = AllyManager.loadAlly(); // 아군 데이터를 JSON에서 로드

        // 예시: ally.json에서 전사, 기사, 도적, 마법사 선택 (index 0,1,2,5)
        int[] allyIndices = { 0, 1, 2, 5 };

        int idCounter = 1; // mappingId 순번 카운터

        // 각 아군에 대해 AllyStatusManager 객체 생성
        for (int i = 0; i < 4; i++) {
            Ally ally = loadedAllies.get(allyIndices[i]);

            // mappingId가 없을 경우 자동으로 부여
            if (ally.getMappingId() == null || ally.getMappingId().isBlank()) {
                ally.setMappingId("A" + idCounter++);
            }

            // AllyStatusManager 객체 생성 및 party에 추가
            AllyStatusManager manager = new AllyStatusManager(ally, ally.getMappingId());
            party.add(manager);  // 이제 AllyStatusManager 객체가 party에 추가됩니다.
        }
    }

    // 파티에 속한 모든 아군 객체를 반환
    public List<AllyStatusManager> getParty() {
        return party;
    }

    // 고유 값을 기반으로 아군 찾기
    public static AllyStatusManager getAllyByMappingID(String mappingId) {
        if (mappingId == null)
            return null;
        return party.stream()
            .filter(ally -> mappingId.equalsIgnoreCase(ally.getBaseStats().getMappingId())) // 대소문자 구분없이 비교
            .findFirst()
            .orElse(null);
    }

    // 파티 상태 출력
    public void printPartyStatus() {
        for (AllyStatusManager ally : party) {
            System.out.println(ally);
        }
    }
}