package gameplay.UI;

import gameplay.GamePlayer;
import gameplay.Party.AllyParty;
import gameplay.Party.AllyStatusManager;
import gameplay.Party.NameMapper;
import loaddata.AllySkills;
import loaddata.SkillManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class LeftPanel extends JPanel {

	private final JTextArea consoleTextArea;
	private final JTextField inputField;
	private final CommandProcessor commandProcessor;
	private JPopupMenu popup; // 팝업 메뉴를 클래스 레벨에 저장
	private GamePlayer gamePlayer; // 게임 플레이어 객체 추가
	private JPanel skillPanel; // 스킬 버튼 패널
	private JPanel upperStatsPanel; // 스킬 관련 데이터 표시 영역

	public LeftPanel(MidPanel battlePanel, GamePlayer gamePlayer) {
		this.commandProcessor = new CommandProcessor(gamePlayer);

		// 게임 플레이어 객체 초기화
		this.gamePlayer = gamePlayer;

		setPreferredSize(new Dimension(920, 0));
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("스킬 및 아군 정보"));

		// System.out을 consoleTextArea로 리디렉션
		System.setOut(new PrintStream(System.out) {
			@Override
			public void print(String s) {
				logToConsole(s);
			}

			@Override
			public void println(String s) {
				logToConsole(s);
			}
		});

		skillPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 0)); // skillPanel 초기화
		ButtonGroup skillGroup = new ButtonGroup();

		ImageIcon activeIcon = new ImageIcon("src/image/Interface/green.png");
		ImageIcon inactiveIcon = new ImageIcon("src/image/Interface/red.png");

		for (int i = 0; i < 6; i++) {
		    JRadioButton skillButton = new JRadioButton("S" + (i + 1));
		    skillButton.setPreferredSize(new Dimension(100, 100));
		    skillButton.setIcon(inactiveIcon);

		    skillButton.addActionListener(e -> {
		        // 버튼이 선택되었을 때의 처리
		        if (skillButton.isSelected()) {
		            skillButton.setIcon(activeIcon);
		            String buttonText = skillButton.getText();  // 예: "S1"
		            int skillNumber = Integer.parseInt(buttonText.substring(1));  // "S" 제거하고 숫자만 추출
		            updateSkillDataDisplay(skillNumber); // 번호를 인자로 전달
		        } else {
		            skillButton.setIcon(inactiveIcon);
		        }

		        // 다른 버튼들의 상태 변경
		        for (Component comp : skillPanel.getComponents()) {
		            if (comp instanceof JRadioButton btn && btn != skillButton) {
		                btn.setIcon(inactiveIcon);
		                btn.setSelected(false); // 다른 버튼은 선택을 해제
		            }
		        }
		    });

		    skillGroup.add(skillButton);  // ButtonGroup에 추가
		    skillPanel.add(skillButton);  // skillPanel에 추가
		}



		JPanel skillGroupPanel = new JPanel(new BorderLayout());
		skillGroupPanel.setBorder(BorderFactory.createTitledBorder("스킬 그룹"));
		skillGroupPanel.add(skillPanel, BorderLayout.CENTER);

		// Info Panel (Log and Stats)
		JPanel infoPanel = new JPanel(new BorderLayout());

		// Log Panel
		JPanel logPanel = new JPanel(new BorderLayout());
		logPanel.setBorder(BorderFactory.createTitledBorder("로그 창"));

		consoleTextArea = new JTextArea();
		consoleTextArea.setEditable(false);
		consoleTextArea.setBackground(Color.BLACK);
		consoleTextArea.setForeground(Color.GREEN);
		consoleTextArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));

		JScrollPane scrollPane = new JScrollPane(consoleTextArea);
		logPanel.add(scrollPane, BorderLayout.CENTER);

		inputField = new JTextField();
		inputField.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
		inputField.setBackground(Color.DARK_GRAY);
		inputField.setForeground(Color.WHITE);
		inputField.setCaretColor(Color.WHITE);

		inputField.addActionListener(e -> {
			String text = inputField.getText().trim();
			if (!text.isEmpty()) {
				logToConsole("> " + text);
				String result = commandProcessor.processCommand(text);
				logToConsole(result);
				inputField.setText("");
			}
		});

		// Auto-complete popup
		inputField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				String typedText = inputField.getText().toLowerCase();

				if (typedText.isEmpty()) {
					if (popup != null) {
						popup.setVisible(false);
					}
					return;
				}

				String[] commands = { "spawn", "stage", "allyskill", "enemyskill", "showask", "showesk", "exit" };
				ArrayList<String> matchingCommands = new ArrayList<>();
				for (String command : commands) {
					if (command.contains(typedText)) {
						matchingCommands.add(command);
					}
				}

				if (matchingCommands.isEmpty()) {
					if (popup != null) {
						popup.setVisible(false);
					}
					return;
				}

				if (matchingCommands.size() > 10) {
					matchingCommands = new ArrayList<>(matchingCommands.subList(0, 10));
				}

				if (popup != null) {
					popup.setVisible(false);
				}

				popup = new JPopupMenu();
				popup.setPreferredSize(new Dimension(60, matchingCommands.size() * 20));

				for (String command : matchingCommands) {
					JMenuItem item = new JMenuItem(command);
					item.setPreferredSize(new Dimension(200, 20));
					item.addActionListener(ev -> {
						inputField.setText(command);
						popup.setVisible(false);
					});
					popup.add(item);
				}

				try {
					Point screenLoc = inputField.getLocationOnScreen();
					SwingUtilities.convertPointFromScreen(screenLoc, inputField.getParent());
					popup.show(inputField.getParent(), screenLoc.x + 300, screenLoc.y);
					inputField.requestFocusInWindow();
				} catch (IllegalComponentStateException ex) {
					ex.printStackTrace();
				}
			}

		});

		logPanel.add(inputField, BorderLayout.SOUTH);

		// Stats Panel (Split into two sections)
		JPanel statsPanel = new JPanel(new BorderLayout());
		statsPanel.setBorder(BorderFactory.createTitledBorder("아군 스탯 표시창"));

		// Upper Panel for Skill Data
		upperStatsPanel = new JPanel(new BorderLayout()); // upperStatsPanel 초기화
		upperStatsPanel.setBorder(BorderFactory.createTitledBorder("스킬 데이터"));
		upperStatsPanel.add(new JLabel("스킬 관련 데이터 표시", SwingConstants.CENTER), BorderLayout.CENTER);

		// Lower Panel for Character Stats
		JPanel lowerStatsPanel = new JPanel(new BorderLayout());
		lowerStatsPanel.setBorder(BorderFactory.createTitledBorder("캐릭터 스탯"));
		lowerStatsPanel.add(new JLabel("캐릭터 스탯 데이터 표시", SwingConstants.CENTER), BorderLayout.CENTER);

		// Split the statsPanel 1:2 ratio
		JSplitPane statsSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upperStatsPanel, lowerStatsPanel);
		statsSplitPane.setResizeWeight(0.33); // Upper panel takes 1/3 of the space
		statsSplitPane.setDividerSize(2);
		statsPanel.add(statsSplitPane, BorderLayout.CENTER);

		// Split the main panel
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, logPanel, statsPanel);
		splitPane.setResizeWeight(0.33);
		splitPane.setDividerSize(2);
		splitPane.setEnabled(false);

		infoPanel.add(splitPane, BorderLayout.CENTER);

		// Add panels to main panel
		add(skillGroupPanel, BorderLayout.NORTH);
		add(infoPanel, BorderLayout.CENTER);

		logToConsole("게임이 시작되었습니다.");
		logToConsole("전투 준비 중...");

		updateSkillButtonIcons(); // 스킬 버튼 아이콘 업데이트
	}

	// 스킬 버튼 아이콘 업데이트
	private void updateSkillButtonIcons() {
		if (gamePlayer == null || gamePlayer.getAllyParty() == null)
			return;

		ArrayList<String> skills = new ArrayList<>();
		for (int i = 1; i <= gamePlayer.getAllyParty().getParty().size(); i++) {
			AllyStatusManager statusManager = gamePlayer.getAllyParty().getAllyByMappingID("A" + i);
			if (statusManager != null) {
				statusManager.getSkillList().forEach(skill -> skills.add(skill.getName()));
			}
		}

		for (int i = 0; i < 5; i++) { // 6번 버튼은 예비용으로 제외
			if (i < skills.size()) {
				JRadioButton skillButton = (JRadioButton) skillPanel.getComponent(i);
				skillButton.setText(skills.get(i));
			}
		}
	}

	// 스킬 데이터를 표시하는 메서드
	private void updateSkillDataDisplay(int skillnum) {
		// 스킬 이름에 맞는 스킬을 찾아서 데이터 표시
		AllyStatusManager statusManager = AllyParty.getAllyByMappingID("A" + 1);
		if (statusManager != null) {
			String name = NameMapper.toSystemName(statusManager.getName());
			List<AllySkills> skills = SkillManager.loadAlltSkillsByKeyName(name);
			AllySkills skill = skills.get(skillnum-1);
			
			if (skill != null) {
				String skillDetails = "스킬 이름: " + skill.getName() + "\n";
				skillDetails += "설명: " + skill.getName() + "\n";
				skillDetails += "피해량: " + skill.getDamageMultiplier() + "\n";
				// 기타 필요한 스킬 정보 추가

				upperStatsPanel.removeAll();
				upperStatsPanel.add(new JTextArea(skillDetails), BorderLayout.CENTER);
				upperStatsPanel.revalidate();
				upperStatsPanel.repaint();
			}
		}
	}

	private void logToConsole(String message) {
		String currentText = consoleTextArea.getText();
		String[] lines = currentText.split("\n");

		if (lines.length >= 50) {
			currentText = currentText.substring(currentText.indexOf("\n") + 1);
		}

		currentText += message + "\n";
		consoleTextArea.setText(currentText);
		consoleTextArea.setCaretPosition(consoleTextArea.getDocument().getLength());
	}
}
