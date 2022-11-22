package pt.iscte.poo.example;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import java.awt.event.KeyEvent;


public class EngineExample implements Observer {

    public static final int GRID_HEIGHT = 10;
    public static final int GRID_WIDTH = 10;

    private static EngineExample INSTANCE = null;
    private ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

    private List<AbstractObject> elements = new ArrayList<>();
    private Hero hero;
    private int turns;

    public static EngineExample getInstance() {
        if (INSTANCE == null)
            INSTANCE = new EngineExample();
        return INSTANCE;
    }

    private EngineExample() {
        gui.registerObserver(this);
        gui.setSize(GRID_WIDTH, GRID_HEIGHT);
        gui.go();
    }


    public void start() {
        addFloor();
        //		loadRoom(0);
        addObjects();

        for (AbstractObject abstractObject : elements) {
            gui.addImage(abstractObject);
        }

        gui.setStatusMessage("ROGUE Starter Package - Turns:" + turns);
        gui.update();
    }

    private void addFloor() {
        for (int x=0; x!=GRID_WIDTH; x++)
            for (int y=0; y!=GRID_HEIGHT; y++)
                elements.add(new Floor(new Point2D(x,y)));
    }

    private void addObjects() {
        hero = new Hero(new Point2D(4,4));
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