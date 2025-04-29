package ui;

import javax.swing.*;
import java.awt.*;

public class MainmenuUI extends JFrame {
    public MainmenuUI() {
        setTitle("메인 화면");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙 정렬
        setLayout(new BorderLayout());

        // 상단 타이틀
        JLabel titleLabel = new JLabel("🏰 Dungeon Oracle", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        add(titleLabel, BorderLayout.NORTH);

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));

        JButton startButton = new JButton("▶ 게임 시작");
        JButton shopButton = new JButton("🐙 상점 보기");
        JButton settingsButton = new JButton("⚙ 설정");
        JButton exitButton = new JButton("❌ 종료");

        // 버튼 추가
        buttonPanel.add(startButton);
        buttonPanel.add(shopButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.CENTER);

        // 종료 버튼 기능
        exitButton.addActionListener(e -> System.exit(0));

        // 나중에 여기에 각 버튼마다 화면 전환 연결하면 됨!
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainmenuUI menu = new MainmenuUI();
            menu.setVisible(true);
        });
    }
}

