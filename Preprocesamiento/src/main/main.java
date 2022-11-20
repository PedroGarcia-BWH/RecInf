package main;

import preprocessing.preprocesamiento;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class main {
    static HashMap<String, Integer> fTerm;
    static HashMap<String, docIDpeso > indiceInvertido;
    public static void main(String[] args) throws IOException {
        System.out.println("Introduce directorio de trabajo:");
        String directorio = System.console().readLine();
        File fDir = new File(directorio);
        File[] afFichero = fDir.listFiles();
        String sTexto;
        ArrayList<String> asTerm;
        preprocesamiento prepro = new preprocesamiento();


        for (int x=0;x<afFichero.length;x++) {
            fTerm = new HashMap<String, Integer>();
            sTexto = new String (Files.readAllBytes(Paths.get(afFichero[x].getAbsolutePath())));
            asTerm = prepro.preprocessing(sTexto);

            conteoElementos(asTerm);

        }
    }

    public static void conteoElementos(ArrayList<String> asTerm) {

        for(String sTerm : asTerm) {
            if(fTerm.containsKey(sTerm)) {
                fTerm.put(sTerm, fTerm.get(sTerm) + 1);
            } else {
                fTerm.put(sTerm, 1);
            }
        }
    }

    public static void calcularTF_paso2(String sNombreFichero) {
        for (String sTerm : fTerm.keySet()) {
            double tf = (double) 1 + Math.log(fTerm.get(sTerm)) / Math.log(2);
            if(indiceInvertido.containsKey(sTerm)) {
                indiceInvertido.put(sTerm, indiceInvertido.get(sTerm).docId.put(sNombreFichero, tf));
            } else {
                indiceInvertido.put(sTerm, new docIDpeso(tf));
            }
        }
    }
}
