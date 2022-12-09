package pt.iscte.poo.example.enemies;

import pt.iscte.poo.example.Enemy;
import pt.iscte.poo.example.GameEngine;
import pt.iscte.poo.example.Hero;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Thug extends Enemy {

    public Thug(Point2D position, int hitPoints) {
        super(Thug.class.getSimpleName(), position, hitPoints);
    }

    @Override
    public void move() {
        Hero hero = GameEngine.getHero();
        setPosition(moveTowardsHero(hero.getPosition()));

        if (getPosition().distanceTo(hero.getPosition()) == 0 && Math.random() > 0.7) {
            attack(hero, -3);
        }
    }
}