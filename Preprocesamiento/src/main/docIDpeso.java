package main;

import java.util.HashMap;

public class docIDpeso {

    public HashMap<String, Double> docId;
    private double IDF;

    public docIDpeso(double IDF) {
        this.IDF = IDF;
    }

    public void setIDF(double IDF) {
        this.IDF = IDF;
    }
}
