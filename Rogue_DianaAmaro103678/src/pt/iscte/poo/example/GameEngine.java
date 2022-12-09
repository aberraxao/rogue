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
    private static int score;
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

        gui.setStatusMessage("ROGUE Starter Package - Turns: " + turns + ", Score: " + score);
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

    public static Hero getHero() {
        return hero;
    }

    public static int getScore() {
        return score;
    }

    public static void updateScore(int delta) {
        score += delta;
    }

    public static void setCurrRoomNb(int nb) {
        currRoomNb = nb;
    }

    public static List<GameElement> getRoomList() {
        return room.getRoomList();
    }

    public static void setRoomElement(GameElement el) {
        room.getRoomList().add(el);
    }

    public static int getCurrRoomNb() {
        return currRoomNb;
    }

    public static <E> void drawList(List<E> elements) {
        for (E el : elements)
            gui.addImage((ImageTile) el);
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

    public static String askUser(String message) {
        return gui.askUser(message);
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
        } else if (key == KeyEvent.VK_Q) {
            inventory.useInventoryItem(0);
        } else if (key == KeyEvent.VK_W) {
            inventory.useInventoryItem(1);
        } else if (key == KeyEvent.VK_E) {
            inventory.useInventoryItem(2);
        }

        room.moveEnemies();

        gui.setStatusMessage("ROGUE Starter Package - Turns: " + turns + ", Score: " + score);
        gui.update();

        if (hero.getHitPoints() == 0) {
            String user = GameEngine.askUser("GAME OVER. Insert you name to save the score!");
            logger.info(user + " got the score " + GameEngine.getScore());
            // TODO: save scores
            //GameEngine.sendMessageToGui("Press 'ok' to play again. Close the window to leave the game.");
            // if (((ImageMatrixGUI) source).wasWindowClosed())
            GameEngine.closeGui();
            // else {
            //    gui.dispose();
            //    gui = ImageMatrixGUI.getInstance();
            //    GameEngine.getInstance().start();
        }
    }

    public static void removeGameElement(GameElement el) {
        room.getRoomList().remove(el);
        gui.removeImage(el);
    }

    public static void moveToRoom(int nb, Point2D position) {
        logger.info(format("Moved to room %d", nb));

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