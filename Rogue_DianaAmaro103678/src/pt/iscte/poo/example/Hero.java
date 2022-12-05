package pt.iscte.poo.example;

import pt.iscte.poo.example.items.Key;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.util.List;

import static pt.iscte.poo.example.GameEngine.getInventory;
import static pt.iscte.poo.example.GameEngine.logger;

public class Hero extends GameElement implements Movable {

    private final int layer = 2;

    private int hitPoints = 10;

    public Hero(Point2D position) {
        super(Hero.class.getSimpleName(), position);
    }

    public void move(Direction d) {
        Point2D newPos = getPosition().plus(d.asVector());

        List<GameElement> elementList = select(GameEngine.getElements(), el -> el.getPosition().equals(newPos) && el.getLayer() >= 1);

        action(elementList, newPos);
    }

    public void action(List<GameElement> elementList, Point2D position) {
        // TODO: Break this into smaller functions and add option to move to another room
        if (elementList.isEmpty()) {
            super.setPosition(position);
            logger.info(this.getName() + " just moved to " + this.getPosition());
        }
        for (GameElement el : elementList)
            if (el.getName().equals("Wall")) {
                logger.info(this.getName() + " hit a " + el.getName());
            } else {
                if (this.getName().equals("Hero")) {
                    if (el.getName().matches("DoorWay|DoorOpen")) {

                        logger.info(this.getName() + " is moving to another room");
                    } else if (el.getName().equals("DoorClosed")) {
                        if (GameEngine.getInventory().hasKey(((Door) el).getKey())) {
                            ((Door) el).openDoor();
                            // TODO
                            super.setPosition(position);
                            logger.info(this.getName() + " opened a door and is moving to another room");
                        } else {
                            logger.info(this.getName() + " cannot move because he has no key for that door");
                        }
                    } else if (el.getName().equals("Key")) {
                        // TODO: fix inventory display
                        GameEngine.getInventory().addInventory((Item) el);
                        super.setPosition(position);
                        GameEngine.updateGui();
                        logger.info(this.getName() + " grabbed the key " + ((Key) el).getKeyId());
                    }
                } else
                    logger.info(this.getName() + " cannot leave the room");
            }
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