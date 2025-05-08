package UI;

import javax.swing.*;
import java.awt.*;

public class MainGameUI extends JFrame {

    public MainGameUI() {
        setTitle("메인 UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.add(new TopPanel());
        mainPanel.add(new BottomPanel());

        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGameUI frame = new MainGameUI();
            frame.pack();
            frame.setVisible(true);
        });
    }
}
