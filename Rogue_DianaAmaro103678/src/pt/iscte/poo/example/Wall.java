package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

import static pt.iscte.poo.example.GameEngine.logger;

public class Wall  extends GameElement {

    public Wall(Point2D position) {
        super("Wall", position);
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
