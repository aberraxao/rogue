package pt.iscte.poo.example;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;
import java.util.List;

public class HealthBar {

    private final int gridHeight;
    private final int healthMax;
    private double healthPoints;

    static List<Health> healthList = new ArrayList<>();

    public HealthBar(int healthMax, int gridHeight) {
        this.healthMax = healthMax;
        this.healthPoints = healthMax;
        this.gridHeight = gridHeight;
        for (int x = 0; x < this.healthMax / 2; x++)
            healthList.add(new Health(new Point2D(x, gridHeight)));
    }

    private double getHealthPosition() {
        return (double) GameEngine.getInstance().getHero().getHitPoints() / 2;
    }

    public void updateHealth() {
        for (Health health : healthList) {
            if (getList().indexOf(health) <= getHealthPosition() - 1)
                health.setName("Green");
            else if ((getList().indexOf(health)) >= getHealthPosition())
                health.setName("Red");
            else
                health.setName("GreenRed");
        }
    }

    public List<Health> getList() {
        return healthList;
    }
}
