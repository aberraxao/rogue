package pt.iscte.poo.example;

import java.util.List;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.lang.System.exit;

public class GameEngine implements Observer {

    public static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static GameEngine INSTANCE = null;
    private ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
    private static final int MAX_HEALTH = 10;
    private static final int GRID_HEIGHT = 10;
    private static final int GRID_WIDTH = 10;
    private Dungeon dungeon;
    private HealthBar healthBar;
    private Inventory inventory;
    private Hero hero;
    private Room room;

    public static GameEngine getInstance() {
        if (INSTANCE == null) INSTANCE = new GameEngine();
        return INSTANCE;
    }

    private GameEngine() {
        gui.registerObserver(this);
        gui.setSize(GRID_WIDTH, GRID_HEIGHT + 1);
        gui.go();
    }

    public void start() {
        setGameElements(0);
        drawGameElements();

        gui.setStatusMessage("ROGUE Starter Package - Turns: " + hero.getTurns() + ", Score: " + hero.getScore());
        updateGui();
    }

    private void setGameElements(int nb) {
        setHero();
        setRoom(nb);
        setDungeon(nb);
        setHealthBar();
        setInventory();
    }

    private void drawGameElements() {
        gui.addImage(hero);
        drawList(room.getRoomElementsList());
        drawList(healthBar.getList());
        drawList(inventory.getList());
    }

    public int getGridHeight() {
        return GRID_HEIGHT;
    }

    public int getGridWidth() {
        return GRID_WIDTH;
    }

    public void setRoom(int nb) {
        room = new Room(nb);
    }

    public Room getRoom() {
        return room;
    }

    public void setDungeon(int nb) {
        dungeon = new Dungeon(nb, room);
    }

    public void setHealthBar() {
        healthBar = new HealthBar(MAX_HEALTH, GRID_HEIGHT);
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }

    public void setInventory() {
        inventory = new Inventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setHero() {
        hero = new Hero(MAX_HEALTH);
    }

    public Hero getHero() {
        return hero;
    }

    public <E> void drawList(List<E> elements) {
        for (E el : elements)
            gui.addImage((ImageTile) el);
    }

    public <E> void clearList(List<E> elements) {
        for (E el : elements) {
            room.removeElement((GameElement) el);
            gui.removeImage((ImageTile) el);
        }
    }

    public void updateGui() {
        GameEngine.getInstance().getHealthBar().updateHealth();
        gui.update();
    }

    public void closeGui() {
        gui.dispose();
        exit(0);
    }

    public void sendMessageToGui(String message) {
        gui.setMessage(message);
    }

    public String askUser(String message) {
        return gui.askUser(message);
    }

    @Override
    public void update(Observed source) {

        int key = ((ImageMatrixGUI) source).keyPressed();

        switch (key) {
            case KeyEvent.VK_RIGHT -> hero.move(Direction.RIGHT);
            case KeyEvent.VK_LEFT -> hero.move(Direction.LEFT);
            case KeyEvent.VK_DOWN -> hero.move(Direction.DOWN);
            case KeyEvent.VK_UP -> hero.move(Direction.UP);
            case KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3 ->
                    inventory.removeInventoryIntoPosition(Character.getNumericValue(key) - 1, hero.getPosition());
            case KeyEvent.VK_Q -> inventory.useInventoryItem(0);
            case KeyEvent.VK_W -> inventory.useInventoryItem(1);
            case KeyEvent.VK_E -> inventory.useInventoryItem(2);
            default -> logger.info("Key not taken into account");
        }

        gui.setStatusMessage("ROGUE Starter Package - Turns: " + hero.getTurns() + ", Score: " + hero.getScore());
        updateGui();
        if (hero.getHitPoints() == 0) handleEndGame(false);
    }

    public void removeGameElement(GameElement el) {
        room.removeElement(el);
        gui.removeImage(el);
    }

    public void moveToRoom(int nb, Point2D position) {
        logger.info(format("Moved to room %d", nb));

        gui.clearImages();
        dungeon.addDungeonRoom(room.getNb(), room);

        if (dungeon.dungeonHasRoom(nb))
            room = dungeon.getDungeonRoom(nb);
        else {
            setRoom(nb);
            dungeon.addDungeonRoom(nb, room);
        }

        hero.setPosition(position);
        drawGameElements();
    }

    private void clearAll() {
        gui.clearImages();
        hero = null;
        dungeon = null;
        room = null;
        inventory = null;
        healthBar = null;
    }

    public void handleEndGame(boolean won) {
        String user;
        if (won)
            user = askUser("YOU WON. Insert you name to save the score!");
        else
            user = askUser("GAME OVER. Insert you name to save the score!");
        logger.info(user + " got the score " + hero.getScore());
        // TODO: save scores
        gui.setMessage("Press 'ok' to play again. Close the window to leave the game.");
        if (gui.wasWindowClosed())
            closeGui();
        else {
            clearAll();
            start();
        }
    }
}