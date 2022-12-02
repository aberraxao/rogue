package pt.iscte.poo.example;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Hero extends GameElement implements Movable {

    private final int layer = 1;

    private int hitPoints = 10;

    public Hero(Point2D position) {
        super(Hero.class.getSimpleName(), position);
        System.out.println(this);
    }

    public void move(Direction d) {
        Point2D newPos = getPosition().plus(d.asVector());
        if (isWithinBounds(newPos)) {
            newPos.getNeighbourhoodPoints();
            // TODO: List<AbstractObject> selection = select(ImageMatrixGUI.getInstance().images, el -> el.getPosition() ==newPos) ;
            super.setPosition(newPos);
        } else System.out.println(this.getName() + " hit a wall");
    }

    public static boolean isWithinBounds(Point2D p) {
        System.out.println(ImageMatrixGUI.getInstance().getGridDimension().hashCode());
        return p.getX() >= 0 && p.getY() >= 0 && p.getX() < ImageMatrixGUI.getInstance().getGridDimension().getWidth() && p.getY() < ImageMatrixGUI.getInstance().getGridDimension().getHeight() - 1;
    }

    @Override
    public int getHitPoints() {
        return this.hitPoints;
    }

    @Override
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    @Override
    public void updateHitPoints(int delta) {
        this.hitPoints = this.hitPoints + delta;
    }
    @Override
    public int getLayer(){
        return layer;
    }

}