package gameplay.Map;

import java.util.ArrayList;
import java.util.Random;

public class MakeUseMap {
    private final int SIZE = 8;
    private int[][] mapData = new int[SIZE][SIZE];
    private final Random random = new Random();

    public void mkMap() {
        // 기본 맵 데이터 초기화 (0=벽, 1=방 가능)
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                mapData[i][j] = 0;

        // 임의의 룸 생성 로직 (행/열 기준 연속 방 생성 알고리즘 적용)
        mkRoom();
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
}
