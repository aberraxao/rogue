package pt.iscte.poo.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.example.items.Key;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import javax.swing.text.Position;
import java.awt.event.KeyEvent;
import java.util.Scanner;
import java.util.logging.Logger;


public class GameEngine implements Observer {

    public static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static final int GRID_HEIGHT = 10;
    public static final int GRID_WIDTH = 10;
    private static GameEngine INSTANCE = null;
    private static ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
    private static List<GameElement> elements = new ArrayList<>();
    private static HealthBar healthBar;
    private static Inventory inventory;
    private Hero hero;
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
        addGameElement();
        addImage();

        gui.setStatusMessage("ROGUE Starter Package - Turns:" + turns);
        gui.update();
    }


    private void addGameElement() {
        addFloor();
        addRoom(0);
        addHero();
        addHealth();
        addInventory();
    }

    private void addImage() {

        for (GameElement gameElement : elements) {
            gui.addImage(gameElement);
        }

        for (Health health : healthBar.getHealthList()) {
            gui.addImage(health);
        }

        for (Item item : inventory.getInventoryList()) {
            gui.addImage(item);
        }
    }

    private void addFloor() {
        for (int x = 0; x != GRID_WIDTH; x++)
            for (int y = 0; y != GRID_HEIGHT; y++)
                elements.add(new Floor(new Point2D(x, y)));
    }

    private void addWall(Scanner s) {
        // TODO: verificar o tamanho do mapa
        int y = 0;
        String line;
        while (s.hasNextLine() && y <= GRID_HEIGHT) {
            line = s.nextLine();
            for (int x = 0; x < line.length(); x++)
                if (line.charAt(x) == '#') elements.add(new Wall(new Point2D(x, y)));
            y++;
        }
    }

    private void addElement(Scanner s) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        String[] line;

        while (s.hasNextLine()) {
            line = s.nextLine().split(",");

            if (line[0].equals("Door")) {
                if (line.length == 7)
                    elements.add(new Door(getPoint2D(line, 1, 2), line[3], getPoint2D(line, 4, 5), line[6]));
                else elements.add(new Door(getPoint2D(line, 1, 2), line[3], getPoint2D(line, 4, 5)));

            } else if (line[0].equals("Key")) {
                elements.add(new Key(getPoint2D(line, 1, 2), line[3]));
            } else {
                Class<?> clazz;
                try {
                    clazz = Class.forName("pt.iscte.poo.example.enemies." + line[0]);
                } catch (ClassNotFoundException e) {
                    clazz = Class.forName("pt.iscte.poo.example.items." + line[0]);
                }

                Constructor<?> constructor = clazz.getConstructor(Point2D.class);
                elements.add((GameElement) constructor.newInstance(getPoint2D(line, 1, 2)));
            }
        }
    }

    private Point2D getPoint2D(String[] line, int x, int y) {
        return new Point2D(Integer.parseInt(line[x]), Integer.parseInt(line[y]));
    }

    private void addHealth() {
        healthBar = new HealthBar(hero.getHitPoints(), (int) (gui.getGridDimension().getHeight() - 1));
    }

    private void addInventory() {
        inventory = new Inventory((int) (gui.getGridDimension().getWidth()), (int) (gui.getGridDimension().getHeight() - 1));
    }

    private void addHero() {
        hero = new Hero(new Point2D(1, 1));
        gui.addImage(hero);
    }

    public void addRoom(int nb) {
        try {
            Scanner s = new Scanner(new File("rooms/room" + nb + ".txt"));
            addWall(s);
            addElement(s);
            s.close();
        } catch (FileNotFoundException e) {
            gui.setMessage("Room " + nb + " not found.");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException |
                 InvocationTargetException e) {
            // TODO: better error handling
            gui.setMessage(e.getMessage());
        }
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
        } else if (key >= KeyEvent.VK_1 || key <= KeyEvent.VK_3) {
            inventory.removeInventoryIntoPosition(Character.getNumericValue(key)-1, hero.getPosition());
        }

        gui.setStatusMessage("ROGUE Starter Package - Turns:" + turns);
        gui.update();
    }

    public static List<GameElement> getElements() {
        return elements;
    }

    public static Inventory getInventory() {
        return inventory;
    }

    public static void updateGui() {
        gui.update();
    }

    public static void sendMessageToGui(String message) {
        gui.setMessage(message);
    }

}