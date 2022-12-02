package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;

public class Item extends GameElement {
    ArrayList<Item> inventory = new ArrayList<>();

    public Item(String name, Point2D position) {
        super(name, position);
    }

    @Override
    public int getLayer() {
        return 1;
    }

    public void addInventory(int position, Item item)
    {
        inventory.add(position, item);
    }

    public void removeInventory(int position, Item item)
    {
        inventory.remove(position);
    }
}