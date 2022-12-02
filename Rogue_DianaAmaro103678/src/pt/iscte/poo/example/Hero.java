package pt.iscte.poo.example;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.util.List;

import static pt.iscte.poo.example.GameEngine.logger;

public class Hero extends GameElement implements Movable {

    private final int layer = 2;

    private int hitPoints = 10;

    public Hero(Point2D position) {
        super(Hero.class.getSimpleName(), position);
    }

    public void move(Direction d) {
        Point2D newPos = getPosition().plus(d.asVector());

        List<GameElement> elementList = select(GameEngine.getInstance().getElements(), el -> el.getPosition().equals(newPos) && el.getLayer() >= 1);

        if (isWithinBounds(elementList)) {
            newPos.getNeighbourhoodPoints();

            super.setPosition(newPos);
        } else logger.info(this.getName() + " hit a wall");
    }

    public static boolean isWithinBounds(List<GameElement> elementList) {
        for (GameElement el : elementList)
            if (el.getName().equals("Wall"))
                return false;
            else if(el.getName().equals("DoorClosed")){
                return true;
            }


        return true;
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
    public int getLayer() {
        return layer;
    }

}