package gameplay.UI.Top;

import gameplay.GamePlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CharacterGridPanel extends JPanel {

    private GamePlayer gamePlayer;
    private CharacterPanelMaker panelMaker;

    private JPanel allyArea;
    private JPanel emptyArea;
    private JPanel enemyArea;

    public CharacterGridPanel(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
        this.panelMaker = new CharacterPanelMaker();

        gamePlayer.addObserver(panelMaker);
        gamePlayer.addObserver(gp -> updateIcons());

        setLayout(null);
        setOpaque(false);

        allyArea = new JPanel(null);
        allyArea.setOpaque(false);
        add(allyArea);

        emptyArea = new JPanel();
        emptyArea.setOpaque(false);
        add(emptyArea);

        enemyArea = new JPanel(null);
        enemyArea.setOpaque(false);
        add(enemyArea);

        for (int i = 4; i >= 1; i--) {
            String mappingId = "A" + i;
            CharacterUnitPanel unitPanel = new CharacterUnitPanel(mappingId, "아군", i);
            allyArea.add(unitPanel);
        }

        JLabel emptyLabel = new JLabel();
        emptyArea.add(emptyLabel);

        for (int i = 1; i <= 4; i++) {
            String mappingId = "E" + i;
            CharacterUnitPanel unitPanel = new CharacterUnitPanel(mappingId, "적군", i);
            enemyArea.add(unitPanel);
        }

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                layoutAreas();
            }
        });
    }

    private void layoutAreas() {
        int width = getWidth();
        int height = getHeight();

        int allyStartX = (int)(width * 0.05);
        int allyWidth = (int)(width * 0.40);

        int emptyStartX = (int)(width * 0.45);
        int emptyWidth = (int)(width * 0.10);

        int enemyStartX = (int)(width * 0.55);
        int enemyWidth = (int)(width * 0.40);

        allyArea.setBounds(allyStartX, 0, allyWidth, height);
        emptyArea.setBounds(emptyStartX, 0, emptyWidth, height);
        enemyArea.setBounds(enemyStartX, 0, enemyWidth, height);

        layoutUnits(allyArea, "아군");
        layoutUnits(enemyArea, "적군");

        updateIcons();
    }

    private void layoutUnits(JPanel area, String faction) {
        int areaWidth = area.getWidth();
        int areaHeight = area.getHeight();

        int verticalMargin = (int)(areaHeight * 0.05);
        int usableHeight = (int)(areaHeight * 0.9);

        int unitWidth = (int)(areaWidth * 0.20);
        int gapWidth = (int)(areaWidth * 0.05);

        Component[] units = area.getComponents();

        for (int i = 0; i < units.length; i++) {
            Component comp = units[i];
            int x = i * (unitWidth + gapWidth);

            comp.setBounds(x, verticalMargin, unitWidth, usableHeight);

            if (comp instanceof CharacterUnitPanel) {
                ((CharacterUnitPanel) comp).resizeInnerComponents(unitWidth, usableHeight);
            }
        }
    }

    private void updateIcons() {
        updateAllyIcons();
        updateEnemyIcons();
    }

    private void updateAllyIcons() {
        Component[] allyUnits = allyArea.getComponents();
        String selectedId = gamePlayer.getMappingId();

        for (Component comp : allyUnits) {
            if (comp instanceof CharacterUnitPanel) {
                CharacterUnitPanel cup = (CharacterUnitPanel) comp;
                String mappingId = cup.getMappingId();

                if (gamePlayer.getAllyParty() != null) {
                    var allyStatus = gamePlayer.getAllyParty().getAllyByMappingID(mappingId);
                    if (allyStatus != null) {
                        String imgPath = allyStatus.getBaseStats().getImagePath();
                        setButtonIcon(cup.getButton(), imgPath);
                    } else {
                        cup.getButton().setIcon(null);
                    }
                } else {
                    cup.getButton().setIcon(null);
                }

                if (mappingId.equals(selectedId)) {
                    cup.getButton().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                } else {
                    cup.getButton().setBorder(null);
                }
            }
        }
    }

    private void updateEnemyIcons() {
        Component[] enemyUnits = enemyArea.getComponents();

        for (Component comp : enemyUnits) {
            if (comp instanceof CharacterUnitPanel) {
                CharacterUnitPanel cup = (CharacterUnitPanel) comp;
                String mappingId = cup.getMappingId();

                if (gamePlayer.getEnemyParty() != null) {
                    var enemyStatus = gamePlayer.getEnemyParty().getEnemyByMappingID(mappingId);
                    if (enemyStatus != null) {
                        String imgPath = enemyStatus.getBaseStats().getImagePath();
                        setButtonIcon(cup.getButton(), imgPath);
                    } else {
                        cup.getButton().setIcon(null);
                    }
                } else {
                    cup.getButton().setIcon(null);
                }

                // 테두리 설정 제거: 적은 하이라이트 효과 없음
                cup.getButton().setBorder(null);
            }
        }
    }

    private void setButtonIcon(JButton button, String imgPath) {
        if (imgPath == null || imgPath.isEmpty()) {
            button.setIcon(null);
            return;
        }

        int btnWidth = button.getWidth() > 0 ? button.getWidth() : 100;
        int btnHeight = button.getHeight() > 0 ? button.getHeight() : 100;

        int iconWidth = (int)(btnWidth * 0.7);
        int iconHeight = (int)(btnHeight * 0.6);

        Icon currentIcon = button.getIcon();
        if (currentIcon instanceof ImageIcon) {
            ImageIcon currentImageIcon = (ImageIcon) currentIcon;
            if (currentImageIcon.getDescription() != null && currentImageIcon.getDescription().equals(imgPath)) {
                return; // 동일 이미지면 재설정 안 함
            }
        }

        ImageIcon icon = createScaledIcon(imgPath, iconWidth, iconHeight);
        icon.setDescription(imgPath);
        button.setIcon(icon);
    }

    private ImageIcon createScaledIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    private class CharacterUnitPanel extends JPanel {
        private JButton button;
        private JPanel infoPanel;
        private String mappingId;
        private String faction;

        public CharacterUnitPanel(String mappingId, String faction, int index) {
            this.mappingId = mappingId;
            this.faction = faction;

            setLayout(null);
            setOpaque(false);

            button = new JButton(mappingId + " - " + faction);
            if ("아군".equals(faction)) {
                button.setForeground(Color.WHITE);
                button.addActionListener(e -> {
                    gamePlayer.selectAllyByMid(index);
                    updateIcons();
                });
            } else {
                button.setForeground(Color.RED);
                button.addActionListener(e -> {
                    gamePlayer.selectEnemyByMid(index);
                    updateIcons();
                });
            }

            button.setFont(new Font("맑은 고딕", Font.BOLD, 12));
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(true);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.BOTTOM);

            add(button);

            infoPanel = panelMaker.create(mappingId, faction, gamePlayer);
            infoPanel.setOpaque(false);
            add(infoPanel);
        }

        public String getMappingId() {
            return mappingId;
        }

        public JButton getButton() {
            return button;
        }

        public void resizeInnerComponents(int width, int height) {
            button.setBounds(0, 0, width, height);
            infoPanel.setBounds(0, 0, width, height);
        }
    }
}
