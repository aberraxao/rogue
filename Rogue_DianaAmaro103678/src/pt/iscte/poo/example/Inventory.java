package pt.iscte.poo.example;

import static pt.iscte.poo.example.GameEngine.logger;

import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Inventory {

    private static final int ITEM_MAX = 3;
    private static final String DEFAULT_ITEM = "Black";
    private static final int DEFAULT_LAYER = 1;
    private static final int INVENTORY_WIDTH = GameEngine.getInstance().getGridWidth() - ITEM_MAX;
    private static final int INVENTORY_HEIGHT = GameEngine.getInstance().getGridHeight();

    List<Item> inventoryList = new ArrayList<>(ITEM_MAX);

    public Inventory() {
        for (int x = 0; x < ITEM_MAX; x++)
            inventoryList.add(defaultInventoryItem(x));
    }

    public List<Item> getList() {
        return inventoryList;
    }

    public List<Item> resetList() {
        return inventoryList = new ArrayList<>(ITEM_MAX);
    }

    private Item getItem(int position) {
        return getList().get(position);
    }

    Item getItem(Predicate<Item> filter) {
        for (Item it : GameEngine.getInstance().getInventory().getList())
            if (filter.test(it)) return it;
        return null;
    }

    public boolean inInventory(String itemName) {
        return getItem(el -> el.getName().equals(itemName)) != null;
    }

    private Item defaultInventoryItem(int position) {
        return new Item(DEFAULT_ITEM, new Point2D(INVENTORY_WIDTH + position, INVENTORY_HEIGHT), DEFAULT_LAYER);
    }

    public void addInventory(Item item) {
        for (int x = 0; x < ITEM_MAX; x++)
            if (getItem(x).getName().equals(DEFAULT_ITEM)) {
                item.setPosition(new Point2D(INVENTORY_WIDTH + x, INVENTORY_HEIGHT));
                GameEngine.getInstance().getInventory().getList().set(x, item);
                return;
            }
        logger.info("Inventory is full!");
    }

    public void setDefaultInventory(Item it) {
        it.setName(DEFAULT_ITEM);
        it.setLayer(DEFAULT_LAYER);
    }

    public void removeInventoryIntoPosition(int position, Point2D newPosition) {
        if (getItem(position).getName().equals(DEFAULT_ITEM))
            logger.info("Nothing to be removed");
        else {
            getItem(position).setPosition(newPosition);
            GameEngine.getInstance().getRoom().addElementToRoom(getItem(position));
            GameEngine.getInstance().getInventory().getList().set(position, defaultInventoryItem(position));
        }
    }

    public Item removeInventory(int position) {
        if (getItem(position).getName().equals(DEFAULT_ITEM)) {
            logger.info("Nothing to be removed");
            return null;
        } else {
            Item it = getItem(position);
            GameEngine.getInstance().getInventory().getList().set(position, defaultInventoryItem(position));
            return it;
        }
    }

    public void useInventoryItem(int position) {
        if (getItem(position).getName().equals("HealingPotion")) {
            logger.info(getItem(position).getName() + " has been used");
            setDefaultInventory(getItem(position));
            Hero hero = GameEngine.getInstance().getHero();
            hero.setIsPoisoned(false);
            hero.setHitPoints(Math.min(hero.getMaxHitPoints(), hero.getHitPoints() + 5));
        }
    }
}
