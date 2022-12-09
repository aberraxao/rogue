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
    private static final int INVENTORY_WIDTH = GameEngine.GRID_WIDTH - ITEM_MAX;
    private static final int INVENTORY_HEIGHT = GameEngine.GRID_HEIGHT;

    static List<Item> inventoryList = new ArrayList<>(ITEM_MAX);

    public Inventory() {
        for (int x = 0; x < ITEM_MAX; x++)
            inventoryList.add(defaultInventoryItem(x));
    }

    public static List<Item> getList() {
        return inventoryList;
    }

    private static Item select(int position) {
        return getList().get(position);
    }

    static Item select(Predicate<Item> filter) {
        for (Item it : Inventory.getList())
            if (filter.test(it)) return it;
        return null;
    }

    public static boolean inInventory(String itemName) {
        return select(el -> el.getName().equals(itemName)) != null;
    }

    private Item defaultInventoryItem(int position) {
        return new Item(DEFAULT_ITEM, new Point2D(INVENTORY_WIDTH + position, INVENTORY_HEIGHT), DEFAULT_LAYER);
    }

    public static void addInventory(Item item) {
        for (int x = 0; x < ITEM_MAX; x++)
            if (select(x).getName().equals(DEFAULT_ITEM)) {
                item.setPosition(new Point2D(INVENTORY_WIDTH + x, INVENTORY_HEIGHT));
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
            GameEngine.getRoomList().add(select(position));
            setDefault(position);
        }
    }
}
