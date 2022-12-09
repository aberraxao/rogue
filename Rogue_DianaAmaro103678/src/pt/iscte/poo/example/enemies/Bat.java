package pt.iscte.poo.example.enemies;

import pt.iscte.poo.example.Enemy;
import pt.iscte.poo.example.GameEngine;
import pt.iscte.poo.example.Hero;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Bat extends Enemy {

    public Bat(Point2D position, int hitPoints) {
        super(Bat.class.getSimpleName(), position, hitPoints);
    }

    @Override
    public void move() {
        Hero hero = GameEngine.getInstance().getHero();
        Point2D newPos;
        if (Math.random() > 0.5)
            newPos = moveTowardsHero(hero.getPosition());
        else {
            newPos = getPosition().plus(Direction.random().asVector());
            if (!positionEmpty(newPos)) {
                newPos = getPosition();
            }
        }

        if (newPos.distanceTo(hero.getPosition()) == 0) {
            if (Math.random() > 0.5) {
                attack(hero, -1);
                this.updateHitPoints(1);
            }
        } else
            setPosition(newPos);
    }
}