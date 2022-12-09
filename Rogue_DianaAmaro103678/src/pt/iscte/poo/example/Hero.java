package pt.iscte.poo.example;

import pt.iscte.poo.example.items.Key;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.util.List;

import static pt.iscte.poo.example.GameEngine.*;

public class Hero extends GameElement implements Movable {

    private int hitPoints;
    private boolean isDying = false;

    public Hero(Point2D position, int hitPoints) {
        super(Hero.class.getSimpleName(), position);
        this.hitPoints = hitPoints;
    }

    @Override
    public int getLayer() {
        return 3;
    }

    public void setIsDying(boolean isDying) {
        this.isDying = isDying;
    }

    public boolean getIsDying() {
        return isDying;
    }

    @Override
    public void move() {
    }

    public void move(Direction d) {
        Point2D newPos = getPosition().plus(d.asVector());

        if (isOnTheEdge(newPos)) {
            Door door = (Door) select(GameEngine.getRoomList(), el -> el.getPosition().equals(getPosition()) && el.getName().matches("Door.*"));
            if (door != null) interactWithDoor(door);
        } else {
            List<GameElement> elementList = selectList(GameEngine.getRoomList(), el -> el.getPosition().equals(newPos) && el.getLayer() >= 1);
            if (elementList.isEmpty())
                setPosition(newPos);
            else
                interactWithElements(elementList, newPos);
        }

        if (getIsDying())
            updateHitPoints(-1);
    }

    private boolean isOnTheEdge(Point2D position) {
        return position.getX() < 0 || position.getX() > GameEngine.getGridWidth()
                || position.getY() < 0 || position.getY() > GameEngine.getGridHeight() - 1;
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
        if (Inventory.inInventory("Armor") && Math.random() > 0.5)
            setHitPoints(Math.min(GameEngine.getHeroMaxHitPoints(), Math.max(0, getHitPoints() + delta)));
    }

    @Override
    public void attack(Movable movable, int hitPoints) {
        // TODO: fix this
        int oldScore = movable.getHitPoints();
        if (Inventory.inInventory("Sword"))
            movable.updateHitPoints(2 * hitPoints);
        else
            movable.updateHitPoints(hitPoints);
        updateScore(oldScore - movable.getHitPoints());
        logger.info(getName() + " hit " + movable.getName() + " -> new hitPoints: " + movable.getHitPoints());
    }

    private void interactWithElements(List<GameElement> elementList, Point2D position) {
        for (GameElement el : elementList)
            if (el.getName().equals("Wall")) {
                logger.info(this.getName() + " hit a Wall");
            } else if (el.getName().matches("Door.*")) {
                interactWithDoor((Door) el);
            } else if (el.getLayer() == 2) {
                addItemToInventory(el, position);
            } else {
                this.attack((Enemy) el, -1);
            }
    }

    private void addItemToInventory(GameElement el, Point2D position) {
        logger.info(this.getName() + " tries to grab the item " + el.getName());
        Inventory.addInventory((Item) el);
        setPosition(position);
    }

    private void interactWithDoor(Door door) {
        logger.info(this.getName() + " reaches a Door");

        if (door.getName().matches("DoorWay|DoorOpen")) {
            logger.info(this.getName() + " moves to another room");
            moveToAnotherRoom(door);
        } else if (door.getName().equals("DoorClosed")) {
            tryKeysOnDoor(door);
        }
    }

    private void tryKeysOnDoor(Door door) {

        Item keyItem = Inventory.getItem(el -> el.getName().equals("Key") && ((Key) el).getKeyId().equals(door.getKey()));

        if (keyItem == null) {
            logger.info(this.getName() + " cannot move because there is no key in the inventory to open the door");
        } else {
            logger.info(this.getName() + " opens a door and moves to another room");
            Inventory.setDefaultInventory(keyItem);
            GameEngine.removeGameElement(keyItem);
            moveToAnotherRoom(door);
        }
    }

    private void moveToAnotherRoom(Door door) {
        door.openDoor();
        setPosition(door.getPosition());
        GameEngine.updateGui();
        GameEngine.moveToRoom(door.getOtherRoomInt(), door.getPositionOtherRoom());
        Door otherDoor = (Door) select(GameEngine.getRoomList(), el -> el.getPosition().equals(door.getPositionOtherRoom()) && el.getName().equals("DoorClosed"));
        if (otherDoor != null) otherDoor.openDoor();
        GameEngine.updateGui();
    }
}