package preprocessing;

public class Filtro {
    private String sPatron;
    private String sReemplazo;
    //contructor
    public Filtro (String sPatron, String sReemplazo) {
        this.sPatron = sPatron;
        this.sReemplazo = sReemplazo;
    }
    public String apply (String sTexto) {
        return sTexto.replaceAll(sPatron, sReemplazo);
    }
}

