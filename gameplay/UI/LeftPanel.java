package UI;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {

    public LeftPanel(BattleAreaPanel battlePanel) {
        setPreferredSize(new Dimension(920, 0));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("스킬 및 아군 정보"));

        // 스킬 버튼 패널
        JPanel skillPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 0));
        ButtonGroup skillGroup = new ButtonGroup();

        ImageIcon activeIcon = new ImageIcon("src/image/Interface/green.png");
        ImageIcon inactiveIcon = new ImageIcon("src/image/Interface/red.png");

        for (int i = 0; i < 6; i++) {
            JRadioButton skillButton = new JRadioButton("S" + (i + 1));
            skillButton.setPreferredSize(new Dimension(100, 100));
            skillButton.setIcon(inactiveIcon);

            skillButton.addActionListener(e -> {
                if (skillButton.isSelected()) {
                    skillButton.setIcon(activeIcon);
                } else {
                    skillButton.setIcon(inactiveIcon);
                }

                for (Component comp : skillPanel.getComponents()) {
                    if (comp instanceof JRadioButton btn && btn != skillButton) {
                        btn.setIcon(inactiveIcon);
                        btn.setSelected(false);
                    }
                }
            });

            skillGroup.add(skillButton);
            skillPanel.add(skillButton);
        }

        JPanel skillGroupPanel = new JPanel(new BorderLayout());
        skillGroupPanel.setBorder(BorderFactory.createTitledBorder("스킬 그룹"));
        skillGroupPanel.add(skillPanel, BorderLayout.CENTER);

        // 로그 창 및 아군 스탯 패널 (1:2 비율 분할)
        JPanel infoPanel = new JPanel(new BorderLayout());

        // 로그 창 패널
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("로그 창"));

        // JTextArea로 콘솔 창 구현
        JTextArea consoleTextArea = new JTextArea();
        consoleTextArea.setEditable(false); // 사용자가 수정할 수 없도록 설정
        consoleTextArea.setBackground(Color.BLACK); // 배경 색상
        consoleTextArea.setForeground(Color.GREEN); // 텍스트 색상
        consoleTextArea.setFont(new Font("Courier New", Font.PLAIN, 14)); // 폰트 설정

        // 콘솔 영역을 스크롤 가능하게 만들기
        JScrollPane scrollPane = new JScrollPane(consoleTextArea);
        logPanel.add(scrollPane, BorderLayout.CENTER);

        // 아군 스탯 표시 패널
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setBorder(BorderFactory.createTitledBorder("아군 스탯 표시창"));
        statsPanel.add(new JLabel("아군 스탯 표시창", SwingConstants.CENTER), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, logPanel, statsPanel);
        splitPane.setResizeWeight(0.33); // 1:2 비율 설정
        splitPane.setDividerSize(2); // 얇은 분할선
        splitPane.setEnabled(false); // 사용자 조정 비활성화

        infoPanel.add(splitPane, BorderLayout.CENTER);

        // 상단: 스킬, 하단: 정보
        add(skillGroupPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);

        // 예시 로그 출력 (디버깅 및 로그 확보용)
        logToConsole(consoleTextArea, "게임이 시작되었습니다.");
        logToConsole(consoleTextArea, "전투 준비 중...");
    }

    // 콘솔에 로그를 출력하는 메서드 (최대 20개 로그 유지)
    private void logToConsole(JTextArea consoleTextArea, String message) {
        // 현재 텍스트 영역의 모든 텍스트를 가져와서 분리
        String currentText = consoleTextArea.getText();
        String[] lines = currentText.split("\n");

        // 최대 20개 로그 유지
        if (lines.length >= 20) {
            // 첫 번째 줄(가장 오래된 로그)을 삭제
            currentText = currentText.substring(currentText.indexOf("\n") + 1);
        }

        // 새 메시지를 추가
        currentText += message + "\n";

        // 텍스트 영역에 다시 설정
        consoleTextArea.setText(currentText);

        // 스크롤을 항상 최신 로그로
        consoleTextArea.setCaretPosition(consoleTextArea.getDocument().getLength());
    }
}
