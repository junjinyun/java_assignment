package MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mainmenu extends JFrame {
    private CardLayout cardLayout;  // 화면 전환을 위한 레이아웃
    private JPanel mainPanel;

    public Mainmenu() {
        // 창 설정
        setTitle("게임 메인 화면");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // CardLayout을 사용해 화면 전환
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // 각 화면 생성
        JPanel menuPanel = createMainMenuPanel();
        JPanel dungeonPanel = createDungeonPanel();
        JPanel shopPanel = createShopPanel();

        // 패널을 추가
        mainPanel.add(menuPanel, "메인");
        mainPanel.add(dungeonPanel, "던전");
        mainPanel.add(shopPanel, "상점");

        // 메인 패널을 프레임에 추가
        add(mainPanel);
    }

    // 🔹 메인 메뉴 패널 생성
    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // 수동으로 배치하기 때문에 레이아웃을 null 로 설정

        JLabel title = new JLabel("🎮 게임 메인 화면", SwingConstants.CENTER);
        title.setBounds(100, 20, 300, 50);

        JButton dungeonButton = new JButton("던전 입장");
        dungeonButton.setBounds(150, 100, 200, 50);

        JButton shopButton = new JButton("상점");
        shopButton.setBounds(150, 170, 200, 50);

        // 버튼 이벤트
        dungeonButton.addActionListener(e -> cardLayout.show(mainPanel, "던전"));
        shopButton.addActionListener(e -> cardLayout.show(mainPanel, "상점"));

        panel.add(title);
        panel.add(dungeonButton);
        panel.add(shopButton);

        return panel;
    }

    // 🔹 던전 입장 화면 패널 생성
    private JPanel createDungeonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("🗡️ 던전 입장!", SwingConstants.CENTER);
        JButton backButton = new JButton("뒤로 가기");

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "메인"));

        panel.add(label, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    // 🔹 상점 화면 패널 생성
    private JPanel createShopPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("🛒 상점입니다!", SwingConstants.CENTER);
        JButton backButton = new JButton("뒤로 가기");

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "메인"));

        panel.add(label, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    // 🔥 실행 메서드
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Mainmenu game = new Mainmenu();
            game.setVisible(true);
        });
    }
}
