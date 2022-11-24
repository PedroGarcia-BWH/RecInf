package preprocessing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class preprocesamiento {
    private gestorFiltro gestorCaracters = new gestorFiltro();

    public preprocesamiento () throws IOException {
        //caracteres
        añadirFiltrosCaracteres();
    }
        public ArrayList<String> preprocessing(String sTexto) throws IOException {

        ArrayList asTerm;

        //preprocesamos
            // convertimos todo el texto a minúsculas
            sTexto.toLowerCase();
            //aplicamos el filtro
            sTexto = gestorCaracters.apply(sTexto);

            //Division de texto en terminos en un array
            asTerm = new ArrayList<String>(Arrays.asList(sTexto.split(" ")));

            //aplicamos el filtro de terminos
            return eliminarTerminos(asTerm);
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

    private ArrayList eliminarTerminos(ArrayList asTerm) throws IOException {
        String sTerminos = new String (Files.readAllBytes(Paths.get("C:\\Users\\condo\\Desktop\\ProjectRecInf\\RecInf\\Preprocesamiento\\src\\preprocessing\\terminos.txt")));
        String[] asTerminos = sTerminos.split(" ");

        for (String sTermino : asTerminos) {
            if(asTerm.contains(sTermino)) {
                asTerm.remove(sTermino);
            }
        }

        return asTerm;

    }

}
