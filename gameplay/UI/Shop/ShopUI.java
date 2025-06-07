package gameplay.UI.Shop;

import gameplay.Model.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gameplay.Item.Item;
import gameplay.GamePlayer;
import gameplay.Item.InventoryItem;
import gameplay.UI.Bottom.Right.InventoryUI;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ShopUI extends JPanel {
    private Player player;
    private JLabel goldLabel;
    private List<Item> currentItemList = new ArrayList<>();
    private String currentCategory = "";

    private final InventoryUI inventoryUI;
    private final GamePlayer gameplayer;

    public ShopUI(JDialog parentDialog, GamePlayer gameplayer, Object data) {
        setLayout(new BorderLayout());
        this.gameplayer = gameplayer;

        CardLayout cardLayout = new CardLayout();
        JPanel parentPanel = new JPanel();
        this.inventoryUI = new InventoryUI(cardLayout, parentPanel, gameplayer);

        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        loadPlayerData();
        updateGoldLabel();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("🛒 상점", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("뒤로가기");
        backButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        });
        topPanel.add(backButton, BorderLayout.WEST);

        JPanel categoryPanel = new JPanel(new GridLayout(1, 3));
        Map<String, String> categoryNames = Map.of(
                "gimmick_item", "기믹 아이템",
                "cashable_item", "환금성 아이템",
                "usable_item", "일회성 아이템"
        );
        Map<String, String> categoryFiles = Map.of(
                "gimmick_item", "src/data/gimmick_item.json",
                "cashable_item", "src/data/cashable_item.json",
                "usable_item", "src/data/usable_item.json"
        );

        categoryNames.forEach((engKey, korName) -> {
            JButton catButton = new JButton(korName);
            catButton.addActionListener(e -> loadCategoryItems(engKey, categoryFiles.get(engKey)));
            categoryPanel.add(catButton);
        });
        topPanel.add(categoryPanel, BorderLayout.SOUTH);

        return topPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));

        JPanel itemPanel = new JPanel(new BorderLayout());
        JList<String> itemList = new JList<>();
        DefaultListModel<String> itemListModel = new DefaultListModel<>();
        itemList.setModel(itemListModel);
        itemPanel.add(new JScrollPane(itemList), BorderLayout.CENTER);

        JPanel descPanel = new JPanel(new BorderLayout());
        JTextArea descriptionArea = new JTextArea("아이템 설명이 여기에 표시됩니다.");
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        itemList.addListSelectionListener(e -> {
            int index = itemList.getSelectedIndex();
            if (index >= 0 && index < currentItemList.size()) {
                descriptionArea.setText(currentItemList.get(index).getInformation());
            }
        });

        this.itemListModel = itemListModel;
        this.itemList = itemList;
        this.descriptionArea = descriptionArea;

        centerPanel.add(itemPanel);
        centerPanel.add(descPanel);
        return centerPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        goldLabel = new JLabel();

        JButton buyButton = new JButton("구매");
        buyButton.addActionListener(e -> buySelectedItem());

        JButton sellButton = new JButton("판매");
        sellButton.addActionListener(e -> sellSelectedItem());

        bottomPanel.add(goldLabel);
        bottomPanel.add(buyButton);
        bottomPanel.add(sellButton);

        return bottomPanel;
    }

    private DefaultListModel<String> itemListModel;
    private JList<String> itemList;
    private JTextArea descriptionArea;

    private void loadPlayerData() {
        player = Player.loadPlayer("src/data/player_data.json");
        if (player == null) {
            player = new Player();
        }
    }

    private void updateGoldLabel() {
        goldLabel.setText("보유 골드: " + player.getGold() + "G");
    }

    private void loadCategoryItems(String categoryKey, String filePath) {
        currentCategory = categoryKey;
        currentItemList = loadItemsFromJson(filePath);
        itemListModel.clear();
        for (Item item : currentItemList) {
            itemListModel.addElement(item.toString());
        }
        descriptionArea.setText("아이템을 선택하세요.");
    }

    private List<Item> loadItemsFromJson(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            Map<String, List<Item>> data = gson.fromJson(reader, new TypeToken<Map<String, List<Item>>>() {}.getType());
            if (data != null && !data.isEmpty()) {
                return data.values().iterator().next();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "아이템 데이터를 불러오는 중 오류가 발생했습니다: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    private void buySelectedItem() {
        int index = itemList.getSelectedIndex();
        if (index < 0 || index >= currentItemList.size()) {
            JOptionPane.showMessageDialog(this, "구매할 아이템을 선택하세요.");
            return;
        }
        Item selectedItem = currentItemList.get(index);

        if (player.getGold() < selectedItem.getPrice()) {
            JOptionPane.showMessageDialog(this, "골드가 부족합니다!");
            return;
        }

        player.setGold(player.getGold() - selectedItem.getPrice());
        addItemToInventory(selectedItem);
        updateGoldLabel();
        savePlayer();
        inventoryUI.update(gameplayer);

        JOptionPane.showMessageDialog(this, selectedItem.getName() + " 구매 완료!");
    }

    private void sellSelectedItem() {
        int index = itemList.getSelectedIndex();
        if (index < 0 || index >= currentItemList.size()) {
            JOptionPane.showMessageDialog(this, "판매할 아이템을 선택하세요.");
            return;
        }
        Item selectedItem = currentItemList.get(index);

        if (!removeItemFromInventory(selectedItem)) {
            JOptionPane.showMessageDialog(this, "해당 아이템이 인벤토리에 없습니다.");
            return;
        }

        player.setGold(player.getGold() + selectedItem.getPrice());
        updateGoldLabel();
        savePlayer();
        inventoryUI.update(gameplayer);

        JOptionPane.showMessageDialog(this, selectedItem.getName() + " 판매 완료!");
    }

    private void addItemToInventory(Item item) {
        for (InventoryItem invItem : player.getInventory()) {
            if (invItem.getCategory().equals(currentCategory) && invItem.getItemId() == item.getId()) {
                invItem.setQuantity(invItem.getQuantity() + 1);
                return;
            }
        }
        player.getInventory().add(new InventoryItem(currentCategory, item.getId(), 1));
    }

    private boolean removeItemFromInventory(Item item) {
        Iterator<InventoryItem> iterator = player.getInventory().iterator();
        while (iterator.hasNext()) {
            InventoryItem invItem = iterator.next();
            if (invItem.getCategory().equals(currentCategory) && invItem.getItemId() == item.getId()) {
                if (invItem.getQuantity() > 1) {
                    invItem.setQuantity(invItem.getQuantity() - 1);
                } else {
                    iterator.remove();
                }
                return true;
            }
        }
        return false;
    }

    private void savePlayer() {
        try (FileWriter writer = new FileWriter("src/data/player_data.json")) {
            Gson gson = new Gson();
            gson.toJson(player, writer);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "플레이어 데이터 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
