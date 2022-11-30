package pt.iscte.poo.example;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Hero extends AbstractObject /*, Moveable*/ {

    public Hero(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Hero";
    }

    public void move(Direction d) {
        Vector2D randVector = d.asVector();
        super.setPosition(getPosition().plus(randVector));
    }

    @Override
    public int getLayer() {
        return 1;
    }
}