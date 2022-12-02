package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Item extends GameElement {

    private final int layer = 3;

    public Item(String name, Point2D position) {
        super(name, position);
    }

    @Override
    public int getLayer() {
        return layer;
    }
}