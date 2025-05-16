package gameplay.Map;

import java.util.Random;

public class EntryPointSelector {

    public static int[][] selectEntryPoint(int[][] mapData) {
        int limit = 2;
        int increment = 0;

        Random random = new Random();
        int type = random.nextInt(4);  // 0~3 방향 선택

        System.out.println("탐색 방향(type): " + type);

        boolean startLogged = false; // 시작 위치 한 번만 출력

        find: for (int q = 0; q < 14; q++) {
            limit = 2;
            increment += 1;
            limit += increment;

            if (type < 2) {
                for (int i = 0; i < 8; i++) {
                    limit -= 1;
                    if (limit < 8) {
                        for (int j = 0; j < limit; j++) {
                            switch (type) {
                                case 0:
                                    if (!startLogged) {
                                        System.out.println("탐색 시작 위치: (" + i + ", " + j + ")");
                                        startLogged = true;
                                    }
                                    if (mapData[i][j] == 1) {
                                        mapData[i][j] = 3;
                                        System.out.println("선택된 진입점: (" + i + ", " + j + ")");
                                        break find;
                                    }
                                    break;
                                case 1:
                                    if (!startLogged) {
                                        System.out.println("탐색 시작 위치: (" + i + ", " + (7 - j) + ")");
                                        startLogged = true;
                                    }
                                    if (mapData[i][7 - j] == 1) {
                                        mapData[i][7 - j] = 3;
                                        System.out.println("선택된 진입점: (" + i + ", " + (7 - j) + ")");
                                        break find;
                                    }
                                    break;
                            }
                        }
                    } else {
                        for (int j = 0; j < 8; j++) {
                            switch (type) {
                                case 0:
                                    if (!startLogged) {
                                        System.out.println("탐색 시작 위치: (" + i + ", " + j + ")");
                                        startLogged = true;
                                    }
                                    if (mapData[i][j] == 1) {
                                        mapData[i][j] = 3;
                                        System.out.println("선택된 진입점: (" + i + ", " + j + ")");
                                        break find;
                                    }
                                    break;
                                case 1:
                                    if (!startLogged) {
                                        System.out.println("탐색 시작 위치: (" + i + ", " + (7 - j) + ")");
                                        startLogged = true;
                                    }
                                    if (mapData[i][7 - j] == 1) {
                                        mapData[i][7 - j] = 3;
                                        System.out.println("선택된 진입점: (" + i + ", " + (7 - j) + ")");
                                        break find;
                                    }
                                    break;
                            }
                        }
                    }
                }
            } else {
                for (int i = 7; i >= 0; i--) {
                    limit -= 1;
                    if (limit < 8) {
                        for (int j = 0; j < limit; j++) {
                            switch (type) {
                                case 2:
                                    if (!startLogged) {
                                        System.out.println("탐색 시작 위치: (" + i + ", " + j + ")");
                                        startLogged = true;
                                    }
                                    if (mapData[i][j] == 1) {
                                        mapData[i][j] = 3;
                                        System.out.println("선택된 진입점: (" + i + ", " + j + ")");
                                        break find;
                                    }
                                    break;
                                case 3:
                                    if (!startLogged) {
                                        System.out.println("탐색 시작 위치: (" + i + ", " + (7 - j) + ")");
                                        startLogged = true;
                                    }
                                    if (mapData[i][7 - j] == 1) {
                                        mapData[i][7 - j] = 3;
                                        System.out.println("선택된 진입점: (" + i + ", " + (7 - j) + ")");
                                        break find;
                                    }
                                    break;
                            }
                        }
                    } else {
                        for (int j = 0; j < 8; j++) {
                            switch (type) {
                                case 2:
                                    if (!startLogged) {
                                        System.out.println("탐색 시작 위치: (" + i + ", " + j + ")");
                                        startLogged = true;
                                    }
                                    if (mapData[i][j] == 1) {
                                        mapData[i][j] = 3;
                                        System.out.println("선택된 진입점: (" + i + ", " + j + ")");
                                        break find;
                                    }
                                    break;
                                case 3:
                                    if (!startLogged) {
                                        System.out.println("탐색 시작 위치: (" + i + ", " + (7 - j) + ")");
                                        startLogged = true;
                                    }
                                    if (mapData[i][7 - j] == 1) {
                                        mapData[i][7 - j] = 3;
                                        System.out.println("선택된 진입점: (" + i + ", " + (7 - j) + ")");
                                        break find;
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        }

        return mapData;
    }
}
