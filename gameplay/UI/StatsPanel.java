package gameplay.UI;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class StatsPanel extends JPanel {
    private final Map<String, JLabel> statLabels = new LinkedHashMap<>();

    public StatsPanel() {
        setLayout(new GridLayout(0, 2)); // 2열로 구성된 능력치 그리드
        setBorder(BorderFactory.createTitledBorder("캐릭터 스탯"));

        // 기본 스탯 항목 초기화 (원하는 항목 추가 가능)
        addStat("HP");
        addStat("MP");
        addStat("공격력");
        addStat("방어력");
        addStat("속도");
        addStat("치명타 확률");

        showDefaultStats(); // 기본값 세팅
    }

    private void addStat(String name) {
        JLabel label = new JLabel(name + ": ");
        JLabel value = new JLabel("0"); // 초기값
        statLabels.put(name, value);
        add(label);
        add(value);
    }

    public void updateStat(String name, int value) {
        JLabel label = statLabels.get(name);
        if (label != null) {
            label.setText(String.valueOf(value));
        }
    }

    public void showDefaultStats() {
        for (String key : statLabels.keySet()) {
            statLabels.get(key).setText("0");
        }
    }
}
