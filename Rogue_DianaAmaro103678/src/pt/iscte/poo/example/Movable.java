package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;

public interface Movable {

    void move(Direction d);

    int getLayer();

    int getHitPoints();

    void setHitPoints(int hitPoints);

    void updateHitPoints(int delta);

}
