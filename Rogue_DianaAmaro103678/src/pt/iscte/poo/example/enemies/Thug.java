package pt.iscte.poo.example.enemies;

import pt.iscte.poo.example.Enemy;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Thug extends Enemy {

    public Thug(Point2D position, int hitPoints) {
        super(Thug.class.getSimpleName(), position, hitPoints);
    }

    public void move(Direction d) {
        // TODO: problema quando vai para a linha dos items
        Vector2D randVector = d.asVector();
        Point2D newPos = getPosition().plus(randVector);
        if (ImageMatrixGUI.getInstance().isWithinBounds(newPos)) setPosition(newPos);
    }
}