package main;

import com.google.gson.Gson;
import preprocessing.preprocesamiento;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;


public class main {
    private static preprocesamiento _preprocesamiento ;
    static HashMap<String, Integer> longDocumento = new HashMap<String, Integer>();
    static HashMap<String, docIDpeso > indiceInvertido = new HashMap<String, docIDpeso>();

    static HashMap<String, Double> docId = new HashMap<String, Double>();

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        boolean bFinished = true;
        _preprocesamiento = new preprocesamiento();
        do{
            System.out.println("Search: ");
            Scanner out = new Scanner(System.in);
            String sConsulta = out.nextLine();
            //consulta preprocesada
            ArrayList<String> asPreprocesado =  _preprocesamiento.preprocessing(sConsulta);
            ranking(asPreprocesado);
            //sort docId
            List<Ranking> aRanking = sort();

            //print ranking
            if(aRanking.size() < 10) {
                for (int i = 0; i < aRanking.size(); i++) {
                    System.out.println(aRanking.get(i).doc + " " + aRanking.get(i).peso);
                }
            }else {
                for (int i = 0; i < 10; i++) { // preguntar que hacemos si no hay 10
                    System.out.println(aRanking.get(i).doc + " " + aRanking.get(i).peso);
                }
            }

            System.out.println("¿Quieres realizar otra consulta?S/N: ");
            Scanner out2 = new Scanner(System.in);
            String sRespuesta = out2.nextLine();
            if(sRespuesta.equals("N")) bFinished = false;
        }while(bFinished);
    }

    public static List<Ranking> sort() {
        List<String> aKeys = new ArrayList<String>(docId.keySet());
        List<Double> aValues = new ArrayList<Double>(docId.values());
        List<Ranking> sorted = new ArrayList<Ranking>();

        for (int i = 0; i < aKeys.size(); i++) {
            sorted.add(new Ranking(aKeys.get(i), aValues.get(i)));
        }


        Collections.sort(sorted, Comparator.comparing(Ranking::getPeso));
        return sorted;
    }

    public static void ranking(ArrayList<String> asPreprocesado) throws IOException { //preguntar antonio pag 61
        //recorremos el indice invertido
        for (String sTermino : asPreprocesado) {
            if(indiceInvertido.containsKey(sTermino)) {
                for(String sDocIdpeso : indiceInvertido.get(sTermino).docId.keySet()) {
                    //calculamos el peso
                    double dPeso = indiceInvertido.get(sTermino).docId.get(sDocIdpeso) * indiceInvertido.get(sTermino).getIDF();
                    //añadimos el peso al docId
                    if(docId.containsKey(sDocIdpeso)) docId.put(sDocIdpeso, docId.get(sDocIdpeso) + dPeso);
                    else docId.put(sDocIdpeso, dPeso);
                }
            }

        }
    }

    public void leerLongDocumento() throws IOException {
        String filePath = "C:\\Users\\condo\\Desktop\\ProjectRecInf\\RecInf\\Preprocesamiento\\src\\main\\longitudDocumento.txt";

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split(" ", 2);
            if (parts.length >= 2)
            {
                longDocumento.put(parts[0], Integer.parseInt(parts[1]));
            }
        }
    }

    public void leerIndiceInvertido() throws FileNotFoundException {
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\condo\\Desktop\\ProjectRecInf\\RecInf\\Preprocesamiento\\src\\main\\indiceInvertido.json"));
        indiceInvertido = gson.fromJson(br, HashMap.class);
    }
}
