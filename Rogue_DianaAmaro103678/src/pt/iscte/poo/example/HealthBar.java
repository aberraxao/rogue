package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;
import java.util.List;

public class HealthBar {

    private static final int HEALTH_MAX_POS = 5;
    int healthMax;
    static double healthPoints;

    static List<Health> healthList = new ArrayList<>(HEALTH_MAX_POS);

    public HealthBar(int healthMax, int gridHeight) {
        this.healthMax = healthMax;
        this.healthPoints = healthMax;
        for (int x = 0; x < HEALTH_MAX_POS; x++)
            healthList.add(new Health(new Point2D(x, gridHeight)));
    }

    private static double getHealthPoints(){
        return GameEngine.getInstance().getHero().getHitPoints();
    }

    public static void updateHealth() {
        for (Health health : healthList) {
            if ( getList().indexOf(health) >= (getHealthPoints() /2-1))
                health.setName("Red");
            else if ( (getList().indexOf(health)) >= (int)(getHealthPoints() /2-1))
                health.setName("GreenRed");
            else
                health.setName("Green");
        }
    }

    public static List<Health> getList() {
        return healthList;
    }
}
