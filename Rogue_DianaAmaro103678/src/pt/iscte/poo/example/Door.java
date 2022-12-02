package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Door extends GameElement {
    private String key;
    private String room;
    private Point2D posRoom;

    public Door(Point2D position, String room, Point2D posRoom, String key) {
        super("DoorClosed", position);
        this.room = room;
        this.posRoom = posRoom;
        this.key = key;
        System.out.println(this);
    }
    public Door(Point2D position, String room, Point2D posRoom) {
        super("DoorWay", position);
        this.room = room;
        this.posRoom = posRoom;
    }
    @Override
    public int getLayer() {
        return 0;
    }

    private boolean hasKey() {
        return key != null;
    }

    public String getKey() {
        return this.key;
    }
}