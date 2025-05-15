package gameplay.UI.Top;

import gameplay.GamePlayer;
import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyStatusManager;

import javax.swing.*;
import java.awt.*;

public class CharacterPanelMaker {

    public static JPanel create(String mappingId, String faction, GamePlayer gamePlayer) {
        JPanel charPanel = new JPanel(new BorderLayout());
        charPanel.setBorder(BorderFactory.createTitledBorder(mappingId));
        charPanel.setOpaque(false);

        // 체력 바 설정
        JProgressBar healthBar = new JProgressBar(0, 100);
        healthBar.setStringPainted(true);
        healthBar.setOpaque(false);
        healthBar.setBackground(new Color(0, 0, 0, 0));
        healthBar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI());

        // 아군일 경우
        if (faction.equals("아군")) {
            AllyStatusManager allyStatus = gamePlayer.getAllyParty().getAllyByMappingID(mappingId);
            if (allyStatus != null) {
                int health = allyStatus.getBaseStats().getHealth();
                int maxHealth = allyStatus.getBaseStats().getMaxHealth();
                healthBar.setValue((int) ((double) health / maxHealth * 100));
            }
        }
        // 적군일 경우
        else if (faction.equals("적군")) {
            EnemyStatusManager enemyStatus = gamePlayer.getEnemyParty().getEnemyByMappingID(mappingId);
            if (enemyStatus != null) {
                int health = enemyStatus.getBaseStats().getHealth();
                int maxHealth = enemyStatus.getBaseStats().getMaxHealth();
                healthBar.setValue((int) ((double) health / maxHealth * 100));
            }
        }

        charPanel.add(healthBar, BorderLayout.NORTH);

        // 진영 레이블 설정
        JLabel factionLabel = new JLabel(faction, SwingConstants.CENTER);
        factionLabel.setOpaque(false);
        factionLabel.setForeground(Color.YELLOW);
        charPanel.add(factionLabel, BorderLayout.CENTER);

        // 효과 패널 설정
        JPanel effectsPanel = new JPanel(new GridLayout(4, 1));
        effectsPanel.setOpaque(false);
        String[] labels = {"버프: 없음", "상태이상: 없음", "디버프: 없음", "특수효과: 없음"};

        // 아군일 경우 상태 표시
        if (faction.equals("아군")) {
            AllyStatusManager allyStatus = gamePlayer.getAllyParty().getAllyByMappingID(mappingId);
            if (allyStatus != null) {
                // 여기에 아군 특유의 상태 (버프, 디버프, 상태이상 등) 정보를 추가할 수 있음
                // 예시: "버프: 방어력 증가" 같은 문구로 표시할 수 있습니다.
                labels[0] = "버프: " +;// allyStatus.getBuffs();
                labels[1] = "상태이상: ";// + allyStatus.getStatusEffects();
                labels[2] = "디버프: ";// + allyStatus.getDebuffs();
                labels[3] = "특수효과: ";// + allyStatus.getSpecialEffects();
            }
        }
        // 적군일 경우 상태 표시
        else if (faction.equals("적군")) {
            EnemyStatusManager enemyStatus = gamePlayer.getEnemyParty().getEnemyByMappingID(mappingId);
            if (enemyStatus != null) {
                // 적군의 상태를 비슷하게 표시할 수 있음
                labels[0] = "버프: ";// + enemyStatus.getBuffs();
                labels[1] = "상태이상: ";// + enemyStatus.getStatusEffects();
                labels[2] = "디버프: ";// + enemyStatus.getDebuffs();
                labels[3] = "특수효과: ";// + enemyStatus.getSpecialEffects();
            }
        }

        // 효과들 레이블로 표시
        for (String text : labels) {
            JLabel lbl = new JLabel(text, SwingConstants.CENTER);
            lbl.setOpaque(false);
            lbl.setForeground(Color.YELLOW);
            effectsPanel.add(lbl);
        }

        charPanel.add(effectsPanel, BorderLayout.SOUTH);

        return charPanel;
    }
}
