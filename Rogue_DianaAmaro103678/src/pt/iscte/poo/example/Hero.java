package pt.iscte.poo.example;

import pt.iscte.poo.example.items.Key;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.util.List;

import static pt.iscte.poo.example.GameEngine.logger;

public class Hero extends GameElement implements Movable {

    private int hitPoints;
    private static final int DEFAULT_DAMAGE = -1;

    public Hero(Point2D position, int hitPoints) {
        super(Hero.class.getSimpleName(), position);
        this.hitPoints = hitPoints;
    }

    @Override
    public int getLayer() {
        return 3;
    }

    public void move(Direction d) {
        Point2D newPos = getPosition().plus(d.asVector());
        List<GameElement> elementList = selectList(GameEngine.getRoomList(), el -> el.getPosition().equals(newPos) && el.getLayer() >= 1);
        action(elementList, newPos);
    }

    public void action(List<GameElement> elementList, Point2D position) {

        if (elementList.isEmpty()) {
            if (hitBorder(position)) {
                Door door = (Door) select(GameEngine.getRoomList(), el -> el.getPosition().equals(getPosition()) && el.getName().matches("Door.*"));
                if (door != null) handleDoors(door);
            } else {
                logger.info(this.getName() + " moved to " + this.getPosition());
                setPosition(position);
            }
        }

        for (GameElement el : elementList)
            if (el.getName().equals("Wall")) {
                logger.info(this.getName() + " hit a Wall");
            } else if (el.getName().matches("Door.*")) {
                handleDoors((Door) el);
            } else if (el.getLayer() == 2) {
                addItemToInventory(el, position);
            } else {
                this.attack((Enemy) el);
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
        // TODO: restart or close game
        setHitPoints(Math.max(0, getHitPoints() + delta));
        if (getHitPoints() == 0)
            GameEngine.askUser(getName() + " has died. Do you want to play again?");
    }

    @Override
    public void attack(Movable movable) {
        if (Inventory.inInventory("Sword"))
            movable.updateHitPoints(2 * DEFAULT_DAMAGE);
        else
            movable.updateHitPoints(DEFAULT_DAMAGE);
        logger.info(getName() + " hit " + movable.getName() + " -> new hitPoints: " + movable.getHitPoints());
    }

    private void addItemToInventory(GameElement el, Point2D position) {
        logger.info(this.getName() + " tries to grab the item " + el.getName());
        Inventory.addInventory((Item) el);
        setPosition(position);
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

        Item keyItem = Inventory.select(el -> el.getName().equals("Key") && ((Key) el).getKeyId().equals(door.getKey()));

        if (keyItem == null) {
            logger.info(this.getName() + " cannot move because there is no key in the inventory to open the door");
        } else {
            logger.info(this.getName() + " opens a door and moves to another room");
            GameEngine.removeInventoryItem(keyItem);
            handleNewRoom(door);
        }
    }

    private void handleNewRoom(Door door) {
        door.openDoor();
        setPosition(door.getPosition());
        GameEngine.updateGui();
        GameEngine.moveToRoom(door.getOtherRoomInt(), door.getPositionOtherRoom());
        Door otherDoor = (Door) select(GameEngine.getRoomList(), el -> el.getPosition().equals(door.getPositionOtherRoom()) && el.getName().equals("DoorClosed"));
        if (otherDoor != null) otherDoor.openDoor();
        GameEngine.updateGui();
    }
}