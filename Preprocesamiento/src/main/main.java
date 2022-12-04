package main;

import preprocessing.preprocesamiento;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.Gson;


import com.google.gson.Gson;

public class main {
    static HashMap<String, Integer> fTerm;
    static HashMap<String, docIDpeso > indiceInvertido = new HashMap<String, docIDpeso>();

    static HashMap<String, Double > longPesoDoc = new HashMap<String, Double>();
    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Introduce directorio de trabajo:");
        Scanner out = new Scanner(System.in);
        String directorio = out.nextLine();
        directorio.replace("\\", "\\\\");
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
            calcularTF_paso2(Paths.get(afFichero[x].getAbsolutePath()).getFileName().toString());
        }

        calcularIDF(afFichero.length);
        //imprmir en fichero indiceInvertido
        imprimirIndiceInvertido(); //preguntar antonio velez
        //imprimir en fichero longPesoDoc
        imprimirLongPesoDoc();


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
        //System.out.println(sNombreFichero);
        for (String sTerm : fTerm.keySet()) {
            double tf = (double) 1 + Math.log(fTerm.get(sTerm)) / Math.log(2);

            if(!indiceInvertido.containsKey(sTerm)) indiceInvertido.put(sTerm, new docIDpeso());

            indiceInvertido.get(sTerm).docId.put(sNombreFichero, tf);
            //System.out.println(indiceInvertido.keySet());
        }
        //System.out.println(indiceInvertido);
        System.out.println(fTerm);
    }

    public static void calcularIDF (int nDocs) {
        for (String sTerm : indiceInvertido.keySet()) {
            double idf = nDocs / indiceInvertido.get(sTerm).docId.size();
            indiceInvertido.get(sTerm).setIDF(idf);

            //calcular peso de cada documento
            for (String sDoc : indiceInvertido.get(sTerm).docId.keySet()) {
                double peso = indiceInvertido.get(sTerm).docId.get(sDoc) * indiceInvertido.get(sTerm).getIDF();

                if(!longPesoDoc.containsKey(sDoc)) longPesoDoc.put(sDoc, 0.0);
                longPesoDoc.put(sDoc, longPesoDoc.get(sDoc) + Math.pow(peso, 2));
            }
        }
    }

    public static void imprimirLongPesoDoc() {
        FileWriter fichero = null;
        PrintWriter pw ;
        try
        {
            fichero = new FileWriter("../longPesoDoc.txt");
            pw = new PrintWriter(fichero);

            for (String sDoc : longPesoDoc.keySet()) {
                //imprmir en fichero peso.txt
                pw.println(sDoc + " " + longPesoDoc.get(sDoc));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void imprimirIndiceInvertido() {
        String path = "../indiceInvertido.json";
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String jsonString = gson.toJson(indiceInvertido);
            out.write(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
