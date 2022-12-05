package pt.iscte.poo.example;

import pt.iscte.poo.example.items.Key;
import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Inventory {

    private static final int ITEM_MAX = 3;
    public static final String DEFAULT_NAME = "Black";
    public static final int DEFAULT_LAYER = 1;
    int inventoryPosX;
    int inventoryPosY;

    List<Item> inventoryList = new ArrayList<>(ITEM_MAX);

    public Inventory(int inventoryMax, int y) {
        this.inventoryPosX = inventoryMax - ITEM_MAX;
        this.inventoryPosY = y;
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
        return new Item(DEFAULT_NAME, new Point2D(inventoryPosX + position, this.inventoryPosY), DEFAULT_LAYER);
    }

    public void addInventory(Item item) {
        for (int x = 0; x < ITEM_MAX; x++)
            if (inventoryList.get(x).getName().equals(DEFAULT_NAME)) {
                item.setPosition(new Point2D(inventoryPosX + x, inventoryPosY));
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
        if (getItem(position).getName().equals(DEFAULT_NAME)) GameEngine.sendMessageToGui("Nothing to be removed");
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
