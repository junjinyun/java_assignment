package gameplay.UI.Bottom.Left;

import gameplay.GamePlayer;
import gameplay.Party.AllyStatusManager;
import gameplay.UI.UIObserver;
import loaddata.Ally;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

public class StatsPanel extends JPanel implements UIObserver {
    private final Map<String, JLabel> statLabels = new LinkedHashMap<>();
    private GamePlayer gamePlayer;

    public StatsPanel(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;

        setLayout(new GridLayout(0, 2)); // 2열로 구성된 능력치 그리드
        setBorder(BorderFactory.createTitledBorder("캐릭터 스탯"));

        // 기본 스탯 항목 초기화
        addStat("이름");
        addStat("체력");
        addStat("최대체력");
        addStat("공격력");
        addStat("방어력");
        addStat("최소 속도");
        addStat("최대 속도");
        addStat("속도");
        addStat("위치");

        showDefaultStats(); // 초기값 세팅

        // 옵저버로 등록
        gamePlayer.addObserver(this);
        
        // 초기 값 설정
        updateStatsFromGamePlayer();
    }

    private void addStat(String name) {
        JLabel label = new JLabel(name + ": ");
        JLabel value = new JLabel("0"); // 초기값
        statLabels.put(name, value);
        add(label);
        add(value);
    }

    public void updateStat(String name, String value) {
        JLabel label = statLabels.get(name);
        if (label != null) {
            label.setText(value);
        }
    }

    public void updateStat(String name, int value) {
        updateStat(name, String.valueOf(value));
    }

    public void showDefaultStats() {
        for (String key : statLabels.keySet()) {
            JLabel label = statLabels.get(key);
            if (label != null) {
                label.setText("로드 되지 않음");
            }
        }
    }

    private void updateStatsFromGamePlayer() {
        if (gamePlayer == null || gamePlayer.getAllyParty() == null)
            return;

        AllyStatusManager statusManager = gamePlayer.getAllyParty().getAllyByMappingID(gamePlayer.getMappingId());
        if (statusManager == null)
            return;

        Ally baseStats = statusManager.getBaseStats();
        if (baseStats == null)
            return;

        updateStat("이름", baseStats.getName());
        updateStat("체력", baseStats.getHealth());
        updateStat("최대체력", baseStats.getMaxHealth());
        updateStat("공격력", baseStats.getAttack());
        updateStat("방어력", baseStats.getDefense());
        updateStat("최소 속도", baseStats.getMinSpeed());
        updateStat("최대 속도", baseStats.getMaxSpeed());
        updateStat("속도", statusManager.getCurrentSpeed());
        updateStat("위치", statusManager.getPosition());
    }

    @Override
    public void update(GamePlayer gamePlayer) {
        updateStatsFromGamePlayer();
    }
}
