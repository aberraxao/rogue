package pt.iscte.poo.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import pt.iscte.poo.example.enemies.*;
import pt.iscte.poo.example.items.*;
import pt.iscte.poo.utils.Point2D;

import static pt.iscte.poo.example.GameElement.selectList;

public class Room {

    private int nb;
    private final int roomHeight = GameEngine.getInstance().getGridHeight();
    private final int roomWidth = GameEngine.getInstance().getGridWidth();
    private List<GameElement> roomElementsList = new ArrayList<>();

    public Room(int nb) {
        try {
            this.nb = nb;
            String roomPath = String.format("rooms/room%s.txt", nb);
            Scanner s = new Scanner(new File(roomPath));
            addFloor();
            addWall(s);
            addElement(s);
            s.close();
        } catch (FileNotFoundException e) {
            GameEngine.getInstance().sendMessageToGui("Room " + nb + " not found.");
        }
    }

    private Point2D getPoint2D(String[] line, int x, int y) {
        return new Point2D(Integer.parseInt(line[x]), Integer.parseInt(line[y]));
    }

    public void addElementToRoom(GameElement el){
        roomElementsList.add(el);
    }

    private void addFloor() {
        for (int x = 0; x != roomWidth; x++) {
            for (int y = 0; y != roomHeight; y++)
                addElementToRoom(new Floor(new Point2D(x, y)));
            addElementToRoom(new Item("Black", new Point2D(x, roomHeight), 0));
        }
    }

    private void addWall(Scanner s) {
        int y = 0;
        String line;
        while (s.hasNextLine() && y <= roomHeight) {
            line = s.nextLine();
            for (int x = 0; x < line.length(); x++)
                if (line.charAt(x) == '#') addElementToRoom(new Wall(new Point2D(x, y)));
            y++;
        }
        if (y != roomHeight + 1) {
            GameEngine.getInstance().sendMessageToGui("The room measures should be " + roomHeight + " x " + roomWidth);
            GameEngine.getInstance().closeGui();
        }
    }

    private void addElement(Scanner s) {
        while (s.hasNextLine()) {

            String[] line = s.nextLine().split(",");
            String elName = line[0];

            try {
                GameElement gameElement = switch (elName) {
                    case "Door" -> {
                        if (line.length == 7)
                            yield new Door(getPoint2D(line, 1, 2), line[3], getPoint2D(line, 4, 5), line[6]);
                        else yield new Door(getPoint2D(line, 1, 2), line[3], getPoint2D(line, 4, 5));
                    }
                    case "Bat" -> new Bat(getPoint2D(line, 1, 2), 3);
                    case "Scorpio" -> new Scorpio(getPoint2D(line, 1, 2), 2);
                    case "Skeleton" -> new Skeleton(getPoint2D(line, 1, 2), 5);
                    case "Thief" -> new Thief(getPoint2D(line, 1, 2), 5);
                    case "Thug" -> new Thug(getPoint2D(line, 1, 2), 10);
                    case "Armor" -> new Armor(getPoint2D(line, 1, 2));
                    case "HealingPotion" -> new HealingPotion(getPoint2D(line, 1, 2));
                    case "Key" -> new Key(getPoint2D(line, 1, 2), line[3]);
                    case "Sword" -> new Sword(getPoint2D(line, 1, 2));
                    case "Treasure" -> new Treasure(getPoint2D(line, 1, 2));
                    default -> null;
                };
                if (gameElement == null) {
                    GameEngine.getInstance().sendMessageToGui("A class needs to be defined for " + elName);
                    GameEngine.getInstance().closeGui();
                } else addElementToRoom(gameElement);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ArrayIndexOutOfBoundsException("The parameters are not well defined for the class " + elName);
            }
        }
    }

    public int getNb() {
        return this.nb;
    }

    public List<GameElement> getRoomElementsList() {
        return this.roomElementsList;
    }

    public List<GameElement> getEnemiesList() {
        return selectList(this.roomElementsList, el -> el instanceof Enemy);
    }

    public void moveEnemies() {
        for (GameElement el : getEnemiesList())
            ((Enemy) el).move();
    }

    public void removeElement(GameElement el) {
        roomElementsList.remove(el);
    }
}