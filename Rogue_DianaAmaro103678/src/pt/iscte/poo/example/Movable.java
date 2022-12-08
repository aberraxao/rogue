package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;

public interface Movable {

    void move(Direction d);

    int getLayer();

    int getHitPoints();

    String getName();

    void setHitPoints(int hitPoints);

    void updateHitPoints(int delta);

    void attack(Movable movable);
}
