package gameplay.Party;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class SetSpeedAct {
    // 속도 설정: 아군과 적군 모두의 currentSpeed를 무작위로 설정
    public static void setSpeed(List<AllyStatusManager> all, List<EnemyStatusManager> ene) {
    	Object[] allies = all.toArray();
    	Object[] enemies = ene.toArray();
        Random random = new Random();

        for (Object ally : allies) {
            int min = ((AllyStatusManager) ally).getBaseStats().getMinSpeed();
            int max = ((AllyStatusManager) ally).getBaseStats().getMaxSpeed();
            int speed = random.nextInt(max - min + 1) + min;
            ((AllyStatusManager) ally).setCurrentSpeed(speed);
        }

        for (Object enemy : enemies) {
            int min = ((EnemyStatusManager) enemy).getBaseStats().getMinSpeed();
            int max = ((EnemyStatusManager) enemy).getBaseStats().getMaxSpeed();
            int speed = random.nextInt(max - min + 1) + min;
            ((EnemyStatusManager) enemy).setCurrentSpeed(speed);
        }
    }

    public static void setActionOrder(List<AllyStatusManager> all, List<EnemyStatusManager> ene) {
    	Object[] allies = all.toArray();
    	Object[] enemies = ene.toArray();
    	int totalUnits = allies.length + enemies.length;
        Object[] allUnits = new Object[totalUnits];

        System.arraycopy(allies, 0, allUnits, 0, allies.length);
        System.arraycopy(enemies, 0, allUnits, allies.length, enemies.length);

        Arrays.sort(allUnits, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                int speed1 = getSpeed(o1);
                int speed2 = getSpeed(o2);

                if (speed1 != speed2) {
                    return Integer.compare(speed2, speed1); // 속도 높은 순
                }

                boolean isAlly1 = o1 instanceof AllyStatusManager;
                boolean isAlly2 = o2 instanceof AllyStatusManager;

                if (isAlly1 != isAlly2) {
                    return isAlly1 ? -1 : 1; // 아군 우선
                }

                int pos1 = getPosition(o1);
                int pos2 = getPosition(o2);
                return Integer.compare(pos1, pos2); // 위치 낮은 순
            }

            private int getSpeed(Object obj) {
                if (obj instanceof AllyStatusManager) {
                    return ((AllyStatusManager) obj).getCurrentSpeed();
                } else {
                    return ((EnemyStatusManager) obj).getCurrentSpeed();
                }
            }

            private int getPosition(Object obj) {
                if (obj instanceof AllyStatusManager) {
                    return ((AllyStatusManager) obj).getPosition();
                } else {
                    return ((EnemyStatusManager) obj).getPosition();
                }
            }
        });

        for (int i = 0; i < allUnits.length; i++) {
            if (allUnits[i] instanceof AllyStatusManager) {
                ((AllyStatusManager) allUnits[i]).setActionOrder(i + 1);
            } else {
                ((EnemyStatusManager) allUnits[i]).setActionOrder(i + 1);
            }
        }
    }

}