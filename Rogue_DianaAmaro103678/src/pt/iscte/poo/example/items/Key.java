package pt.iscte.poo.example.items;

import pt.iscte.poo.example.Item;
import pt.iscte.poo.utils.Point2D;

public class Key extends Item {

    public Key(Point2D position) {
        super(Key.class.getSimpleName(), position);
        System.out.println(this);
    }

}