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

import static java.lang.System.exit;

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
    private int currentRoom = 0;

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
        setGameElements();
        drawGameElements();

        gui.setStatusMessage("ROGUE Starter Package - Turns:" + turns);
        gui.update();
    }

    private void setGameElements() {
        setRoom(currentRoom);
        setHealthBar();
        setInventory();
        setHero();
    }

    private void drawGameElements() {
        drawRoom(currentRoom);
        drawHealthBar();
        drawInventory();
        drawHero();
    }

    private static void setHealthBar() {
        healthBar = new HealthBar(heroHealthPoints, GRID_HEIGHT);
    }

    public static HealthBar getHealthBar() {
        return healthBar;
    }

    public static void drawHealthBar() {
        for (Health item : healthBar.getList())
            gui.addImage(item);
    }

    private static void setInventory() {
        inventory = new Inventory((int) (gui.getGridDimension().getWidth()), (int) (gui.getGridDimension().getHeight()));
    }

    public static Inventory getInventory() {
        return inventory;
    }

    public static void drawInventory() {
        for (Item item : inventory.getList())
            gui.addImage(item);
    }

    private static void setRoom(int nb) {
        elements = new Room(nb, GRID_WIDTH, GRID_HEIGHT).getRoom();
    }

    public static List<GameElement> getRoom() {
        return elements;
    }

    public static void drawRoom(int nb) {
        for (GameElement room : elements)
            gui.addImage(room);
    }

    private static void setHero() {
        hero = new Hero(new Point2D(1, 1));
    }

    public static Hero getHero() {
        return hero;
    }

    public static void drawHero() {
        gui.addImage(hero);
    }

    public static void updateGui() {
        gui.update();
    }

    public static void closeGui() {
        // TODO: improve this
        gui.dispose();
        exit(0);
    }

    public static void sendMessageToGui(String message) {
        gui.setMessage(message);
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


    public static Room updateRoom(int nb) {

        return new Room(nb, GRID_WIDTH, GRID_HEIGHT);
    }
}