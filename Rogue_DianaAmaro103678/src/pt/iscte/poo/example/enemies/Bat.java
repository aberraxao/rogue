package pt.iscte.poo.example.enemies;

import pt.iscte.poo.example.Enemie;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Bat extends Enemie {

    public Bat(Point2D position, int hitPoints) {
        super(Bat.class.getSimpleName(), position, hitPoints);
    }

    public void move(Direction d) {
        // TODO: problema quando vai para a linha dos items
        Vector2D randVector = d.asVector();
        Point2D newPos = getPosition().plus(randVector);
        if (ImageMatrixGUI.getInstance().isWithinBounds(newPos)) setPosition(newPos);
    }
}