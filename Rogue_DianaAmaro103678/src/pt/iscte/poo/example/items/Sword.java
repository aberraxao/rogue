package pt.iscte.poo.example.items;

import pt.iscte.poo.example.Item;
import pt.iscte.poo.utils.Point2D;

public class Sword extends Item {

    public Sword(Point2D position) {
        super(Sword.class.getSimpleName(), position);
    }

}