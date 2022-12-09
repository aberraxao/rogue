package pt.iscte.poo.example.enemies;

import pt.iscte.poo.example.Enemy;
import pt.iscte.poo.example.GameEngine;
import pt.iscte.poo.example.Hero;
import pt.iscte.poo.utils.Point2D;

public class Thug extends Enemy {

    public Thug(Point2D position, int hitPoints) {
        super(Thug.class.getSimpleName(), position, hitPoints);
    }

    @Override
    public void move() {
        Hero hero = GameEngine.getInstance().getHero();
        Point2D newPos = moveTowardsHero(hero.getPosition());

        if (newPos.distanceTo(hero.getPosition()) == 0) {
            if (Math.random() > 0.7)
                attack(hero, -3);
        } else
            setPosition(newPos);
    }
}