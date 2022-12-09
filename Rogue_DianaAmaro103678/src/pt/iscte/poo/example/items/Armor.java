package pt.iscte.poo.example.items;

import pt.iscte.poo.example.Item;
import pt.iscte.poo.utils.Point2D;

public class Armor extends Item {

    public Armor(Point2D position) {
        super(Armor.class.getSimpleName(), position);
    }
}