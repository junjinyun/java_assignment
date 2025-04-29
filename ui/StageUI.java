package ui;

/* Stage 진행 UI
*
* 1. 열을 2~3줄로 해서 양 끝에 아군과 적군이 각각 나오도록 하기
* 2. 생각나면 적기 */

import javax.swing.*;
import java.awt.*;

public class StageUI extends JFrame {
    public StageUI() {
        setTitle("스테이지 전투");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. 상단 정보 패널
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel stageLabel = new JLabel("스테이지 1-1");
        JButton backButton = new JButton("돌아가기");
        topPanel.add(stageLabel);
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        // 2. 중앙 전투 필드
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        JPanel playerPanel = new JPanel(); // 아군
        JPanel enemyPanel = new JPanel();  // 적군
        playerPanel.setBorder(BorderFactory.createTitledBorder("아군"));
        enemyPanel.setBorder(BorderFactory.createTitledBorder("적군"));

        // 예시 유닛들
        playerPanel.add(new JLabel("플레이어"));
        enemyPanel.add(new JLabel("슬라임"));

        centerPanel.add(playerPanel);
        centerPanel.add(enemyPanel);
        add(centerPanel, BorderLayout.CENTER);

        // 3. 하단 로그/버튼
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton startButton = new JButton("전투 시작");
        JTextArea logArea = new JTextArea(5, 20);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        bottomPanel.add(startButton, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new StageUI();
    }
}
