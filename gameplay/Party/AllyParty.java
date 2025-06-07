package gameplay.Party;

import loaddata.Ally;
import loaddata.AllyManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllyParty {
    private static List<AllyStatusManager> party;

    public AllyParty() {
        this.party = new ArrayList<>();
    }

    // 모든 캐릭터 이름 출력 (아군 전체)
    public void printAllAllyNames() {
        List<Ally> loadedAllies = AllyManager.loadAlly(); // 모든 아군 데이터를 JSON에서 로드

        System.out.println("전체 아군 캐릭터 목록:");
        for (Ally ally : loadedAllies) {
            System.out.println("- " + ally.getName());
        }
    }

    // 선택된 인덱스 기준으로 아군 파티 생성
    public void createPartyFromIndices(List<Integer> indices) {
        List<Ally> loadedAllies = AllyManager.loadAlly();

        int idCounter = 1; // mappingId 순번 카운터

        for (int index : indices) {
            Ally ally = loadedAllies.get(index);

            // mappingId가 없을 경우 자동으로 부여
            if (ally.getMappingId() == null || ally.getMappingId().isBlank()) {
                ally.setMappingId("A" + idCounter++);
            }

            AllyStatusManager manager = new AllyStatusManager(ally, ally.getMappingId());
            party.add(manager);
        }
    }

    // 파티에 속한 모든 아군 객체를 반환
    public List<AllyStatusManager> getParty() {
        return party;
    }

    // 고유 값을 기반으로 아군 찾기
    public AllyStatusManager getAllyByMappingID(String mappingId) {
        if (mappingId == null)
            return null;
        return party.stream()
            .filter(ally -> mappingId.equalsIgnoreCase(ally.getBaseStats().getMappingId()))
            .findFirst()
            .orElse(null);
    }

    // 파티 상태 출력
    public void printPartyStatus() {
        for (AllyStatusManager ally : party) {
            System.out.println(ally);
        }
    }

    // -------------------------------------------------------
    // 새로 추가: 사용자 입력을 받아 4명 아군 중복 없이 선택하여 파티 생성
    // 입력 예: "0 2 1 3"
    // true 리턴: 파티 생성 성공, false: 실패 (재입력 필요)
    public boolean selectPartyFromInput(String input) {
        if (input == null || input.isBlank()) {
            System.out.println("입력이 비어 있습니다. 다시 입력해 주세요.");
            return false;
        }

        String[] parts = input.trim().split("\\s+");
        if (parts.length != 4) {
            System.out.println("아군은 4명을 선택해야 합니다. 다시 입력해 주세요.");
            return false;
        }

        Set<Integer> uniqueIndices = new HashSet<>();
        List<Integer> selectedIndices = new ArrayList<>();

        try {
            for (String part : parts) {
                int idx = Integer.parseInt(part);
                selectedIndices.add(idx);
                uniqueIndices.add(idx);
            }
        } catch (NumberFormatException e) {
            System.out.println("숫자 형태가 아닌 입력이 포함되어 있습니다. 다시 입력해 주세요.");
            return false;
        }

        if (uniqueIndices.size() != 4) {
            System.out.println("중복된 아군 인덱스가 있습니다. 다시 입력해 주세요.");
            return false;
        }

        List<Ally> loadedAllies = AllyManager.loadAlly();
        for (int idx : selectedIndices) {
            if (idx < 0 || idx >= loadedAllies.size()) {
                System.out.println("잘못된 인덱스가 포함되어 있습니다. 다시 입력해 주세요.");
                return false;
            }
        }

        // 성공 시 기존 파티 초기화 후 새로 생성
        party.clear();
        createPartyFromIndices(selectedIndices);
        return true;
    }
}
