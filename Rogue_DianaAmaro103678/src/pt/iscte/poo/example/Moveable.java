package pt.iscte.poo.example;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Moveable extends AbstractObject {

    private int hitPoints = 0;

    public Moveable(String name, Point2D position) {
        super(name, position);
    }

    public Moveable(String name, Point2D position, int hitPoints) {
        super(name, position);
        this.hitPoints = hitPoints;
    }

    public void move(Direction d) {
        // TODO: problema quando vai para a linha dos items
        Vector2D randVector = d.asVector();
        Point2D newPos = getPosition().plus(randVector);
        if (ImageMatrixGUI.getInstance().isWithinBounds(newPos)) super.setPosition(newPos);
    }

    @Override
    public int getLayer() {
        return 1;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void updateHitPoints(int delta) {
        this.hitPoints = this.hitPoints + delta;
    }
}