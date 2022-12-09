package pt.iscte.poo.example.enemies;

import pt.iscte.poo.example.Enemy;
import pt.iscte.poo.example.GameEngine;
import pt.iscte.poo.example.Hero;
import pt.iscte.poo.utils.Point2D;

public class Scorpio extends Enemy {

    public Scorpio(Point2D position, int hitPoints) {
        super(Scorpio.class.getSimpleName(), position, hitPoints);
    }

    @Override
    public void move() {
        Hero hero = GameEngine.getInstance().getHero();
        Point2D newPos = moveTowardsHero(hero.getPosition());

        if (newPos.distanceTo(hero.getPosition()) == 0) {
            hero.setIsPoisoned(true);
            attack(hero, -1);
        } else setPosition(newPos);
    }
}