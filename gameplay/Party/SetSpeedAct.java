package gameplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class SetSpeedAct {

    // 속도 설정: 아군과 적군 모두의 currentSpeed를 무작위로 설정
    public static void setSpeed(AllyStatusManager[] allies, EnemyStatusManager[] enemies) {
        Random random = new Random();

        for (AllyStatusManager ally : allies) {
            int min = ally.getBaseStats().getMinSpeed();
            int max = ally.getBaseStats().getMaxSpeed();
            int speed = random.nextInt(max - min + 1) + min;
            ally.setCurrentSpeed(speed);
        }

        for (EnemyStatusManager enemy : enemies) {
            int min = enemy.getBaseStats().getMinSpeed();
            int max = enemy.getBaseStats().getMaxSpeed();
            int speed = random.nextInt(max - min + 1) + min;
            enemy.setCurrentSpeed(speed);
        }
    }

    // 행동 순서 설정 (속도가 높은 순서대로 1부터 할당)
    public static void setActionOrder(AllyStatusManager[] allies, EnemyStatusManager[] enemies) {
        List<CharacterWrapper> allCharacters = new ArrayList<>();

        for (AllyStatusManager ally : allies) {
            allCharacters.add(new CharacterWrapper(ally.getCurrentSpeed(), ally));
        }

        for (EnemyStatusManager enemy : enemies) {
            allCharacters.add(new CharacterWrapper(enemy.getCurrentSpeed(), enemy));
        }

        Collections.sort(allCharacters, Comparator.comparingInt(CharacterWrapper::getSpeed).reversed());

        int order = 1;
        for (CharacterWrapper wrapper : allCharacters) {
            wrapper.setActionOrder(order++);
        }
    }

    // 내부 클래스: 아군/적군을 같은 리스트에서 처리하기 위한 래퍼
    private static class CharacterWrapper {
        private int speed;
        private Object character;

        public CharacterWrapper(int speed, Object character) {
            this.speed = speed;
            this.character = character;
        }

        public int getSpeed() {
            return speed;
        }

        public void setActionOrder(int order) {
            if (character instanceof AllyStatusManager ally) {
                ally.setActionOrder(order);
            } else if (character instanceof EnemyStatusManager enemy) {
                enemy.setActionOrder(order);
            }
        }
    }
}