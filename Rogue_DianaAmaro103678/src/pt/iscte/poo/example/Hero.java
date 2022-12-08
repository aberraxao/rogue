package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.util.List;

import static pt.iscte.poo.example.GameEngine.logger;

public class Hero extends GameElement implements Movable {

    private int hitPoints;

    public Hero(Point2D position, int hitPoints) {
        super(Hero.class.getSimpleName(), position);
        this.hitPoints = hitPoints;
    }

    public void move(Direction d) {
        Point2D newPos = getPosition().plus(d.asVector());
        List<GameElement> elementList = select(GameEngine.getRoom(), el -> el.getPosition().equals(newPos) && el.getLayer() >= 1);
        action(elementList, newPos);
    }

    public void action(List<GameElement> elementList, Point2D position) {

        if (elementList.isEmpty()) {
            if (position.getX() < 0 || position.getX() > GameEngine.getInstance().getGridWidth() || position.getY() < 0 || position.getY() > GameEngine.getInstance().getGridHeight() - 1) {
                Door door = selectDoor(GameEngine.getRoom(), el -> el.getName().matches("Door.*"));
                if (door != null) handleDoors(door);
            } else {
                logger.info(this.getName() + " moved to " + this.getPosition());
                super.setPosition(position);
            }
        }

        for (GameElement el : elementList)
            if (el.getName().equals("Wall")) {
                logger.info(this.getName() + " hit a Wall");
            } else if (el.getName().matches("Door.*")) {
                handleDoors((Door) el);
            } else if (el.getLayer() == 2) {
                handleInventory(el, position);
            } else {
                this.attack((Enemie) el);
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
        this.hitPoints = Math.max(0, getHitPoints() + delta);
    }

    @Override
    public void attack(Movable movable) {
        movable.updateHitPoints(-1);
        logger.info(getName() + " hit " + movable.getName() + " -> new hitpoints: " + movable.getHitPoints());
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

    private void handleDoors(Door door) {

        logger.info(this.getName() + " reaches a Door");

        if (door.getName().matches("DoorWay|DoorOpen")) {
            logger.info(this.getName() + " moves to another room");
            handleNewRoom(door);
        } else if (door.getName().equals("DoorClosed")) {
            tryKeysOnDoor(door);
        }
    }

    private void tryKeysOnDoor(Door door) {

        Integer inventoryPos = GameEngine.getInventory().getKeyIdPos(door.getKey());

        if (inventoryPos == -1) {
            logger.info(this.getName() + " cannot move because there is no key in the inventory to open the door");
        } else {
            logger.info(this.getName() + " opens a door and moves to another room");
            GameEngine.removeGuiImage(GameEngine.getInventory().getList().get(inventoryPos));
            GameEngine.getInventory().removeInventory(inventoryPos);
            handleNewRoom(door);
        }
    }

    private void handleNewRoom(Door door) {
        door.openDoor();
        super.setPosition(door.getPosition());
        GameEngine.updateGui();
        GameEngine.moveToRoom(door.getOtherRoomInt(), door.getPositionOtherRoom());
        Door otherDoor = selectDoor(GameEngine.getRoom(), el -> el.getPosition().equals(door.getPositionOtherRoom()) && el.getName().equals("DoorClosed"));
        if (otherDoor != null) otherDoor.openDoor();
        GameEngine.updateGui();
    }
}