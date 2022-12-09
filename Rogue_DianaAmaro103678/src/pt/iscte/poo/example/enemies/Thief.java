package pt.iscte.poo.example.enemies;

import pt.iscte.poo.example.Enemy;
import pt.iscte.poo.example.GameEngine;
import pt.iscte.poo.example.Hero;
import pt.iscte.poo.example.Movable;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.example.Item;

import java.util.Random;

public class Thief extends Enemy implements Movable {

    private Item robbedItem;

    public Thief(Point2D position, int hitPoints) {
        super(Thief.class.getSimpleName(), position, hitPoints);
    }

    @Override
    public void move() {
        Hero hero = GameEngine.getInstance().getHero();
        Point2D newPos = moveTowardsHero(hero.getPosition());

        if (newPos.distanceTo(hero.getPosition()) == 0) {
            // To implement
            Random r = new Random();
            robbedItem = GameEngine.getInstance().getInventory().removeInventory(r.nextInt(2));
        } else setPosition(newPos);
    }
}