package gameplay;

import dungeon.Ally;
import dungeon.GetAllyJson;
import gameplay.AllyStatusManager;

import java.util.Collections;
import java.util.List;
import java.util.Random;

// 4명의 아군을 중복 없이 랜덤하게 선택하여 관리하는 클래스
@SuppressWarnings("unused")
public class AllyParty {
    public static AllyStatusManager[] party = new AllyStatusManager[4];

    public static void initializeParty() {
        List<Ally> allyList = GetAllyJson.loadAlly(); // ally.json에서 불러온 전체 목록

        if (allyList.size() < 4) {
            throw new IllegalStateException("아군 데이터가 4명 미만입니다. 최소 4명이 필요합니다.");
        }

        // 리스트를 섞고 앞에서 4명을 중복 없이 선택
        Collections.shuffle(allyList);

        for (int i = 0; i < 4; i++) {
            Ally selected = allyList.get(i);
            AllyStatusManager asm = new AllyStatusManager(selected, i + 1);

            // 랜덤 속도 설정
            int speedRange = selected.getMaxSpeed() - selected.getMinSpeed() + 1;
            int currentSpeed = selected.getMinSpeed() + new Random().nextInt(speedRange);
            asm.setCurrentSpeed(currentSpeed);

            // 행동 순서 설정 (기본은 위치 기반)
            asm.setActionOrder(i + 1);

            party[i] = asm;
        }
    }
}