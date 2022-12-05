package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;
public class Item extends GameElement {

    private int layer = 2;

    public Item(String name, Point2D position) {
        super(name, position);
    }

    public Item(String name, Point2D position, int layer) {
        super(name, position);
        this.setLayer(layer);
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public void setLayer(int layer) {
        this.layer = layer;
    }
}