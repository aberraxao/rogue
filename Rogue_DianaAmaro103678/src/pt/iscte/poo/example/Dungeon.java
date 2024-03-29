package pt.iscte.poo.example;

import java.util.HashMap;

public class Dungeon {

    HashMap<Integer, Room> dungeonRooms = new HashMap<>();

    public Dungeon(int nb, Room room) {
        addDungeonRoom(nb, room);
    }

    public HashMap<Integer, Room> getDungeon() {
        return dungeonRooms;
    }

    public void addDungeonRoom(int nb, Room room) {
        dungeonRooms.put(nb, room);
    }

    public Room getDungeonRoom(int nb) {
        return dungeonRooms.get(nb);
    }

    public boolean dungeonHasRoom(int nb) {
        return dungeonRooms.containsKey(nb);
    }
}
