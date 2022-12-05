package pt.iscte.poo.example.items;

import pt.iscte.poo.example.Item;
import pt.iscte.poo.utils.Point2D;

public class Key extends Item {

    String keyId;

    public Key(Point2D position, String keyId) {
        super(Key.class.getSimpleName(), position);
        this.keyId = keyId;
    }

    public String getKeyId() {
        return this.keyId;
    }
}