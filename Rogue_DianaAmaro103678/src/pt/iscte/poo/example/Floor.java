package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

import static pt.iscte.poo.example.GameEngine.logger;

public class Floor extends GameElement {

    public Floor(Point2D position) {
        super(Floor.class.getSimpleName(), position);
    }

    @Override
    public int getLayer() {
        return 0;
    }
}