package gameplay.UI.Bottom.Right;

import gameplay.GamePlayer;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {
    private final GamePlayer gamePlayer;
    private int[][] mapData;
    private final JButton[][] buttons = new JButton[8][8];

    private int currentX = -1;
    private int currentY = -1;

    // 아이콘 (실제 경로 필요 시 교체)
    private final ImageIcon emptyIcon = null; // new imageicon(주소) 형식으로 이미지 지정
    private final ImageIcon unknownRoomIcon = null;
    private final ImageIcon roomIcon = null;
    private final ImageIcon currentIcon = null;

    public MapPanel(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
        this.mapData = gamePlayer.getMapData();

        setLayout(new GridLayout(8, 8, 10, 10));
        setPreferredSize(new Dimension(620, 620));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initButtons();
        updateMapUI();
    }

    private void initButtons() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(64, 64));
                btn.setFocusPainted(false);
                btn.setBorderPainted(false);
                btn.setContentAreaFilled(false);
                btn.setBorder(BorderFactory.createEmptyBorder());

                int x = i, y = j;
                btn.addActionListener(e -> {
                    if (canMoveTo(x, y)) {
                        moveTo(x, y);
                    }
                });

                buttons[i][j] = btn;
                add(btn);
            }
        }
    }

    private boolean canMoveTo(int x, int y) {
        return isAdjacent(currentX, currentY, x, y) &&
                (mapData[x][y] == 1 || mapData[x][y] == 2);
    }

    private boolean isAdjacent(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2) == 1;
    }

    private void moveTo(int x, int y) {
        mapData[currentX][currentY] = 2;
        mapData[x][y] = 3;
        currentX = x;
        currentY = y;
        updateMapUI();
    }

    private void updateMapUI() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int value = mapData[i][j];
                ImageIcon icon = switch (value) {
                    case 1 -> unknownRoomIcon;
                    case 2 -> roomIcon;
                    case 3 -> currentIcon;
                    default -> emptyIcon;
                };
                buttons[i][j].setIcon(icon);

                if (value == 3) {
                    currentX = i;
                    currentY = j;
                }
            }
        }
        revalidate();
        repaint();
    }
}
