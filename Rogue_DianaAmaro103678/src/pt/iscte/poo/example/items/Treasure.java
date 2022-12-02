package pt.iscte.poo.example.items;

import pt.iscte.poo.example.Item;
import pt.iscte.poo.utils.Point2D;

public class Treasure extends Item {

    public Treasure(Point2D position) {
        super(Treasure.class.getSimpleName(), position);
    }

}