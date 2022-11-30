package pt.iscte.poo.example;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class AbstractObject implements ImageTile {

    private String name;

    private Point2D position;

    public AbstractObject(String name, Point2D position) {
        this.name = name;
        this.position = position;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position){
        this.position = position;
    }


}