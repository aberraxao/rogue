package pt.iscte.poo.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.example.items.Key;
import pt.iscte.poo.utils.Point2D;

import static pt.iscte.poo.example.GameEngine.sendMessageToGui;

public class Room {

    private final int gridWidth;
    private final int gridHeight;
    private final String roomPath = "rooms/room{NB}.txt";

    List<GameElement> roomItems = new ArrayList<>();

    public Room(String nb, int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;

        try {
            Scanner s = new Scanner(new File(roomPath.replace("{NB}", nb)));
            addFloor();
            addWall(s);
            addElement(s);
            s.close();
        } catch (FileNotFoundException e) {
            sendMessageToGui("Room " + nb + " not found.");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException |
                 InvocationTargetException e) {
            // TODO: better error handling
            sendMessageToGui(e.getMessage());
        }
    }

    private void addFloor() {
        for (int x = 0; x != this.gridWidth; x++)
            for (int y = 0; y != this.gridHeight; y++)
                roomItems.add(new Floor(new Point2D(x, y)));
    }

    private void addWall(Scanner s) {
        // TODO: verificar o tamanho do mapa
        int y = 0;
        String line;
        while (s.hasNextLine() && y <= this.gridHeight) {
            line = s.nextLine();
            for (int x = 0; x < line.length(); x++)
                if (line.charAt(x) == '#') roomItems.add(new Wall(new Point2D(x, y)));
            y++;
        }
    }

    private Point2D getPoint2D(String[] line, int x, int y) {
        return new Point2D(Integer.parseInt(line[x]), Integer.parseInt(line[y]));
    }

    private void addElement(Scanner s) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        String[] line;

        while (s.hasNextLine()) {
            line = s.nextLine().split(",");

            if (line[0].equals("Door")) {
                if (line.length == 7)
                    roomItems.add(new Door(getPoint2D(line, 1, 2), line[3], getPoint2D(line, 4, 5), line[6]));
                else roomItems.add(new Door(getPoint2D(line, 1, 2), line[3], getPoint2D(line, 4, 5)));

            } else if (line[0].equals("Key")) {
                roomItems.add(new Key(getPoint2D(line, 1, 2), line[3]));
            } else {
                Class<?> clazz;
                try {
                    clazz = Class.forName("pt.iscte.poo.example.enemies." + line[0]);
                } catch (ClassNotFoundException e) {
                    clazz = Class.forName("pt.iscte.poo.example.items." + line[0]);
                }

                Constructor<?> constructor = clazz.getConstructor(Point2D.class);
                roomItems.add((GameElement) constructor.newInstance(getPoint2D(line, 1, 2)));
            }
        }
    }

    public List<GameElement> getList() {
        return this.roomItems;
    }
}