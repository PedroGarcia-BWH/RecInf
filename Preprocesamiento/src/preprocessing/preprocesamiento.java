package preprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
        BufferedReader br = new BufferedReader(new FileReader(url.toURI().getPath()));
        String sWord;
        while((sWord = br.readLine()) != null) {
            while(asTerm.contains(sWord)) {
                //System.out.println("Eliminando: " + sWord);
                asTerm.remove(sWord);
            }
        }

        return asTerm;

    }

}
