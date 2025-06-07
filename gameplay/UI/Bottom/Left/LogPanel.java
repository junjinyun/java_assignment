package gameplay.UI.Bottom.Left;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.util.ArrayList;

public class LogPanel extends JPanel {
    private final JTextArea consoleTextArea;
    private final JTextField inputField;
    private final JPopupMenu popup;

    public LogPanel(ActionListener inputActionListener) {
    	setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));  // 상(top) 여백 10px
        setLayout(new BorderLayout());

        // 콘솔 출력 영역
        consoleTextArea = new JTextArea();
        consoleTextArea.setEditable(false);
        consoleTextArea.setBackground(Color.BLACK);
        consoleTextArea.setForeground(Color.GREEN);
        consoleTextArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(consoleTextArea);
        add(scrollPane, BorderLayout.CENTER);

        // 입력 필드
        inputField = new JTextField();
        inputField.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        inputField.setBackground(Color.DARK_GRAY);
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(Color.WHITE);
        inputField.addActionListener(inputActionListener);
        add(inputField, BorderLayout.SOUTH);

        // 자동완성 팝업 초기화
        popup = new JPopupMenu();

        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String typedText = inputField.getText().toLowerCase().trim();

                if (typedText.isEmpty()) {
                    popup.setVisible(false);
                    return;
                }

                String[] commands = { "spawn", "spawnally()", "stage", "allyskill", "enemyskill", "showask", "showesk", "exit","selectally()","selectenemy()","map","testAE()","changeBattleState","movechar()","setNoneMode()" };
                ArrayList<String> matchingCommands = new ArrayList<>();
                for (String command : commands) {
                    if (command.contains(typedText)) {
                        matchingCommands.add(command);
                    }
                }

                if (matchingCommands.isEmpty()) {
                    popup.setVisible(false);
                    return;
                }

                if (matchingCommands.size() > 10) {
                    matchingCommands = new ArrayList<>(matchingCommands.subList(0, 10));
                }

                popup.removeAll();
                popup.setPreferredSize(new Dimension(200, matchingCommands.size() * 20));

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

        // System.out 리디렉션 설정
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
    }

    public void logToConsole(String message) {
        String currentText = consoleTextArea.getText();
        String[] lines = currentText.split("\n");

        if (lines.length >= 50) {
            currentText = currentText.substring(currentText.indexOf("\n") + 1);
        }

        currentText += message + "\n";
        consoleTextArea.setText(currentText);
        consoleTextArea.setCaretPosition(consoleTextArea.getDocument().getLength());
    }

    public JTextField getInputField() {
        return inputField;
    }
}
