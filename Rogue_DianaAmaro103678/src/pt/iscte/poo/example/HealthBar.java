package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;
import java.util.List;

public class HealthBar {

    private static final int HEALTH_MAX_POS = 5;
    int healthMax;
    double healthPoints;

    List<Health> healthList = new ArrayList<>(HEALTH_MAX_POS);

    public HealthBar(int y, int healthMax) {
        this.healthMax = healthMax;
        this.healthPoints = healthMax;
        for (int x = 0; x < HEALTH_MAX_POS; x++)
            healthList.add(new Health(new Point2D(x, y)));
    }

    public double getHealth() {
        return this.healthPoints;
    }

    public void addHealth(int delta) {
        // TODO: melhorar o update
        this.healthPoints = Math.min(healthPoints + delta, healthMax);
        updateHealth();
    }

    public void removeHealth(int delta) {
        this.healthPoints = Math.max(healthPoints + delta, 0);
        updateHealth();
    }

    public void updateHealth() {
        for (Health health : healthList) {
            if (health.getPosition().getY() < this.healthPoints / 2)
                health.setName("Green");
            else if (health.getPosition().getY() == (int) this.healthPoints / 2)
                health.setName("RedGreen");
            else
                health.setName("Red");
        }
    }
}
