package preprocessing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class preprocesamiento {
    private gestorFiltro gestor = new gestorFiltro();
    public void preprocesamiento(String directorio) throws IOException {
        File fDir = new File(directorio);
        File[] afFichero = fDir.listFiles();
        String[] asFichero = new String[afFichero.length];
        String sTexto = "";

        for (int x=0;x<afFichero.length;x++) {
           sTexto = new String (Files.readAllBytes(Paths.get(afFichero[x].getAbsolutePath())));
           //convertimos todo el texto a minúsculas
           asFichero[x].toLowerCase();
        }
    }

    private void añadirFiltros() {
        gestor.add(new Filtro("\\p{Punct}", " "));
        //eliminamos los numeros
        gestor.add(new Filtro("\\s[0-9]+\\s", " "));
        //eliminamos  los "-" que no sean guiones
        gestor.add(new Filtro("-+ | -+", " "));
        //eliminamos los espacios duplicdos
        gestor.add(new Filtro(" +", " "));
    }
}
