package gameplay.UI.Top;

import gameplay.GamePlayer;
import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyStatusManager;
import gameplay.UI.UIObserver;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CharacterPanelMaker implements UIObserver {

    private final Map<String, JPanel> panelMap = new HashMap<>();

    public JPanel create(String mappingId, String faction, GamePlayer gamePlayer) {
        JPanel charPanel = new JPanel(new BorderLayout());
        charPanel.setBorder(BorderFactory.createTitledBorder(mappingId));
        charPanel.setOpaque(false);

        // 체력 바
        JProgressBar healthBar = new JProgressBar(0, 100);
        healthBar.setStringPainted(true);
        healthBar.setOpaque(false);
        healthBar.setBackground(new Color(0, 0, 0, 0));
        healthBar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI());

        int healthPercent = 0;
        if ("아군".equals(faction) && gamePlayer.getAllyParty() != null) {
            AllyStatusManager allyStatus = gamePlayer.getAllyParty().getAllyByMappingID(mappingId);
            if (allyStatus != null) {
                int health = allyStatus.getBaseStats().getHealth();
                int maxHealth = allyStatus.getBaseStats().getMaxHealth();
                if (maxHealth > 0) healthPercent = (int) ((double) health / maxHealth * 100);
            }
        } else if ("적군".equals(faction) && gamePlayer.getEnemyParty() != null) {
            EnemyStatusManager enemyStatus = gamePlayer.getEnemyParty().getEnemyByMappingID(mappingId);
            if (enemyStatus != null) {
                int health = enemyStatus.getBaseStats().getHealth();
                int maxHealth = enemyStatus.getBaseStats().getMaxHealth();
                if (maxHealth > 0) healthPercent = (int) ((double) health / maxHealth * 100);
            }
        }

        healthBar.setValue(healthPercent);
        charPanel.add(healthBar, BorderLayout.NORTH);

        JLabel factionLabel = new JLabel(faction, SwingConstants.CENTER);
        factionLabel.setOpaque(false);
        factionLabel.setForeground(Color.YELLOW);
        charPanel.add(factionLabel, BorderLayout.CENTER);

        JPanel effectsPanel = new JPanel(new GridLayout(4, 1));
        effectsPanel.setOpaque(false);
        String[] labels = {"상태이상: 정보 없음", "버프: 정보 없음", "디버프: 정보 없음", "특수효과: 정보 없음"};

        if ("아군".equals(faction) && gamePlayer.getAllyParty() != null) {
            AllyStatusManager allyStatus = gamePlayer.getAllyParty().getAllyByMappingID(mappingId);
            if (allyStatus != null) {
                labels[0] = "상태이상: " + allyStatus.getStatusEffects();
                labels[1] = "버프: " + allyStatus.getStatModifierEffects();
                labels[2] = "디버프: " + allyStatus.getStatModifierEffects();
                labels[3] = "특수효과: ";
            }
        } else if ("적군".equals(faction) && gamePlayer.getEnemyParty() != null) {
            EnemyStatusManager enemyStatus = gamePlayer.getEnemyParty().getEnemyByMappingID(mappingId);
            if (enemyStatus != null) {
                labels[0] = "상태이상: ";
                labels[1] = "버프: ";
                labels[2] = "디버프: ";
                labels[3] = "특수효과: ";
            }
        }

        for (String text : labels) {
            JLabel lbl = new JLabel(text, SwingConstants.CENTER);
            lbl.setOpaque(false);
            lbl.setForeground(Color.YELLOW);
            effectsPanel.add(lbl);
        }

        charPanel.add(effectsPanel, BorderLayout.SOUTH);
        panelMap.put(mappingId, charPanel);

        return charPanel;
    }

    @Override
    public void update(GamePlayer gamePlayer) {
        String mappingId = gamePlayer.getMappingId();
        String eMappingId = gamePlayer.getEMappingId();

        updateCharacterPanel(mappingId, "아군", gamePlayer);
        updateCharacterPanel(eMappingId, "적군", gamePlayer);
    }

    private void updateCharacterPanel(String mappingId, String faction, GamePlayer gamePlayer) {
        if (mappingId == null || !panelMap.containsKey(mappingId)) return;

        JPanel charPanel = panelMap.get(mappingId);
        if (charPanel == null) return;

        JProgressBar healthBar = (JProgressBar) charPanel.getComponent(0);
        JPanel effectsPanel = (JPanel) charPanel.getComponent(2); // SOUTH 영역

        int healthPercent = 0;
        String[] labels = {"상태이상: 정보 없음", "버프: 정보 없음", "디버프: 정보 없음", "특수효과: 정보 없음"};

        if ("아군".equals(faction) && gamePlayer.getAllyParty() != null) {
            AllyStatusManager allyStatus = gamePlayer.getAllyParty().getAllyByMappingID(mappingId);
            if (allyStatus != null) {
                int health = allyStatus.getBaseStats().getHealth();
                int maxHealth = allyStatus.getBaseStats().getMaxHealth();
                if (maxHealth > 0) healthPercent = (int) ((double) health / maxHealth * 100);

                labels[0] = "상태이상: " + allyStatus.getStatusEffects();
                labels[1] = "버프: " + allyStatus.getStatModifierEffects();
                labels[2] = "디버프: " + allyStatus.getStatModifierEffects();
                labels[3] = "특수효과: ";
            }
        } else if ("적군".equals(faction) && gamePlayer.getEnemyParty() != null) {
            EnemyStatusManager enemyStatus = gamePlayer.getEnemyParty().getEnemyByMappingID(mappingId);
            if (enemyStatus != null) {
                int health = enemyStatus.getBaseStats().getHealth();
                int maxHealth = enemyStatus.getBaseStats().getMaxHealth();
                if (maxHealth > 0) healthPercent = (int) ((double) health / maxHealth * 100);
                labels[0] = "상태이상: ";
                labels[1] = "버프: ";
                labels[2] = "디버프: ";
                labels[3] = "특수효과: ";
            }
        }

        healthBar.setValue(healthPercent);

        for (int i = 0; i < labels.length; i++) {
            JLabel label = (JLabel) effectsPanel.getComponent(i);
            label.setText(labels[i]);
        }
    }
}