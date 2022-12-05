package pt.iscte.poo.example;

import pt.iscte.poo.example.items.Key;
import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Inventory {

    private static final int ITEM_MAX = 3;
    private static final String DEFAULT_ITEM = "Black";
    private static final int DEFAULT_LAYER = 1;
    private final int inventoryWidth;
    private final int inventoryHeight;

    List<Item> inventoryList = new ArrayList<>(ITEM_MAX);

    public Inventory(int gridWidth, int gridHeight) {
        this.inventoryWidth = gridWidth - ITEM_MAX;
        this.inventoryHeight = gridHeight - 1;
        for (int x = 0; x < ITEM_MAX; x++)
            inventoryList.add(defaultInventoryItem(x));
    }

    public List<Item> getInventoryList() {
        return this.inventoryList;
    }

    private Item getItem(int position) {
        return this.getInventoryList().get(position);
    }

    private Item defaultInventoryItem(int position) {
        return new Item(DEFAULT_ITEM, new Point2D(inventoryWidth + position, inventoryHeight), DEFAULT_LAYER);
    }

    public void addInventory(Item item) {
        for (int x = 0; x < ITEM_MAX; x++)
            if (inventoryList.get(x).getName().equals(DEFAULT_ITEM)) {
                item.setPosition(new Point2D(inventoryWidth + x, inventoryHeight));
                inventoryList.set(x, item);
                return;
            }
        GameEngine.sendMessageToGui("Inventory is full!");
    }

    private void setDefault(int position) {
        inventoryList.set(position, defaultInventoryItem(position));
    }

    public void removeInventory(int position) {
        setDefault(position);
    }

    public void removeInventoryIntoPosition(int position, Point2D newPosition) {
        if (getItem(position).getName().equals(DEFAULT_ITEM)) GameEngine.sendMessageToGui("Nothing to be removed");
        else {
            getItem(position).setPosition(newPosition);
            removeInventory(position);
        }
    }

    boolean inInventory(String itemName) {
        return getItemPos(this.getInventoryList(), el -> el.getName().equals(itemName)) != -1;
    }

    Integer getKeyIdPos(String keyId) {
        return getItemPos(this.getInventoryList(), el -> el.getName().equals("Key") && ((Key) el).getKeyId().equals(keyId));
    }

    static Integer getItemPos(List<Item> items, Predicate<Item> filter) {
        for (Item it : items)
            if (filter.test(it)) return items.indexOf(it);
        return -1;
    }
}
