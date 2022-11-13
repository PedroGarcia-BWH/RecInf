package preprocessing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class preprocesamiento {
    private gestorFiltro gestorCaracters = new gestorFiltro();
    private gestorFiltro gestorTerminos = new gestorFiltro();
    public void preprocesamiento(String directorio) throws IOException {
        File fDir = new File(directorio);
        File[] afFichero = fDir.listFiles();
        String[] asFichero = new String[afFichero.length];
        String sTexto;
        ArrayList asTerm;

        añadirFiltrosCaracteres();
        añadirFiltrosTerminos();

        for (int x=0;x<afFichero.length;x++) {
           sTexto = new String (Files.readAllBytes(Paths.get(afFichero[x].getAbsolutePath())));
            //preprocesamos
           //convertimos todo el texto a minúsculas
           sTexto.toLowerCase();
           //aplicamos el filtro
            sTexto = gestorCaracters.apply(sTexto);

            //aplicamos el filtro de terminos (PREGUNTAR A ANTONIO  SI SE PUEDE HACER ASI O COMO VIENE EN EL ESQUEMA)
            sTexto = gestorTerminos.apply(sTexto);

            //Division de texto en terminos
            asTerm = new ArrayList<>(Arrays.asList(sTexto.split(" ")));
        }
    }

    private void añadirFiltrosCaracteres() {
        gestorCaracters.add(new Filtro("\\p{Punct}", " "));
        //eliminamos los numeros
        gestorCaracters.add(new Filtro("\\s[0-9]+\\s", " "));
        //eliminamos  los "-" que no sean guiones
        gestorCaracters.add(new Filtro("-+ | -+", " "));
        //eliminamos los espacios duplicdos
        gestorCaracters.add(new Filtro(" +", " "));
    }

    private void añadirFiltrosTerminos() throws IOException {
        String sTerminos = new String (Files.readAllBytes(Paths.get("terminos.txt")));
        String[] asTerminos = sTerminos.split(" ");

        for (String sTermino : asTerminos) {
            gestorTerminos.add(new Filtro(sTermino, " "));
        }

        //eliminamos los espacios duplicdos
        gestorCaracters.add(new Filtro(" +", " "));
    }

}
