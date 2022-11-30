package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Wall  extends  AbstractObject{

    public Wall(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Wall";
    }

    @Override
    public int getLayer() {
        return 1;
    }
}