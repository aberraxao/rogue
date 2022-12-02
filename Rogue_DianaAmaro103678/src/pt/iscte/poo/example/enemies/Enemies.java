package pt.iscte.poo.example.enemies;

import pt.iscte.poo.example.GameElement;
import pt.iscte.poo.example.Movable;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public abstract class Enemies extends GameElement implements Movable {

    private final int layer = 1;
    private int hitPoints = 0;

    public Enemies(String name, Point2D position) {
        super(name, position);
    }

    public Enemies(String name, Point2D position, int hitPoints) {
        super(name, position);
        this.hitPoints = hitPoints;
    }

    public void move(Direction d) {
        // TODO: problema quando vai para a linha dos items
        Vector2D randVector = d.asVector();
        Point2D newPos = getPosition().plus(randVector);
        if (ImageMatrixGUI.getInstance().isWithinBounds(newPos)) super.setPosition(newPos);
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