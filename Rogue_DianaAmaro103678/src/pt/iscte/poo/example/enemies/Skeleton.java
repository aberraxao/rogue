package pt.iscte.poo.example.enemies;

import pt.iscte.poo.example.Enemy;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import static pt.iscte.poo.example.GameEngine.logger;

public class Skeleton extends Enemy {

    public Skeleton(Point2D position, int hitPoints) {
        super(Skeleton.class.getSimpleName(), position, hitPoints);
    }

    @Override
    public void move(Direction d) {
        // TODO: problema quando vai para a linha dos items
        Vector2D randVector = d.asVector();
        Point2D newPos = getPosition().plus(randVector);
        //List<GameElement> elementList = selectList(GameEngine.getRoomList(), el -> el.getPosition().equals(newPos) && el.getLayer() >= 1);
        setPosition(newPos);
    }
}