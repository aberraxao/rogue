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

    private int layer;

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
        return this.position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
        logger.info(this.getName() + " moved to " + this.getPosition());
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

    static <E> List<E> selectList(List<E> elements, Predicate<E> filter) {
        List<E> selection = new ArrayList<>();
        for (E el : elements)
            if (filter.test(el))
                selection.add(el);

        return selection;
    }

    static <E> E select(List<E> elements, Predicate<E> filter) {
        for (E el : elements)
            if (filter.test(el)) return el;
        return null;
    }
}