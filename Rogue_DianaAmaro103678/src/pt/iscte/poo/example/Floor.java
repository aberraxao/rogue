package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Floor extends AbstractObject {

    public Floor(Point2D position) {
        super(Floor.class.getSimpleName(), position);
    }

    @Override
    public int getLayer() {
        return 0;
    }
}