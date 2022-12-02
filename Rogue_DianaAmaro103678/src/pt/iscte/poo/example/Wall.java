package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Wall  extends GameElement {

    public Wall(Point2D position) {
        super("Wall", position);
        System.out.println(this);
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
