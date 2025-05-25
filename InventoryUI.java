package gameplay.UI.Bottom.Right;

import gameplay.Item.Item;
import gameplay.Item.InventoryItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gameplay.Model.Player;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryUI extends JPanel {
    private final DefaultListModel<String> itemListModel = new DefaultListModel<>(); // 🔄 모델 필드로 분리
    private final JList<String> itemList = new JList<>(itemListModel);              // 🔄 리스트 필드로 분리
    private final JTextArea descriptionArea = new JTextArea("아이템 설명이 여기에 표시됩니다."); // 🔄 설명창도 필드로
    private final List<Item> items = new ArrayList<>(); // 🔄 상세 아이템 리스트
    private final JPanel inventoryPanel = new JPanel(new BorderLayout()); // 🔄 중앙 패널 분리
    private final JScrollPane scrollPane = new JScrollPane(itemList);     // 🔄 재사용



    private final Map<String, String> categoryFileMap = Map.of(
            "gimmick_item", "data/gimmick_item.json",
            "usable_item", "data/usable_item.json",
            "cashable_item", "data/cashable_item.json"
    );

    public InventoryUI(CardLayout cardLayout, JPanel parentPanel) {
        setLayout(new BorderLayout());

        // 상단 라벨
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("📦 인벤토리", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // 중앙 UI 구성
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(inventoryPanel);
        JPanel descPanel = new JPanel(new BorderLayout());
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        centerPanel.add(descPanel);

        inventoryPanel.add(scrollPane, BorderLayout.CENTER); // 처음에 scrollPane 붙여줌
        add(centerPanel, BorderLayout.CENTER);

        // 아이템 선택 시 설명 표시
        itemList.addListSelectionListener(e2 -> {
            int index = itemList.getSelectedIndex();
            if (index >= 0 && index < items.size()) {
                descriptionArea.setText(items.get(index).getInformation());
            }
        });

        refresh(); // 🔄 초기 로딩 시 호출
    }

    /**
     * 🔄 인벤토리 목록을 새로 불러와 UI에 적용
     */
    public void refresh() {
        itemListModel.clear();
        items.clear();
        descriptionArea.setText("아이템 설명이 여기에 표시됩니다.");

        Player player = Player.loadPlayer("data/player_data.json");
        List<InventoryItem> inventory = player.getInventory();

        for (InventoryItem inventoryItem : inventory) {
            Item detailedItem = findItemInfo(inventoryItem.getCategory(), inventoryItem.getItemId());
            if (detailedItem != null) {
                items.add(detailedItem);
                String display = String.format(
                        "%s | 수량: %d | 가격: %dG",
                        detailedItem.getName(),
                        inventoryItem.getQuantity(),
                        detailedItem.getPrice()
                );
                itemListModel.addElement(display);
            } else {
                itemListModel.addElement("알 수 없는 아이템 (id: " + inventoryItem.getItemId() + ")");
            }
        }
    }

    public Item findItemInfo(String category, int itemId) {
        String path = categoryFileMap.get(category);
        if (path == null) return null;

        try (FileReader reader = new FileReader(path)) {
            Gson gson = new Gson();
            Map<String, List<Item>> data = gson.fromJson(reader, new TypeToken<Map<String, List<Item>>>() {}.getType());
            List<Item> items = data.values().iterator().next();

            for (Item item : items) {
                if (item.getId() == itemId) {
                    return item;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}