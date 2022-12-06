package pt.iscte.poo.example;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.awt.event.KeyEvent;
import java.util.logging.Logger;

public class GameEngine implements Observer {

    public static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final int GRID_HEIGHT = 10;
    public static final int GRID_WIDTH = 10;
    public static int heroHealthPoints;
    private static GameEngine INSTANCE = null;
    private static ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
    private static List<GameElement> elements = new ArrayList<>();
    private static HealthBar healthBar;
    private static Inventory inventory;
    private static Hero hero;
    private int turns;

    public static GameEngine getInstance() {
        if (INSTANCE == null) INSTANCE = new GameEngine();
        logger.info("Game is instanced");
        return INSTANCE;
    }

    private GameEngine() {
        gui.registerObserver(this);
        gui.setSize(GRID_WIDTH, GRID_HEIGHT + 1);
        gui.go();
    }

    public void start() {
        setHero();
        setRoom("0");
        setHealth();
        setInventory();

        gui.setStatusMessage("ROGUE Starter Package - Turns:" + turns);
        gui.update();
    }

    public static void updateGui() {
        gui.update();
    }

    public static void sendMessageToGui(String message) {
        gui.setMessage(message);
    }

    private static void setHealth() {
        healthBar = new HealthBar(heroHealthPoints, GRID_HEIGHT);
        for (Health health : healthBar.getList())
            gui.addImage(health);
    }

    private static void setInventory() {
        inventory = new Inventory((int) (gui.getGridDimension().getWidth()), (int) (gui.getGridDimension().getHeight()));
        for (Item item : inventory.getList())
            gui.addImage(item);
    }

    private static void setRoom(String nb) {
        elements = new Room(nb, GRID_WIDTH, GRID_HEIGHT).getList();
        for (GameElement room : elements)
            gui.addImage(room);
    }

    private static void setHero() {
        hero = new Hero(new Point2D(1, 1));
        heroHealthPoints = hero.getHitPoints();
        gui.addImage(hero);
    }

    public static Inventory getInventory() {
        return inventory;
    }

    @Override
    public void update(Observed source) {

        int key = ((ImageMatrixGUI) source).keyPressed();

        if (key == KeyEvent.VK_RIGHT) {
            hero.move(Direction.RIGHT);
            turns++;
        } else if (key == KeyEvent.VK_LEFT) {
            hero.move(Direction.LEFT);
            turns++;
        } else if (key == KeyEvent.VK_DOWN) {
            hero.move(Direction.DOWN);
            turns++;
        } else if (key == KeyEvent.VK_UP) {
            hero.move(Direction.UP);
            turns++;
        } else if (key >= KeyEvent.VK_1 && key <= KeyEvent.VK_3) {
            inventory.removeInventoryIntoPosition(Character.getNumericValue(key) - 1, hero.getPosition());
        }

        gui.setStatusMessage("ROGUE Starter Package - Turns:" + turns);
        gui.update();
    }

    public static void removeGuiImage(Item item) {
        gui.removeImage(item);
    }

    public static List<GameElement> getElements() {
        return elements;
    }

    public static void updateRoom(String nb) {
        setRoom(nb);

    }
}