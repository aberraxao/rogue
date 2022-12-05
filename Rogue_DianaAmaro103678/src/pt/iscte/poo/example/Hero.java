package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.util.List;

import static pt.iscte.poo.example.GameEngine.*;

public class Hero extends GameElement implements Movable {

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

        if (elementList.isEmpty()) {
            logger.info(this.getName() + " moved to " + this.getPosition());
            super.setPosition(position);
        }

        for (GameElement el : elementList)
            if (el.getName().equals("Wall")) {
                logger.info(this.getName() + " hit a Wall");
            } else if (el.getName().matches("Door.*")) {
                handleDoors(el, position);
            } else if (el.getLayer() == 2) {
                handleInventory(el, position);
            } else {
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
        return 3;
    }

    private void handleInventory(GameElement el, Point2D position) {

        logger.info(this.getName() + " tries to grab the item " + el.getName());

        GameEngine.getInventory().addInventory((Item) el);
        super.setPosition(position);
        GameEngine.updateGui();
    }

    private void handleDoors(GameElement el, Point2D position) {

        logger.info(this.getName() + " reaches a Door");

        if (el.getName().matches("DoorWay|DoorOpen")) {
            logger.info(this.getName() + " moves to another room");
            handleNewRoom(el, position);
        } else if (el.getName().equals("DoorClosed")) {
            tryKeysOnDoor(el, position);
        }
    }

    private void tryKeysOnDoor(GameElement el, Point2D position) {

        Integer inventoryPos = GameEngine.getInventory().getKeyIdPos(((Door) el).getKey());

        if (inventoryPos == -1) {
            logger.info(this.getName() + " cannot move because there is no key in the inventory to open the door");
        } else {
            logger.info(this.getName() + " opens a door and moves to another room");
            ((Door) el).openDoor();
            GameEngine.removeImage(GameEngine.getInventory().getInventoryList().get(inventoryPos));
            GameEngine.getInventory().removeInventory(inventoryPos);
            handleNewRoom(el, position);
        }
    }

    private void handleNewRoom(GameElement el, Point2D position){
        super.setPosition(position);
        GameEngine.updateGui();
    }
}