package UI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainGameUI extends JFrame {

	private JPanel battleAreaCardPanel;
	private CardLayout battleAreaCardLayout;

	public MainGameUI() {
		setTitle("메인 UI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); // 전체화면
		setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel(new GridLayout(2, 1));
		mainPanel.add(createTopPanel());
		mainPanel.add(createBottomPanel());

		add(mainPanel, BorderLayout.CENTER);
	}

	private JPanel createTopPanel() {
	    JPanel panel = new JPanel(new BorderLayout()) {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);

	            // 배경 이미지 처리
	            ImageIcon icon = new ImageIcon("src/image/cave.jpg"); // 기존 이미지
	            Image img = icon.getImage();

	            BufferedImage resizedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
	            Graphics2D g2d = resizedImage.createGraphics();
	            g2d.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	            g2d.dispose();

	            g.drawImage(resizedImage, 0, 0, this);
	        }
	    };
	    panel.setOpaque(false);

	    // 캐릭터 표시용 패널 (투명)
	    JPanel charactersPanel = new JPanel(new GridLayout(1, 9));
	    charactersPanel.setOpaque(false);

	    for (int i = 4; i >= 1; i--)
	        charactersPanel.add(createCharacterPanel("A" + i));

	    JLabel turnLabel = new JLabel("턴 표시", SwingConstants.CENTER);
	    turnLabel.setForeground(Color.YELLOW); // 텍스트 색상 노란색
	    turnLabel.setOpaque(false);
	    charactersPanel.add(turnLabel);

	    for (int i = 1; i <= 4; i++)
	        charactersPanel.add(createCharacterPanel("E" + i));

	    JLabel titleLabel = new JLabel("해당 턴의 캐릭터 행동순서", SwingConstants.CENTER);
	    titleLabel.setOpaque(false);
	    titleLabel.setForeground(Color.YELLOW); // 텍스트 색상 노란색

	    panel.add(titleLabel, BorderLayout.NORTH);
	    panel.add(charactersPanel, BorderLayout.CENTER);

	    return panel;
	}



	private JPanel createCharacterPanel(String name) {
	    JPanel charPanel = new JPanel(new BorderLayout());
	    charPanel.setBorder(BorderFactory.createTitledBorder(name));
	    charPanel.setOpaque(false); // 패널 투명 처리

	    // 체력 바
	    JProgressBar healthBar = new JProgressBar(0, 100);
	    healthBar.setValue(80);
	    healthBar.setStringPainted(true);
	    healthBar.setOpaque(false);
	    healthBar.setForeground(Color.RED); // 게이지 색상 빨간색
	    healthBar.setBackground(new Color(0, 0, 0, 0)); // 투명 배경
	    healthBar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI() {
	        @Override
	        protected Color getSelectionForeground() {
	            return Color.BLACK; // 체력 숫자 색상
	        }

	        @Override
	        protected Color getSelectionBackground() {
	            return Color.BLACK;
	        }
	    });

	    charPanel.add(healthBar, BorderLayout.NORTH);

	    // 아군/적군 라벨
	    JLabel factionLabel = new JLabel("아군", SwingConstants.CENTER);
	    factionLabel.setOpaque(false);
	    factionLabel.setForeground(Color.YELLOW); // 노란색 텍스트
	    charPanel.add(factionLabel, BorderLayout.CENTER);

	    // 효과 정보 패널
	    JPanel effectsPanel = new JPanel(new GridLayout(4, 1));
	    effectsPanel.setOpaque(false);

	    JLabel buffLabel = new JLabel("버프: 없음", SwingConstants.CENTER);
	    buffLabel.setOpaque(false);
	    buffLabel.setForeground(Color.YELLOW);

	    JLabel statusLabel = new JLabel("상태이상: 없음", SwingConstants.CENTER);
	    statusLabel.setOpaque(false);
	    statusLabel.setForeground(Color.YELLOW);

	    JLabel debuffLabel = new JLabel("디버프: 없음", SwingConstants.CENTER);
	    debuffLabel.setOpaque(false);
	    debuffLabel.setForeground(Color.YELLOW);

	    JLabel specialLabel = new JLabel("특수효과: 없음", SwingConstants.CENTER);
	    specialLabel.setOpaque(false);
	    specialLabel.setForeground(Color.YELLOW);

	    effectsPanel.add(buffLabel);
	    effectsPanel.add(statusLabel);
	    effectsPanel.add(debuffLabel);
	    effectsPanel.add(specialLabel);

	    charPanel.add(effectsPanel, BorderLayout.SOUTH);

	    return charPanel;
	}


	private JPanel createBottomPanel() {
		JPanel bottomPanel = new JPanel(new BorderLayout());

		// ⬅ 좌측: 스킬, 장비, 스탯
		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.setPreferredSize(new Dimension(920, 0));
		leftPanel.setBorder(BorderFactory.createTitledBorder("스킬 및 아군 정보"));

		// 스킬 버튼을 라디오 버튼으로 변경
		ButtonGroup skillGroup = new ButtonGroup();
		JPanel skillPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 0));

		// 이미지 아이콘 준비
		ImageIcon activeIcon = new ImageIcon("src/image/green.png"); // 활성화 이미지
		ImageIcon inactiveIcon = new ImageIcon("src/image/red.png"); // 비활성화 이미지

		// 버튼 크기에 맞게 이미지 크기 조정
		int buttonWidth = 100;
		int buttonHeight = 100;

		// 스킬 버튼 생성
		for (int i = 0; i < 6; i++) {
			JRadioButton skillButton = new JRadioButton("S" + (i + 1));
			skillButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight)); // 버튼 크기 유지

			// 기본 이미지 설정 (비활성화 이미지)
			skillButton.setIcon(inactiveIcon);

			// 버튼이 선택되면 이미지 변경
			skillButton.addActionListener(e -> {
				// 이미 선택된 버튼을 클릭한 경우
				if (skillButton.isSelected()) {
					skillButton.setIcon(activeIcon);
				} else {
					skillButton.setIcon(inactiveIcon);
				}

				// 다른 버튼을 비활성화 상태로 변경
				for (Component comp : skillPanel.getComponents()) {
					if (comp instanceof JRadioButton) {
						JRadioButton btn = (JRadioButton) comp;
						if (btn != skillButton) {
							btn.setIcon(inactiveIcon); // 다른 버튼은 비활성화 상태로 변경
							btn.setSelected(false); // 다른 버튼은 선택 상태 해제
						}
					}
				}
			});

			skillGroup.add(skillButton);
			skillPanel.add(skillButton);
		}

		// 스킬 버튼 그룹에 테두리 추가
		JPanel skillGroupPanel = new JPanel(new BorderLayout());
		Border skillPanelBorder = BorderFactory.createTitledBorder("스킬 그룹");
		skillGroupPanel.setBorder(skillPanelBorder); // 스킬 그룹에 테두리 추가
		skillGroupPanel.add(skillPanel, BorderLayout.CENTER);

		// 아군 정보 및 장비 정보 패널 추가
		JPanel infoPanel = new JPanel(new GridLayout(1, 2));
		infoPanel.add(new JLabel("장비 정보", SwingConstants.CENTER));
		infoPanel.add(new JLabel("아군 스탯 표시창", SwingConstants.CENTER));

		leftPanel.add(skillGroupPanel, BorderLayout.NORTH);
		leftPanel.add(infoPanel, BorderLayout.CENTER);

		// ➡ 우측: 버튼 3개 (지도/인벤토리/적 정보)
		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.setPreferredSize(new Dimension(150, 0));
		rightPanel.setBorder(BorderFactory.createTitledBorder("보기 선택"));

		JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
		JButton btnMap = new JButton("지도");
		JButton btnInv = new JButton("인벤토리");
		JButton btnEnemy = new JButton("적 정보");

		btnMap.addActionListener(e -> battleAreaCardLayout.show(battleAreaCardPanel, "지도"));
		btnInv.addActionListener(e -> battleAreaCardLayout.show(battleAreaCardPanel, "인벤토리"));
		btnEnemy.addActionListener(e -> battleAreaCardLayout.show(battleAreaCardPanel, "적 정보"));

		buttonPanel.add(btnMap);
		buttonPanel.add(btnInv);
		buttonPanel.add(btnEnemy);
		rightPanel.add(buttonPanel, BorderLayout.CENTER);

		// ⬆ 중앙: 전투 영역 카드
		battleAreaCardLayout = new CardLayout();
		battleAreaCardPanel = new JPanel(battleAreaCardLayout);
		battleAreaCardPanel.setBorder(BorderFactory.createTitledBorder("전투영역 A"));
		battleAreaCardPanel.setPreferredSize(new Dimension(800, 800));

		JPanel mapPanel = new JPanel();
		mapPanel.add(new JLabel("A - 지도"));

		JPanel inventoryPanel = new JPanel();
		inventoryPanel.add(new JLabel("A - 인벤토리"));

		JPanel enemyPanel = new JPanel();
		enemyPanel.add(new JLabel("A - 적 정보"));

		battleAreaCardPanel.add(mapPanel, "지도");
		battleAreaCardPanel.add(inventoryPanel, "인벤토리");
		battleAreaCardPanel.add(enemyPanel, "적 정보");
		battleAreaCardLayout.show(battleAreaCardPanel, "지도");

		bottomPanel.add(leftPanel, BorderLayout.WEST);
		bottomPanel.add(battleAreaCardPanel, BorderLayout.CENTER);
		bottomPanel.add(rightPanel, BorderLayout.EAST);

		return bottomPanel;
	}

	public static void main(String[] args) {
		// 애플리케이션 실행
		SwingUtilities.invokeLater(() -> {
			MainGameUI frame = new MainGameUI();
			frame.pack();
			frame.setVisible(true);
		});
	}
}
