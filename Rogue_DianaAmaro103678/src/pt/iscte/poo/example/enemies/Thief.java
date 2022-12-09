package pt.iscte.poo.example.enemies;

import pt.iscte.poo.example.Enemy;
import pt.iscte.poo.example.Movable;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Thief extends Enemy implements Movable {

    public Thief(Point2D position, int hitPoints) {
        super(Thief.class.getSimpleName(), position, hitPoints);
    }

    @Override
    public void move() {
        // TODO: problema quando vai para a linha dos items
        Direction d = Direction.UP;
        Vector2D randVector = d.asVector();
        Point2D newPos = getPosition().plus(randVector);
        if (ImageMatrixGUI.getInstance().isWithinBounds(newPos)) setPosition(newPos);
    }
}