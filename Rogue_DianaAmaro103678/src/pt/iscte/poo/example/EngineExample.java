package pt.iscte.poo.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.example.enemies.Skeleton;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.awt.event.KeyEvent;
import java.util.Scanner;


public class EngineExample implements Observer {

    public static final int GRID_HEIGHT = 10;
    public static final int GRID_WIDTH = 10;

    private static EngineExample INSTANCE = null;
    private ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

    private List<AbstractObject> elements = new ArrayList<>();
    private Hero hero;

    private Skeleton skeleton;

    private int turns;

    public static EngineExample getInstance() {
        if (INSTANCE == null) INSTANCE = new EngineExample();
        return INSTANCE;
    }

    private EngineExample() {
        gui.registerObserver(this);
        gui.setSize(GRID_WIDTH, GRID_HEIGHT + 1);
        gui.go();
    }


    public void start() {
        addFloor();
        loadRoom(1);
        addObjects();

        for (AbstractObject abstractObject : elements) {
            gui.addImage(abstractObject);
        }

        gui.setStatusMessage("ROGUE Starter Package - Turns:" + turns);
        gui.update();
    }

    private void addFloor() {
        for (int x = 0; x != GRID_WIDTH; x++)
            for (int y = 0; y != GRID_HEIGHT; y++)
                elements.add(new Floor(new Point2D(x, y)));
    }

    private void addWall(Scanner s) {
        // TODO: verificar o tamanho do mapa
        // TODO: x,y ao contrário?
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

            } else {
                Class<?> clazz;
                try {
                    clazz = Class.forName("pt.iscte.poo.example.enemies." + line[0]);
                } catch (ClassNotFoundException e) {
                    clazz = Class.forName("pt.iscte.poo.example.items." + line[0]);
                }

                Constructor<?> constructor = clazz.getConstructor(Point2D.class);
                elements.add((AbstractObject) constructor.newInstance(getPoint2D(line, 1, 2)));
            }
        }
    }

    private Point2D getPoint2D(String[] line, int x, int y) {
        return new Point2D(Integer.parseInt(line[x]), Integer.parseInt(line[y]));
    }

    public void loadRoom(int nb) {

        try {
            Scanner s = new Scanner(new File("rooms/room" + nb + ".txt"));
            addWall(s);
            addElement(s);
            s.close();
        } catch (FileNotFoundException e) {
            System.err.println("Sala " + nb + " não encontrada.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void addObjects() {
        hero = new Hero(new Point2D(1, 1));
        gui.addImage(hero);
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
        }

        gui.setStatusMessage("ROGUE Starter Package - Turns:" + turns);
        gui.update();
    }
}