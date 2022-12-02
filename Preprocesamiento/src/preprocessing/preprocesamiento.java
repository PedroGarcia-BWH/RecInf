package preprocessing;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class preprocesamiento {
    private gestorFiltro gestorCaracters = new gestorFiltro();

    public preprocesamiento () throws IOException {
        //caracteres
        añadirFiltrosCaracteres();
    }
        public ArrayList<String> preprocessing(String sTexto) throws IOException, URISyntaxException {

        ArrayList asTerm;

        //preprocesamos
            // convertimos todo el texto a minúsculas
            sTexto = sTexto.toLowerCase(Locale.ENGLISH);
            //aplicamos el filtro
            sTexto = gestorCaracters.apply(sTexto);
            //System.out.println(sTexto);
            //Division de texto en terminos en un array
            asTerm = new ArrayList<String>(Arrays.asList(sTexto.split("[ ||\n]")));

            //aplicamos el filtro de terminos
            return eliminarTerminos(asTerm);
    }

    private void añadirFiltrosCaracteres() {
        gestorCaracters.add(new Filtro("\\p{Punct}", " "));
        //eliminamos los numeros
        gestorCaracters.add(new Filtro("[^A-Za-z]", " "));
        //eliminamos  los "-" que no sean guiones
        gestorCaracters.add(new Filtro("-+ | -+", " "));
        //eliminamos los espacios duplicdos
        gestorCaracters.add(new Filtro(" +", " "));
    }

    private ArrayList<String> eliminarTerminos(ArrayList<String> asTerm) throws IOException, URISyntaxException {
        URL url = getClass().getResource("terminos.txt");
        String sTerminos = new String (Files.readAllBytes(Paths.get(url.toURI())));
        String[] asTerminos = sTerminos.split("[ ||\n]");

        //System.out.println("Terminos a eliminar: " + asTerminos.length);
        for (String sTermino : asTerminos) {
            System.out.println("sTermino: " + sTermino + " asTerm: " + asTerm);
            if(asTerm.contains(sTermino)) {
                System.out.println("Eliminando: " + sTermino);
                asTerm.remove(sTermino);
            }
        }

        return asTerm;

    }

}
