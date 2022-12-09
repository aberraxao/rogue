package pt.iscte.poo.example;

public class Gamer {

    private String name;
    private int score;

    public Gamer(String name, int score) {
        this.name = name;
        this.score = score;
    }
    
    public int compare(Gamer g1, Gamer g2) {
        return g1.getScore() - g2.getScore();}
    
    
    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    @Override
    public String toString() {
        return getName() + " : " + getScore();
    }
}
