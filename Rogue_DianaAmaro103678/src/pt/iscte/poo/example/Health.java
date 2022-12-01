package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;

public class Health extends AbstractObject {

    ArrayList<Health> healthBar = new ArrayList<>( 4);

    public Health(Point2D position) {
        super("Green", position);
    }

    @Override
    public int getLayer() {
        return 0;
    }

    public void addHealth(int position, Health health) {
        healthBar.add(position, health);
    }

    public void removeHealth(int position, Health health) {
        healthBar.remove(position);
    }
}
