package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import static pt.iscte.poo.example.GameEngine.logger;

public interface Movable {

    void move(Direction d);

    int getLayer();

    int getHitPoints();

    String getName();

    void setHitPoints(int hitPoints);

    void updateHitPoints(int delta);

    void attack(Movable movable);

    default boolean hitBorder(Point2D position) {
        logger.info(this.getName() + " hit the border");
        return position.getX() < 0 || position.getX() > GameEngine.getGridWidth()
                || position.getY() < 0 || position.getY() > GameEngine.getGridHeight() - 1;
    }
}
