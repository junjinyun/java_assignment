package gameplay.Model;

import gameplay.Item.InventoryItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {
    private String name;
    private int level;
    private int gold;
    private List<InventoryItem> inventory;

    public Player() {
        this.name = "초보 용사";
        this.level = 1;
        this.gold = 0;
        this.inventory = new ArrayList<>();
    }

    public Player(List<InventoryItem> inventory, int gold, int level, String name) {
        this.inventory = inventory;
        this.gold = gold;
        this.level = level;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public List<InventoryItem> getInventory() {
        return inventory;
    }

    public void setInventory(List<InventoryItem> inventory) {
        this.inventory = inventory;
    }

    public static Player loadPlayer(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Player.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void savePlayer(String path) {
        try (FileWriter writer = new FileWriter(path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToInventory(String category, int itemId, int quantity) {
        for (InventoryItem item : inventory) {
            if (item.getCategory().equals(category) && item.getItemId() == itemId) {
                item.setQuantity(item.getQuantity() + quantity);
                System.out.println("✅ 아이템 수량 증가: " + category + ", id: " + itemId + ", 새 수량: " + item.getQuantity());
                savePlayer("src/data/player_data.json");
                return;
            }
        }

        inventory.add(new InventoryItem(category, itemId, quantity));
        System.out.println("🆕 새 아이템 추가됨: " + category + ", id: " + itemId + ", 수량: " + quantity);
        savePlayer("src/data/player_data.json");
    }

    public void removeFromInventory(String category, int itemId, int quantity) {
        Iterator<InventoryItem> iterator = inventory.iterator();
        while (iterator.hasNext()) {
            InventoryItem item = iterator.next();
            if (item.getCategory().equals(category) && item.getItemId() == itemId) {
                int currentQuantity = item.getQuantity();

                if (quantity > currentQuantity) {
                    System.out.println("❌ 제거 실패: 현재 수량보다 많은 수량을 제거할 수 없습니다. (보유: " + currentQuantity + ", 요청: " + quantity + ")");
                    return;
                } else if (quantity == currentQuantity) {
                    iterator.remove();
                    System.out.println("✅ 아이템 전체 제거됨: " + category + ", id: " + itemId);
                } else {
                    item.setQuantity(currentQuantity - quantity);
                    System.out.println("✅ 아이템 수량 감소: " + category + ", id: " + itemId + " (남은 수량: " + item.getQuantity() + ")");
                }

                savePlayer("src/data/player_data.json");
                return;
            }
        }
        System.out.println("❌ 제거 실패: 해당 아이템을 찾을 수 없습니다.");
    }
}
