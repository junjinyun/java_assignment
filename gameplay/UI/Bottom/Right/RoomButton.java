package gameplay.UI.Bottom.Right;
import javax.swing.*;
import java.awt.*;

public class RoomButton extends JButton {
    private boolean isSelected = false;
    private boolean isSelectable = false;
    private int roomType = 0;
    //0 벽 1 이벤트 방 2 빈 방 3 현제 방
    public RoomButton() {
        setPreferredSize(new Dimension(70, 70));
        setFont(new Font("Arial", Font.BOLD, 16));
        setFocusPainted(false);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        updateVisual();
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
        updateVisual();
    }

    public void setSelectable(boolean selectable) {
        this.isSelectable = selectable;
        updateVisual();
    }

    public void setRoomType(int type) {
        this.roomType = type;
        updateVisual();
    }

    private void updateVisual() {
        if (roomType == 0) {
            // 투명하게 처리하되 버튼은 존재함 (이벤트 가능)
            setOpaque(false);
            setContentAreaFilled(false);
            setBorder(BorderFactory.createEmptyBorder());
            setText("");
            setForeground(new Color(0, 0, 0, 0)); // 글자도 완전 투명 처리 (필요시)
            return;
        }

        setOpaque(true);
        setContentAreaFilled(true);

        if (roomType == 3) {
            // 현재 위치 방
            setBackground(new Color(0, 128, 0));  // 진한 초록
            setForeground(Color.YELLOW);
            setText("■"); // 유일한 기호

        } else if (isSelected) {
            setBackground(Color.GREEN.darker());
            setForeground(Color.WHITE);
            setText("●");
            setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        } else if (isSelectable) {
            setBackground(Color.ORANGE);
            setForeground(Color.BLACK);
            setText("→");
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        } else {
            switch (roomType) {
                case 1 -> {
                    setBackground(Color.BLUE);
                    setForeground(Color.WHITE);
                    setText("?");
                    setBorder(BorderFactory.createLineBorder(Color.CYAN, 1));
                }
                case 2 -> {
                    setBackground(Color.GRAY);
                    setForeground(Color.WHITE);
                    setText("");
                    setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                }
                default -> {
                    setBackground(Color.DARK_GRAY);
                    setText("");
                    setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                }
            }
        }
    }

}
