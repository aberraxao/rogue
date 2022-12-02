package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Health extends GameElement {

    public Health(Point2D position) {
        super("Green", position);
        System.out.println(this);
    }

    @Override
    public int getLayer() {
        return 0;
    }

}
