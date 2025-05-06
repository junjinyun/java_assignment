package gameplay.Party;

import loaddata.Ally;
import loaddata.AllyManager;
import java.util.ArrayList;
import java.util.List;

public class AllyParty {
    private List<AllyStatusManager> party;

    public AllyParty() {
        this.party = new ArrayList<>();
        List<Ally> loadedAllies = AllyManager.loadAlly(); // 알리 데이터를 JSON에서 로드

        // 예시: ally.json에서 전사, 기사, 도적, 마법사 선택 (index 0,1,2,5)
        int[] allyIndices = {0, 1, 2, 5};

        // 각 아군에 대해 AllyStatusManager 객체 생성
        for (int i = 0; i < 4; i++) {
            Ally ally = loadedAllies.get(allyIndices[i]);
            AllyStatusManager manager = new AllyStatusManager(ally, i + 1); // 위치 1~4
            party.add(manager);
        }
    }

    // 파티에 속한 모든 아군 객체를 반환
    public List<AllyStatusManager> getParty() {
        return party;
    }

    // 위치를 기반으로 아군 찾기
    public AllyStatusManager getAllyByPosition(int position) {
        return party.stream()
            .filter(ally -> ally.getPosition() == position)
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