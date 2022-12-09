package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Door extends GameElement {

    private String key;
    private boolean isOpen;
    private final String otherRoom;
    private final Point2D positionOtherRoom;

    public Door(Point2D positionThisRoom, String otherRoom, Point2D positionOtherRoom, String key) {
        super("DoorClosed", positionThisRoom);
        this.isOpen = false;
        this.otherRoom = otherRoom;
        this.positionOtherRoom = positionOtherRoom;
        this.key = key;
    }

    public Door(Point2D positionThisRoom, String otherRoom, Point2D positionOtherRoom) {
        super("DoorWay", positionThisRoom);
        this.isOpen = true;
        this.otherRoom = otherRoom;
        this.positionOtherRoom = positionOtherRoom;
    }

    @Override
    public int getLayer() {
        return 1;
    }

    public String getKey() {
        return this.key;
    }

    public boolean getIsOpen() {
        return this.isOpen;
    }

    public void openDoor() {
        this.isOpen = true;
        setName("DoorOpen");
    }

    public String getOtherRoom() {
        return this.otherRoom.replace("room", "");
    }

    public int getOtherRoomInt() {
        return Integer.parseInt(this.getOtherRoom());
    }

    public Point2D getPositionOtherRoom() {
        return positionOtherRoom;
    }
}
