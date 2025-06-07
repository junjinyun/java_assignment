package gameplay.Map;

import java.util.ArrayList;
import java.util.Random;

public class MakeUseMap {
    private final int SIZE = 8;
    private int[][] mapData = new int[SIZE][SIZE];
    private final Random random = new Random();

    public void mkMap() {
        do {
            // 1. 맵 초기화
            for (int i = 0; i < SIZE; i++)
                for (int j = 0; j < SIZE; j++)
                    mapData[i][j] = 0;

            // 2. 룸 생성
            mkRoom();

        } while (!isFullyConnected());  // 3. 연결되지 않으면 다시 생성
    }

    public void mkRoom() {
        ArrayList<Integer> createdRow = new ArrayList<>();
        ArrayList<Integer> createdCol = new ArrayList<>();

        for (int i = 1; i <= 6; i++) {
            int selectedRowOrCol = random.nextInt(SIZE);
            int startLocation = random.nextInt(4);  // 0~3 사이 시작 위치

            if (i % 2 == 1) { // 홀수 i: 행 기준 방 생성
                while (createdRow.contains(selectedRowOrCol)) {
                    selectedRowOrCol = random.nextInt(SIZE);
                }
                createdRow.add(selectedRowOrCol);

                while (startLocation < SIZE) {
                    mapData[selectedRowOrCol][startLocation] = 1;
                    startLocation++;
                }
            } else { // 짝수 i: 열 기준 방 생성
                while (createdCol.contains(selectedRowOrCol)) {
                    selectedRowOrCol = random.nextInt(SIZE);
                }
                createdCol.add(selectedRowOrCol);

                while (startLocation < SIZE) {
                    mapData[startLocation][selectedRowOrCol] = 1;
                    startLocation++;
                }
            }
        }
    }

    public int[][] getMapdata() {
        return mapData;
    }
    private boolean isFullyConnected() {
        boolean[][] visited = new boolean[SIZE][SIZE];
        int totalRooms = 0;
        int connectedRooms = 0;

        // 전체 방 개수 확인 및 시작점 찾기
        int startX = -1, startY = -1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (mapData[i][j] == 1) {
                    totalRooms++;
                    if (startX == -1) {
                        startX = i;
                        startY = j;
                    }
                }
            }
        }

        if (totalRooms == 0) return false;

        // DFS or BFS 시작
        connectedRooms = dfs(startX, startY, visited);

        return connectedRooms == totalRooms;
    }

    private int dfs(int x, int y, boolean[][] visited) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) return 0;
        if (mapData[x][y] != 1 || visited[x][y]) return 0;

        visited[x][y] = true;
        int count = 1;

        // 상하좌우 탐색
        count += dfs(x + 1, y, visited);
        count += dfs(x - 1, y, visited);
        count += dfs(x, y + 1, visited);
        count += dfs(x, y - 1, visited);

        return count;
    }
}
