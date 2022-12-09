package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public interface Movable {

    void move();

    int getLayer();

    int getHitPoints();

    String getName();

    void setHitPoints(int hitPoints);

    void updateHitPoints(int delta);

    void attack(Movable movable);
}
