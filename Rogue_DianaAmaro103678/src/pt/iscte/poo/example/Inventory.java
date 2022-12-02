package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private static final int ITEM_MAX = 3;
    int inventoryPos;

    List<Item> inventory = new ArrayList<>(ITEM_MAX);

    public Inventory(int inventoryMax, int y) {
        this.inventoryPos = inventoryMax - ITEM_MAX;
        for (int x = inventoryPos; x < inventoryPos + ITEM_MAX; x++)
            inventory.add(new Item("Black", new Point2D(x, y)));
    }

    public List<Item> getInventoryList() {
        return this.inventory;
    }

    public void addInventory(int position, Item item) {
        inventory.add(position, item);
    }

    public void removeInventory(int position, Item item) {
        inventory.remove(position);
    }

}
