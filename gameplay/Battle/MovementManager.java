package gameplay.Battle;

import java.util.Arrays;
import java.util.List;

import gameplay.GamePlayer;
import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyStatusManager;

public class MovementManager {

    private final GamePlayer gamePlayer;

    public MovementManager(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public void moveCharacter(String mappingId, int moveAmount, boolean isForward) {
        List<Integer> positionOrder = Arrays.asList(1, 2, 3, 4);
        if (mappingId.contains("A")) {
            // 아군 처리
            List<AllyStatusManager> party = Arrays.asList(
                    gamePlayer.getAllyParty().getAllyByMappingID("A1"),
                    gamePlayer.getAllyParty().getAllyByMappingID("A2"),
                    gamePlayer.getAllyParty().getAllyByMappingID("A3"),
                    gamePlayer.getAllyParty().getAllyByMappingID("A4")
            );
            moveCharacterInParty(party, positionOrder, mappingId, moveAmount, isForward);
            printPartyPositions("A");
        } else if (mappingId.contains("E")) {
            // 적군 처리
            List<EnemyStatusManager> party = Arrays.asList(
                    gamePlayer.getEnemyParty().getEnemyByMappingID("E1"),
                    gamePlayer.getEnemyParty().getEnemyByMappingID("E2"),
                    gamePlayer.getEnemyParty().getEnemyByMappingID("E3"),
                    gamePlayer.getEnemyParty().getEnemyByMappingID("E4")
            );
            moveCharacterInParty(party, positionOrder, mappingId, moveAmount, isForward);
            printPartyPositions("E");
        }
    }

    // 제네릭 메서드로 위치 이동 로직 처리
    private <T> void moveCharacterInParty(List<T> party, List<Integer> positionOrder,
                                          String mappingId, int moveAmount, boolean isForward) {
        T character = party.stream()
                .filter(p -> getMappingId(p).equals(mappingId))
                .findFirst()
                .orElse(null);

        if (character == null) return;

        int currentPosition = getPosition(character);
        int currentIndex = positionOrder.indexOf(currentPosition);

        if (currentIndex == -1) return;

        int newIndex = isForward ? currentIndex - moveAmount : currentIndex + moveAmount;
        newIndex = Math.max(0, Math.min(newIndex, positionOrder.size() - 1));
        int newPosition = positionOrder.get(newIndex);

        if (currentPosition == newPosition) return;

        // 이동 경로에 있는 캐릭터들을 이동 방향대로 밀기
        shiftPositionsTowards(party, positionOrder, currentIndex, newIndex, isForward);

        setPosition(character, newPosition);
    }

    private <T> void shiftPositionsTowards(List<T> party, List<Integer> order, int from, int to, boolean forward) {
        if (from == to) return;

        if (forward) {
            // from > to 인 경우, 이동 경로 앞쪽 캐릭터들을 뒤로 밀기
            for (int i = from - 1; i >= to; i--) {
                int currPos = order.get(i);
                int nextPos = order.get(i + 1);
                party.stream()
                     .filter(p -> getPosition(p) == currPos)
                     .findFirst()
                     .ifPresent(p -> setPosition(p, nextPos));
            }
        } else {
            // from < to 인 경우, 이동 경로 뒤쪽 캐릭터들을 앞으로 당기기
            for (int i = from + 1; i <= to; i++) {
                int currPos = order.get(i);
                int prevPos = order.get(i - 1);
                party.stream()
                     .filter(p -> getPosition(p) == currPos)
                     .findFirst()
                     .ifPresent(p -> setPosition(p, prevPos));
            }
        }
    }

    private void printPartyPositions(String faction) {
        if ("A".equals(faction)) {
            System.out.println("=== 아군 위치 ===");
            List<AllyStatusManager> party = Arrays.asList(
                    gamePlayer.getAllyParty().getAllyByMappingID("A1"),
                    gamePlayer.getAllyParty().getAllyByMappingID("A2"),
                    gamePlayer.getAllyParty().getAllyByMappingID("A3"),
                    gamePlayer.getAllyParty().getAllyByMappingID("A4")
            );
            party.forEach(ally -> System.out.println(ally.getMappingId() + ": 위치 " + ally.getPosition()));
        } else if ("E".equals(faction)) {
            System.out.println("=== 적군 위치 ===");
            List<EnemyStatusManager> party = Arrays.asList(
                    gamePlayer.getEnemyParty().getEnemyByMappingID("E1"),
                    gamePlayer.getEnemyParty().getEnemyByMappingID("E2"),
                    gamePlayer.getEnemyParty().getEnemyByMappingID("E3"),
                    gamePlayer.getEnemyParty().getEnemyByMappingID("E4")
            );
            party.forEach(enemy -> System.out.println(enemy.getMappingId() + ": 위치 " + enemy.getPosition()));
        }
    }

    // 공통 동작 추출 (리플렉션 없이 타입별 분기)
    private String getMappingId(Object obj) {
        if (obj instanceof AllyStatusManager) {
            return ((AllyStatusManager) obj).getMappingId();
        } else if (obj instanceof EnemyStatusManager) {
            return ((EnemyStatusManager) obj).getMappingId();
        }
        return "";
    }

    private int getPosition(Object obj) {
        if (obj instanceof AllyStatusManager) {
            return ((AllyStatusManager) obj).getPosition();
        } else if (obj instanceof EnemyStatusManager) {
            return ((EnemyStatusManager) obj).getPosition();
        }
        return -1;
    }

    private void setPosition(Object obj, int position) {
        if (obj instanceof AllyStatusManager) {
            ((AllyStatusManager) obj).setPosition(position);
        } else if (obj instanceof EnemyStatusManager) {
            ((EnemyStatusManager) obj).setPosition(position);
        }
    }
}
