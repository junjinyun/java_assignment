package UI;

import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JPanel {

    public BottomPanel() {
        setLayout(new BorderLayout());

        MidPanel battlePanel = new MidPanel();
        add(new LeftPanel(battlePanel), BorderLayout.WEST);
        add(battlePanel, BorderLayout.CENTER);
        add(new RightPanel(battlePanel), BorderLayout.EAST);
    }
}
