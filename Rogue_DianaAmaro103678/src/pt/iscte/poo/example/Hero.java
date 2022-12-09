package pt.iscte.poo.example;

import pt.iscte.poo.example.items.Key;
import pt.iscte.poo.example.items.Treasure;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.util.List;

import static pt.iscte.poo.example.GameEngine.logger;

public class Hero extends GameElement implements Movable {

    private static int MAX_HITPOINTS;
    private int hitPoints;
    private int score = 0;
    private int turns = 0;
    private boolean isPoisoned = false;

    public Hero(int hitPoints) {
        super(Hero.class.getSimpleName(), new Point2D(1, 1));
        this.MAX_HITPOINTS = hitPoints;
        this.hitPoints = hitPoints;
    }

    private void setHeroDirection(Direction d) {
        switch (d) {
            case RIGHT -> this.setName("HeroRight");
            case LEFT -> this.setName("HeroLeft");
            default -> this.setName("Hero");
        }
    }

    @Override
    public int getLayer() {
        return 3;
    }

    public int getMaxHitPoints() {
        return MAX_HITPOINTS;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public void addTurns() {
        this.turns++;
    }

    public int getTurns() {
        return this.turns;
    }

    public void setIsPoisoned(boolean isPoisoned) {
        this.isPoisoned = isPoisoned;
    }

    public boolean getIsPoisoned() {
        return isPoisoned;
    }

    @Override
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    @Override
    public int getHitPoints() {
        return this.hitPoints;
    }

    @Override
    public void move() {
        // Hero's move() method requires an argument
    }

    public void move(Direction d) {
        if (d == null) return;

        Point2D newPos = getPosition().plus(d.asVector());

        if (isRoomEdge(newPos)) {
            // TODO: check class filter
            Door door = (Door) select(GameEngine.getInstance().getRoom().getRoomElementsList(),
                    el -> el.getPosition().equals(getPosition()) && el.getClass().getSimpleName().equals("Door"));
            if (door != null) interactWithDoor(door);
        } else {
            List<GameElement> elementList = selectList(GameEngine.getInstance().getRoom().getRoomElementsList(),
                    el -> el.getPosition().equals(newPos) && el.getLayer() >= 1);
            if (elementList.isEmpty()) {
                setPosition(newPos);
                addTurns();
                setHeroDirection(d);
            } else
                interactWithElements(elementList, newPos);
        }

        if (getIsPoisoned())
            updateHitPoints(-1);

        GameEngine.getInstance().updateGui();
        GameEngine.getInstance().getRoom().moveEnemies();
    }

    private boolean isRoomEdge(Point2D position) {
        if (position == null)
            throw new NullPointerException("Position not valid");
        else
            return position.getX() < 0 || position.getX() > GameEngine.getInstance().getGridWidth()
                    || position.getY() < 0 || position.getY() > GameEngine.getInstance().getGridHeight() - 1;
    }

    @Override
    public void updateHitPoints(int delta) {
        // TODO: restart or close game
        if (Inventory.inInventory("Armor") && Math.random() > 0.5)
            setHitPoints(Math.min(MAX_HITPOINTS, Math.max(0, getHitPoints() + delta)));
        HealthBar.updateHealth();
    }

    @Override
    public void attack(Movable movable, int hitPoints) {
        // TODO: fix this
        int oldHitPoints = movable.getHitPoints();
        if (Inventory.inInventory("Sword"))
            movable.updateHitPoints(2 * hitPoints);
        else
            movable.updateHitPoints(hitPoints);
        setScore(oldHitPoints - movable.getHitPoints());
        logger.info(getName() + " hit " + movable.getName() + " -> new hitPoints: " + movable.getHitPoints());
    }

    private void interactWithElements(List<GameElement> elementList, Point2D position) {
        for (GameElement el : elementList)
            if (el instanceof Treasure)
                GameEngine.getInstance().handleEndGame(true);
            else if (el instanceof Wall)
                logger.info(this.getName() + " hit a Wall");
            else if (el.getName().matches("Door.*"))
                interactWithDoor((Door) el);
            else if (el.getLayer() == 2)
                addItemToInventory(el, position);
            else
                this.attack((Enemy) el, -1);
    }

    private void addItemToInventory(GameElement el, Point2D position) {
        logger.info(this.getName() + " tries to grab the item " + el.getName());
        Inventory.addInventory((Item) el);
        setPosition(position);
    }

    private void interactWithDoor(Door door) {
        logger.info(this.getName() + " reaches a Door");

        if (door.getIsOpen())
            moveToAnotherRoom(door);
        else
            tryKeysOnDoor(door);
    }

    private void tryKeysOnDoor(Door door) {

        Item keyItem = Inventory.getItem(el -> el.getName().equals("Key") && ((Key) el).getKeyId().equals(door.getKey()));

        if (keyItem == null) {
            logger.info(this.getName() + " cannot move because there is no key in the inventory to open the door");
        } else {
            logger.info(this.getName() + " opens a door and moves to another room");
            Inventory.setDefaultInventory(keyItem);
            GameEngine.getInstance().removeGameElement(keyItem);
            moveToAnotherRoom(door);
        }
    }

    private void moveToAnotherRoom(Door door) {
        logger.info(this.getName() + " moves to another room");
        door.openDoor();
        setPosition(door.getPosition());
        addTurns();
        GameEngine.getInstance().updateGui();
        GameEngine.getInstance().moveToRoom(door.getOtherRoomInt(), door.getPositionOtherRoom());
        Door otherDoor = (Door) select(GameEngine.getInstance().getRoom().getRoomElementsList(),
                el -> el.getPosition().equals(door.getPositionOtherRoom()) && el.getName().equals("DoorClosed"));
        if (otherDoor != null) otherDoor.openDoor();
        GameEngine.getInstance().updateGui();
    }
}