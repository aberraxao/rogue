package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static pt.iscte.poo.example.GameEngine.logger;

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

    public List<Item> getList() {
        return this.inventoryList;
    }

    private Item select(int position) {
        return this.getList().get(position);
    }

    static Item select(List<Item> items, Predicate<Item> filter) {
        for (Item it : items)
            if (filter.test(it)) return it;
        return null;
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
        logger.info("Inventory is full!");
    }

    private void setDefault(int position) {
        inventoryList.set(position, defaultInventoryItem(position));
    }

    public void setDefaultInventory(Item it) {
        it.setName(DEFAULT_ITEM);
        it.setLayer(DEFAULT_LAYER);
    }

    public void removeInventoryIntoPosition(int position, Point2D newPosition) {
        if (select(position).getName().equals(DEFAULT_ITEM))
            logger.info("Nothing to be removed");
        else {
            select(position).setPosition(newPosition);
            GameEngine.getRoom().add(select(position));
            setDefault(position);
        }
    }
}
