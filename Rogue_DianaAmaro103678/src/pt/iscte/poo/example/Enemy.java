package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

import java.util.List;

import static pt.iscte.poo.example.GameEngine.logger;

public abstract class Enemy extends GameElement implements Movable {

    private final int MAX_HITPOINTS;
    private int hitPoints;
    private boolean moveEnable = true;

    protected Enemy(String name, Point2D position, int hitPoints) {
        super(name, position);
        this.MAX_HITPOINTS = hitPoints;
        this.hitPoints = hitPoints;
    }

    public boolean isMoveEnable() {
        return this.moveEnable;
    }

    public void reverseMoveEnable() {
        this.moveEnable = !isMoveEnable();
    }

    public int getHitPoints() {
        return this.hitPoints;
    }

    @Override
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    @Override
    public void updateHitPoints(int delta) {
        // TODO: fix this
        setHitPoints(Math.min(MAX_HITPOINTS, Math.max(0, getHitPoints() + delta)));
        if (getHitPoints() == 0) {
            GameEngine.setHeroPosition(getPosition());
            GameEngine.removeGameElement(this);
        }
    }

    @Override
    public void attack(Movable movable) {
        movable.updateHitPoints(-1);
        logger.info(getName() + " hit " + movable.getName() + " -> new hitPoints: " + movable.getHitPoints());
    }

    public Point2D allowedDirectionTo(Point2D heroPosition) {

        // TODO: check for walls

        int minDistance = GameEngine.getGridWidth();
        Point2D minDistancePoint = null;

        List<Point2D> neighbourhood = getPosition().getWideNeighbourhoodPoints();

        for (Point2D neighbour : neighbourhood)
            if (neighbour.distanceTo(heroPosition) <= minDistance) {
                minDistance = neighbour.distanceTo(heroPosition);
                minDistancePoint = neighbour;
            }
        return minDistancePoint;
    }

    @Override
    public int getLayer() {
        return 3;
    }
}