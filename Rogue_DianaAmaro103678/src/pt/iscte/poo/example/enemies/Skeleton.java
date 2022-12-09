package pt.iscte.poo.example.enemies;

import pt.iscte.poo.example.Enemy;
import pt.iscte.poo.example.GameEngine;
import pt.iscte.poo.example.Hero;
import pt.iscte.poo.utils.Point2D;

public class Skeleton extends Enemy {

    public Skeleton(Point2D position, int hitPoints) {
        super(Skeleton.class.getSimpleName(), position, hitPoints);
    }

    @Override
    public void move() {
        // TODO: pode andar na diagonal?
        if (isMoveEnable()) {
            Hero hero = GameEngine.getHero();
            Point2D newPos = allowedDirectionTo(hero.getPosition());
            setPosition(newPos);
            if (getPosition().distanceTo(hero.getPosition()) == 0)
                attack(hero);
        }
        reverseMoveEnable();
    }
}