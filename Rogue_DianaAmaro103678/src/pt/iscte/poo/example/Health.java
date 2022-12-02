package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Health extends  AbstractObject{

    public Health(Point2D position) {
        super("Green", position);
    }

    @Override
    public int getLayer() {
        return 0;
    }

}
