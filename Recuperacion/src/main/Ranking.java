package main;

public class Ranking {
    public String doc;
    public double peso;

    public Ranking(String doc, double peso) {
        this.doc = doc;
        this.peso = peso;
    }

    public double getPeso() {
        return peso;
    }
}
