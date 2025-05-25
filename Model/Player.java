package gameplay.Model;

import gameplay.Item.InventoryItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
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

    public Player(List<InventoryItem> inventory, int gold, int level, String name) {
        this.inventory = inventory;
        this.gold = gold;
        this.level = level;
        this.name = name;
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

    public void addItemToInventory(Player player, InventoryItem newItem) {
        List<InventoryItem> inventory = player.getInventory();

        // 이미 존재하는 아이템이면 수량만 증가
        for (InventoryItem item : inventory) {
            if (item.getItemId() == newItem.getItemId() &&
                    item.getCategory().equals(newItem.getCategory())) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                return;
            }
        }

        // 새 아이템이면 추가
        inventory.add(newItem);
    }

    public void addToInventory(String category, int itemId, int quantity) {
        for (InventoryItem item : inventory) {
            if (item.getCategory().equals(category) && item.getItemId() == itemId) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        inventory.add(new InventoryItem(category, itemId, quantity));
    }

    public void savePlayer(String path) {
        try (FileWriter writer = new FileWriter(path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}