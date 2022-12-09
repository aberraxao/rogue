package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

import static pt.iscte.poo.example.GameEngine.logger;

public abstract class Enemy extends GameElement implements Movable {

    private int hitPoints;

    protected Enemy(String name, Point2D position, int hitPoints) {
        super(name, position);
        this.hitPoints = hitPoints;
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
        setHitPoints(Math.max(0, getHitPoints() + delta));
        if (getHitPoints() == 0) {
            GameEngine.setHeroPosition(getPosition());
            GameEngine.removeGameElement(this);
        }
    }

    @Override
    public void attack(Movable movable) {
        movable.updateHitPoints(-1);
        logger.info(getName() + " hit " + movable.getName() + " -> new hitPoints: %s" + movable.getHitPoints());
    }

    @Override
    public int getLayer() {
        return 3;
    }
}