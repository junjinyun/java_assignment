package gameplay.UI.Bottom.Left;

import gameplay.GamePlayer;
import gameplay.UI.Bottom.Right.*;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {
    private final GamePlayer gamePlayer;
    private final CommandProcessor commandProcessor;
    private SkillButtonPanel skillButtonPanel;
    private LogPanel logPanel;
    private StatsPanel statsPanel;
    private SkillInfoPanel skillInfoPanel;

    public LeftPanel(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
        this.skillInfoPanel = new SkillInfoPanel(null);
        this.skillButtonPanel = new SkillButtonPanel(gamePlayer, skillInfoPanel);
        gamePlayer.addObserver(skillButtonPanel);
        this.commandProcessor = new CommandProcessor(gamePlayer, skillButtonPanel);

        setPreferredSize(new Dimension(920, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("스킬 및 아군 정보"));

        skillButtonPanel.setPreferredSize(new Dimension(920, 100));
        skillButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        add(skillButtonPanel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(920, 540));

        logPanel = new LogPanel(e -> {
            String inputText = logPanel.getInputField().getText().trim();
            if (!inputText.isEmpty()) {
                logPanel.logToConsole("> " + inputText);
                String result = commandProcessor.processCommand(inputText);
                logPanel.logToConsole(result);
                logPanel.getInputField().setText("");
            }
        });

        bottomPanel.add(logPanel, BorderLayout.WEST);
        logPanel.setPreferredSize(new Dimension(384, 0));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        statsPanel = new StatsPanel(gamePlayer);  
        gamePlayer.addObserver(statsPanel);

        skillInfoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 360));

        infoPanel.add(skillInfoPanel);
        infoPanel.add(statsPanel);
        infoPanel.setPreferredSize(new Dimension(526, 0));

        bottomPanel.add(infoPanel, BorderLayout.CENTER);
        add(bottomPanel);

        logPanel.logToConsole("게임이 시작되었습니다.");
        logPanel.logToConsole("전투 준비 중...");
    }


    public void showDefaultSkillInfo() {
        skillInfoPanel.showDefaultSkillInfo();
    }
}
