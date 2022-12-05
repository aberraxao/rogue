package pt.iscte.poo.example;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;

import static pt.iscte.poo.example.GameEngine.logger;

public abstract class GameElement implements ImageTile {

    private String name;

    private Point2D position;

    private int layer = 0;

    protected GameElement(String name, Point2D position) {
        this.name = name;
        this.position = position;
        if (logger.isLoggable(Level.INFO))
            logger.info(this.toString());
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }
    @Override
    public String toString() {
        return getName() + ": layer " + getLayer() + " on pos " + getPosition();
    }

    static List<GameElement> select(List<GameElement> elements, Predicate<GameElement> filter) {
        List<GameElement> selection = new ArrayList<>();
        for (GameElement el : elements)
            if (filter.test(el))
                selection.add(el);

        return selection;
    }

}