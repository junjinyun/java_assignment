package gameplay.Item;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gameplay.Model.Player;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

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

    public void savePlayerData(Player player) {
        try (Writer writer = new FileWriter("src/data/player_data.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(player, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
