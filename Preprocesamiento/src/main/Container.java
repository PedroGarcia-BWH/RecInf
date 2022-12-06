package main;

import java.util.List;

public class Container {
    public String sTermino;
    public double IDF;

    public List<ContainerDocId> aDocIDpeso;
    public Container(String sTermino, double IDF, List<ContainerDocId> aDocIDpeso) {
        this.sTermino = sTermino;
        this.IDF = IDF;
        this.aDocIDpeso = aDocIDpeso;
    }


}
