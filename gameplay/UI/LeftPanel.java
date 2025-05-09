package gameplay.UI;

import gameplay.GamePlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintStream;
import java.util.ArrayList;

public class LeftPanel extends JPanel {

    private final JTextArea consoleTextArea;
    private final JTextField inputField;
    private final CommandProcessor commandProcessor;
    private JPopupMenu popup;  // 팝업 메뉴를 클래스 레벨에 저장

    public LeftPanel(MidPanel battlePanel, GamePlayer gamePlayer) {
        this.commandProcessor = new CommandProcessor(gamePlayer);

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

        // Skill Group
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
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                String typedText = inputField.getText().toLowerCase();

                if (typedText.isEmpty()) {
                    if (popup != null) {
                        popup.setVisible(false);
                    }
                    return;
                }

                String[] commands = {"spawn", "stage", "allyskill", "enemyskill", "exit"};
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
        JPanel upperStatsPanel = new JPanel(new BorderLayout());
        upperStatsPanel.setBorder(BorderFactory.createTitledBorder("스킬 데이터"));
        upperStatsPanel.add(new JLabel("스킬 관련 데이터 표시", SwingConstants.CENTER), BorderLayout.CENTER);

        // Lower Panel for Character Stats
        JPanel lowerStatsPanel = new JPanel(new BorderLayout());
        lowerStatsPanel.setBorder(BorderFactory.createTitledBorder("캐릭터 스탯"));
        lowerStatsPanel.add(new JLabel("캐릭터 스탯 데이터 표시", SwingConstants.CENTER), BorderLayout.CENTER);

        // Split the statsPanel 1:2 ratio
        JSplitPane statsSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upperStatsPanel, lowerStatsPanel);
        statsSplitPane.setResizeWeight(0.33);  // Upper panel takes 1/3 of the space
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
    }

    private void logToConsole(String message) {
        String currentText = consoleTextArea.getText();
        String[] lines = currentText.split("\n");

        if (lines.length >= 20) {
            currentText = currentText.substring(currentText.indexOf("\n") + 1);
        }

        currentText += message + "\n";
        consoleTextArea.setText(currentText);
        consoleTextArea.setCaretPosition(consoleTextArea.getDocument().getLength());
    }
}
