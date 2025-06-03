package gameplay.Item;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gameplay.Model.Player;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class InventoryItem {
    private String category;
    private int itemId;
    private int quantity;

    public String getCategory() {
        return category;
    }

    public int getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public InventoryItem(String category, int itemId, int quantity) {
        this.category = category;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "category='" + category + '\'' +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                '}';
    }

    public boolean hasItemtoInventory(Player player, InventoryItem hasItem) {
        List<InventoryItem> inventory = player.getInventory();

        // 인벤토리에 가지고 있는 아이템이면 true 리턴
        for (InventoryItem item : inventory) {
            if (item.getItemId() == hasItem.getItemId() &&
                    item.getCategory().equals(hasItem.getCategory())) {
                return true;
            }
        }

        // 가지고 있지 않다면 false 리턴
        return false;
    }

    public void removeItemtoInventory(Player player, InventoryItem Item) {
        if (hasItemtoInventory(player, Item)) {
            Item.setQuantity(Item.getQuantity() - 1);
        }
    }

    public void savePlayerData(Player player) {
        try (Writer writer = new FileWriter("src/data/player_data.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(player, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
