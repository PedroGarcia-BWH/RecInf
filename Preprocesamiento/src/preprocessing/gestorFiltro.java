package preprocessing;

import java.util.ArrayList;
import java.util.List;

public class gestorFiltro {
    List<Filtro> aFiltro = new ArrayList<Filtro>();
    //constructor
    public gestorFiltro () {}

    public void add (Filtro filtro) {
        aFiltro.add(filtro);
    }

    public void remove (Filtro filtro) {
        aFiltro.remove(filtro);
    }

    public String apply (String sTexto) {
        for (Filtro filtro : aFiltro) {
            sTexto = filtro.apply(sTexto);
        }
        return sTexto;
    }
}
