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
    private static final int GRID_HEIGHT = 10;
    private static final int GRID_WIDTH = 10;
    private static GameEngine INSTANCE = null;
    private static ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
    private static final Point2D HERO_DEFAULT_POSITION = new Point2D(1, 1);
    private static final int HERO_DEFAULT_HIT_POINTS = 10;
    private static Dungeon dungeon;
    private static HealthBar healthBar;
    private static Inventory inventory;
    private static Hero hero;
    private int turns;
    private static Room room;
    private static int currRoomNb = 0;

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

    public static GameEngine getInstance() {
        if (INSTANCE == null) INSTANCE = new GameEngine();
        logger.info("Game is instanced");
        return INSTANCE;
    }

    public static int getGridHeight() {
        return GRID_HEIGHT;
    }

    public static int getGridWidth() {
        return GRID_WIDTH;
    }

    private static void setGameElements() {
        setRoom();
        setDungeon();
        setHealthBar();
        setInventory();
        setHero();
    }

    private static void drawGameElements() {
        drawList(getRoomList());
        drawList(HealthBar.getList());
        drawList(Inventory.getList());
        gui.addImage(hero);
    }

    public static void setRoom() {
        room = new Room(getCurrRoomNb());
    }

    public static void setDungeon() {
        dungeon = new Dungeon(getCurrRoomNb(), room);
    }

    public static void setHealthBar() {
        healthBar = new HealthBar(HERO_DEFAULT_HIT_POINTS, GRID_HEIGHT);
    }

    public static void setInventory() {
        inventory = new Inventory();
    }

    public static void setHero() {
        hero = new Hero(HERO_DEFAULT_POSITION, HERO_DEFAULT_HIT_POINTS);
    }

    public static void setHeroPosition(Point2D position) {
        hero.setPosition(position);
    }

    public static void setCurrRoomNb(int nb) {
        currRoomNb = nb;
    }

    public static List<GameElement> getRoomList() {
        return room.getRoomList();
    }

    public static int getCurrRoomNb() {
        return currRoomNb;
    }

    public static <E> void drawList(List<E> elements) {
        for (E room : elements)
            gui.addImage((ImageTile) room);
    }

    public static void updateGui() {
        gui.update();
    }

    public static void closeGui() {
        gui.dispose();
        exit(0);
    }

    public static void sendMessageToGui(String message) {
        gui.setMessage(message);
    }

    public static void askUser(String message) {
        gui.askUser(message);
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

        room.moveEnemies();

        gui.setStatusMessage("ROGUE Starter Package - Turns:" + turns);
        gui.update();
    }

    public static void removeGameElement(ImageTile it){
        gui.removeImage(it);
    }

    public static void moveToRoom(int nb, Point2D position) {
        logger.info(format("Moved to room %d", nb) );

        gui.clearImages();
        dungeon.addDungeonRoom(currRoomNb, room);

        setCurrRoomNb(nb);
        if (dungeon.dungeonHasRoom(nb))
            room = dungeon.getDungeonRoom(nb);
        else {
            setRoom();
            dungeon.addDungeonRoom(nb, room);
        }

        hero.setPosition(position);
        drawGameElements();
    }
}