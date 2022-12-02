package pt.iscte.poo.example.items;

import pt.iscte.poo.example.Item;
import pt.iscte.poo.utils.Point2D;

public class HealingPotion extends Item {

    public HealingPotion(Point2D position) {
        super(HealingPotion.class.getSimpleName(), position);
    }

}