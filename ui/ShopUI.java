package ui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import shop.Item;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ShopUI extends JFrame {
    private static final String GIMMICK_ITEM_JSON = "src/data/gimmick_item.json";

    public ShopUI() {
        setTitle("상점");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // 가운데 정렬

        // 상단 제목
        JLabel titleLabel = new JLabel("🛒 상점", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // 중앙 패널: 상품 리스트 + 설명
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));

        // 설명 패널
        JTextArea descriptionArea = new JTextArea("아이템 설명이 여기에 표시됩니다.");
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        centerPanel.add(descScroll);

        add(centerPanel, BorderLayout.CENTER);

        // 하단 패널: 골드 + 구매 버튼
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel goldLabel = new JLabel("보유 골드: 100G");
        JButton buyButton = new JButton("구매");

        bottomPanel.add(goldLabel);
        bottomPanel.add(buyButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // UI 데이터 갱신 | JSON 파일 불러오기
        List<Item> items = loadGimmicksItems();
        // 상품 리스트
        DefaultListModel<String> itemListModel = new DefaultListModel<>();
        for (Item item : items) {
            itemListModel.addElement(item.toString());  // 예: "회복약 - 10G"
        }
        JList<String> itemList = new JList<>(itemListModel);

        JScrollPane itemScroll = new JScrollPane(itemList);
        centerPanel.add(itemScroll);

        // 선택 시 설명 업데이트 (UI만)
        itemList.addListSelectionListener(e -> {
            int index = itemList.getSelectedIndex();
            if (index >= 0) {
                Item selectedItem = items.get(index);
                descriptionArea.setText(selectedItem.getInformation());
            }
        });
    }

    public static java.util.List<Item> loadGimmicksItems() {
        try (FileReader reader = new FileReader(GIMMICK_ITEM_JSON)) {
            Gson gson = new Gson();

            // JSON을 Map 형태로 읽기
            Map<String, java.util.List<Item>> list = gson.fromJson(reader, new TypeToken<Map<String, List<Item>>>(){}.getType());
            return list.get("gimmick_item"); // "gimmick_item" 키에 해당하는 리스트 가져오기
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            ShopUI shop = new ShopUI();
            shop.setVisible(true);
        });
    }
}
