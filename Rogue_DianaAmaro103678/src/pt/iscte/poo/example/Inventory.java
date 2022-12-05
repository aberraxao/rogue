package pt.iscte.poo.example;

import pt.iscte.poo.example.items.Key;
import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Inventory {

    private static final int ITEM_MAX = 3;
    int inventoryPosX;
    int inventoryPosY;

    List<Item> inventory = new ArrayList<>(ITEM_MAX);

    public Inventory(int inventoryMax, int y) {
        this.inventoryPosX = inventoryMax - ITEM_MAX;
        this.inventoryPosY = y;
        for (int x = 0; x < ITEM_MAX; x++)
            inventory.add(defaultInventoryItem(x));
    }

    public List<Item> getInventoryList() {
        return this.inventory;
    }

    private Item getItem(int position) {
        return this.getInventoryList().get(position);
    }

    private Item defaultInventoryItem(int position){
        return new Item("Black", new Point2D(inventoryPosX + position, this.inventoryPosY), 0);
    }

    public void addInventory(Item item) {
        for (int x = 0; x < ITEM_MAX; x++)
            if (inventory.get(x).getName().equals("Black")) {
                item.setPosition(new Point2D(inventoryPosX + x, inventoryPosY));
                inventory.set(0, item);
                return;
            }

        GameEngine.sendMessageToGui("Unable to save " + item.getName() + ". Inventory is full!");
    }

    private void setDefault(int position){
        inventory.set(position,defaultInventoryItem(position));
    }

    public void removeInventory(int position) {
        setDefault(position);
    }

    public void removeInventoryIntoPosition(int position, Point2D newPosition) {
        if (getItem(position).getName().equals("Black"))
            GameEngine.sendMessageToGui("Nothing to be removed");
        else {
            getItem(position).setPosition(newPosition);
            removeInventory(position);
        }
    }

    boolean inInventory(String itemName) {
        return select(this.getInventoryList(), el -> el.getName().equals(itemName)).isEmpty();
    }

    boolean hasKey(String keyId) {
        return !select(this.getInventoryList(), el -> el.getName().equals("Key") && ((Key) el).getKeyId().equals(keyId)).isEmpty();
    }

    static List<Item> select(List<Item> items, Predicate<Item> filter) {
        List<Item> selection = new ArrayList<>();
        for (Item it : items)
            if (filter.test(it)) selection.add(it);

        return selection;
    }

}
