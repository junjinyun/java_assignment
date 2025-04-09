package gameplay;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class SetSpeedAct {
    public static void setSpeed() {
        Random random = new Random(); // Random 객체는 한 번만 생성
        for (int i = 0; i < 4; i++) {
            SelectAlly.Ally[i].CurrentSpeed = random.nextInt(
                    SelectAlly.Ally[i].MaxSpeed + 1 - SelectAlly.Ally[i].MinSpeed) + SelectAlly.Ally[i].MinSpeed;
            SelectEnemy.Enemy[i].CurrentSpeed = random
                    .nextInt(SelectEnemy.Enemy[i].MaxSpeed - SelectEnemy.Enemy[i].MinSpeed)
                    + SelectEnemy.Enemy[i].MinSpeed;
        }
    }

    public static void setActionOrder() {
        // SelectAlly와 SelectEnemy의 길이를 확인하여 속도 순위를 정하기 위한 배열 크기 할당
        int totalLength = SelectAlly.Ally.length + SelectEnemy.Enemy.length;
        Integer[] speedRank = new Integer[totalLength]; // 각 캐릭터의 속도를 저장하는 배열

        // 1. 속도값을 speedRank 배열에 할당하고, 행동 순서를 초기화
        for (int i = 0; i < SelectAlly.Ally.length; i++) {
            speedRank[i] = SelectAlly.Ally[i].CurrentSpeed;
            SelectAlly.Ally[i].ActionOrder = -1; // 초기화
        }
        for (int i = 0; i < SelectEnemy.Enemy.length; i++) {
            speedRank[SelectAlly.Ally.length + i] = SelectEnemy.Enemy[i].CurrentSpeed;
            SelectEnemy.Enemy[i].ActionOrder = -1; // 초기화
        }

        // 2. 속도를 내림차순으로 정렬
        Arrays.sort(speedRank, Collections.reverseOrder());

        // 3. 정렬된 속도를 기반으로 각 캐릭터의 행동 순서를 할당
        int Order = 1; // 순서는 1부터 시작
        for (int i = 0; i < totalLength; i++) {
            for (int j = 0; j < SelectAlly.Ally.length; j++) {
                if (speedRank[i] == SelectAlly.Ally[j].CurrentSpeed && SelectAlly.Ally[j].ActionOrder == -1) {
                    SelectAlly.Ally[j].ActionOrder = Order++;
                }
            }
            for (int j = 0; j < SelectEnemy.Enemy.length; j++) {
                if (speedRank[i] == SelectEnemy.Enemy[j].CurrentSpeed && SelectEnemy.Enemy[j].ActionOrder == -1) {
                    SelectEnemy.Enemy[j].ActionOrder = Order++;
                }
            }
        }
    }
}