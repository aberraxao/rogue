package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Door extends GameElement {

    private String key;
    private final String otherRoom;
    private final Point2D positionOtherRoom;

    public Door(Point2D positionThisRoom, String otherRoom, Point2D positionOtherRoom, String key) {
        super("DoorClosed", positionThisRoom);
        this.otherRoom = otherRoom;
        this.positionOtherRoom = positionOtherRoom;
        this.key = key;
        // TODO: check door consistency
    }

    public Door(Point2D positionThisRoom, String otherRoom, Point2D positionOtherRoom) {
        super("DoorWay", positionThisRoom);
        this.otherRoom = otherRoom;
        this.positionOtherRoom = positionOtherRoom;
    }

    @Override
    public int getLayer() {
        return 1;
    }

    private boolean hasKey() {
        return key != null;
    }

    public String getKey() {
        return this.key;
    }

    public void openDoor () {
        setName("DoorOpen");
    }

    public String getOtherRoom() {
        return this.otherRoom.replace("room", "");
    }

    public int getOtherRoomInt(){
        return Integer.parseInt(this.getOtherRoom());
    }

    public Point2D getPositionOtherRoom() {
        return positionOtherRoom;
    }
}
